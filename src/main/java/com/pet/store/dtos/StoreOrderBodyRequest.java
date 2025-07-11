package com.pet.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreOrderBodyRequest {
  private int id;
  private int petId;
  private int quantity;
  private String shipDate;
  private String status;
  private boolean complete;
}
