package com.pet.store.dtos;

import lombok.Data;

@Data
public class StoreErrorResponse {
  private int code;
  private String type;
  private String message;
}
