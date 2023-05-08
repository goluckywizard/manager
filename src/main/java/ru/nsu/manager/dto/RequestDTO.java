package ru.nsu.manager.dto;

public class RequestDTO {
    String requestId;

    public RequestDTO() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RequestDTO(String requestId) {
        this.requestId = requestId;
    }
}
