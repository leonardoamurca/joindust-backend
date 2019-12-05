package com.joindust.joindustbackend.payloads.responses;

public class DeletedResponse {
  private Long collectId;
  private String message;

  public DeletedResponse(Long collectId, String message) {
    this.collectId = collectId;
    this.message = message;
  }

  public Long getCollectId() {
    return collectId;
  }

  public void setCollectId(Long collectId) {
    this.collectId = collectId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}