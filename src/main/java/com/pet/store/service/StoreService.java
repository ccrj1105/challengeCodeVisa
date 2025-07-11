package com.pet.store.service;

import static com.pet.store.constants.BaseURLConstants.STORE_API_BASE_URL;
import static com.pet.store.constants.StoreEndpointConstants.API_STORE_INVENTORY;
import static com.pet.store.constants.StoreEndpointConstants.API_STORE_ORDER;
import static com.pet.store.constants.StoreEndpointConstants.API_STORE_ORDER_ID;
import static io.restassured.http.ContentType.JSON;

import com.pet.store.dtos.StoreOrderBodyRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StoreService {

  public Response getStoreInventory() {
    return RestAssured.given()
        .baseUri(STORE_API_BASE_URL)
        .contentType(JSON)
        .when()
        .get(API_STORE_INVENTORY)
        .then()
        .extract()
        .response();
  }

  public Response getStoreOrder(int orderId) {
    return RestAssured.given()
        .baseUri(STORE_API_BASE_URL)
        .contentType(JSON)
        .when()
        .pathParam("orderId", orderId)
        .get(API_STORE_ORDER + API_STORE_ORDER_ID)
        .then()
        .log()
        .all()
        .extract()
        .response();
  }

  public Response postStoreOrder(StoreOrderBodyRequest storeOrderBodyRequest) {
    return RestAssured.given()
        .baseUri(STORE_API_BASE_URL)
        .contentType(JSON)
        .body(storeOrderBodyRequest)
        .post(API_STORE_ORDER)
        .then()
        .extract()
        .response();
  }

  public Response deleteStoreOrder(int orderId) {
    return RestAssured.given()
        .baseUri(STORE_API_BASE_URL)
        .contentType(JSON)
        .when()
        .pathParam("orderId", orderId)
        .delete(API_STORE_ORDER + API_STORE_ORDER_ID)
        .then()
        .log()
        .all()
        .extract()
        .response();
  }
}
