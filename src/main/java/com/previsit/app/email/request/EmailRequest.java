package com.previsit.app.email.request;

public record EmailRequest(
    String emailId, String subject, String body
) {

}
