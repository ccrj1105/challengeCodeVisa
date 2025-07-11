package com.pet.store.dtos;

import lombok.Data;

@Data
public class StoreInventoryBodyResponse {
  private int sold;
  private int pending;
  private int available;
}
