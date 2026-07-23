package com.previsit.app.documents;

import java.util.List;
import java.util.Map;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {

  private final Tika tika = new Tika();
  private final DocumentRepository documentRepository;
  private final WebClient groqWebClient;

  @Value("${groq.api.key}")
  private String apiKey;

  @Autowired
  public DocumentController(DocumentRepository documentRepository, WebClient groqWebClient) {
    this.documentRepository = documentRepository;
    this.groqWebClient = groqWebClient;
  }

  @PostMapping("/documents/upload")
  public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
    try {
      String text = tika.parseToString(file.getInputStream());

      Document doc = new Document();
      doc.setFileName(file.getOriginalFilename());
      doc.setContent(text);
      doc = documentRepository.save(doc);

      return ResponseEntity.ok(Map.of(
          "documentId", doc.getId(),
          "fileName", doc.getFileName()
      ));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", "Failed to process file: " + e.getMessage()));
    }
  }

  @PostMapping("/chat")
  public ResponseEntity<?> ask(@RequestBody ChatRequest request) {
    Document doc = documentRepository.findById(request.documentId())
        .orElse(null);

    if (doc == null) {
      return ResponseEntity.badRequest().body(Map.of("error", "Document not found"));
    }

    String systemPrompt = """
            You are a support assistant. Answer ONLY using the document text provided.
            If the answer is not clearly contained in the document, respond exactly with:
            "This question appears to be out of context for the uploaded document."
            Do not use outside knowledge.
            """;

    String userPrompt = "Document:\n" + doc.getContent() + "\n\nQuestion: " + request.question();

    Map<String, Object> body = Map.of(
        "model", "llama-3.3-70b-versatile",
        "max_tokens", 500,
        "messages", List.of(
            Map.of("role", "system", "content", systemPrompt),
            Map.of("role", "user", "content", userPrompt)
        )
    );

    System.out.println("Loaded key: " + apiKey);

    try {
      Map response = groqWebClient.post()
          .uri("/v1/chat/completions")
          .header("Authorization", "Bearer " + apiKey)
          .header("Content-Type", "application/json")
          .bodyValue(body)
          .retrieve()
          .bodyToMono(Map.class)
          .block();

      List choices = (List) response.get("choices");
      Map firstChoice = (Map) choices.get(0);
      Map message = (Map) firstChoice.get("message");
      String answer = (String) message.get("content");

      return ResponseEntity.ok(Map.of("answer", answer));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", "AI request failed: " + e.getMessage()));
    }
  }

  public record ChatRequest(Long documentId, String question) {}

}