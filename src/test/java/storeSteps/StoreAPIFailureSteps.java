package storeSteps;

import asserts.StoreAPIAssert;
import com.pet.store.dtos.StoreErrorResponse;
import com.pet.store.dtos.StoreOrderBodyRequest;
import com.pet.store.service.StoreService;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;

public class StoreAPIFailureSteps {
  StoreService storeService = new StoreService();
  StoreAPIAssert storeAPIAssert = new StoreAPIAssert();

  @Step(
      "Try to create Pet Store order with invalid Data id:<orderId>, petId:<petId>, quantity:<quantity>, shipDate:<shipDate>, complete:<complete>.")
  public void createPetStoreOrderWithInvalidData(
      int orderId, int petId, int quantity, String shipDate, boolean complete) {
    StoreOrderBodyRequest orderRequest =
        StoreOrderBodyRequest.builder()
            .id(orderId)
            .petId(petId)
            .quantity(quantity)
            .shipDate(shipDate)
            .complete(complete)
            .build();
    Gauge.writeMessage("Request Body: " + orderRequest);
    Response response = storeService.postStoreOrder(orderRequest);
    Gauge.writeMessage("Response: " + response.then().extract().body().asString());
    Gauge.writeMessage("Status Code:" + response.getStatusCode());
    StoreErrorResponse errorResponse = response.then().extract().as(StoreErrorResponse.class);
    storeAPIAssert.validateErrorResponse(errorResponse);
    storeAPIAssert.validateStatusCode("Expected status code to be 500", response, 500);
  }

  @Step("Try to get order by invalid id:<orderId>.")
  public void getOrderByInvalidId(int orderId) {
    Response response = storeService.getStoreOrder(orderId);
    Gauge.writeMessage("Response: " + response.then().extract().body().asString());
    Gauge.writeMessage("Status Code:" + response.getStatusCode());
    StoreErrorResponse errorResponse = response.then().extract().as(StoreErrorResponse.class);
    storeAPIAssert.validateErrorResponse(errorResponse);
    storeAPIAssert.validateStatusCode("Expected status code 404", response, 404);
  }

  @Step("Try to delete Store order with invalid ID: <OrderId>")
  public void deleteStoreOrderWithInvalidId(int orderId) {
    Response response = storeService.deleteStoreOrder(orderId);
    Gauge.writeMessage("Response: " + response.then().extract().body().asString());
    Gauge.writeMessage("Status Code:" + response.getStatusCode());
    storeAPIAssert.validateStatusCode("Expected status code 404", response, 404);
    StoreErrorResponse errorResponse = response.then().extract().as(StoreErrorResponse.class);
    storeAPIAssert.validateErrorResponse(errorResponse);
  }
}
