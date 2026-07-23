package com.previsit.app.api.response;

public record ApiResponse(
    boolean success,
    String message
) {

}