package storeSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import asserts.StoreAPIAssert;
import com.pet.store.dtos.StoreInventoryBodyResponse;
import com.pet.store.dtos.StoreOrderBodyRequest;
import com.pet.store.dtos.StoreOrderDeleteresponse;
import com.pet.store.service.StoreService;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;

public class StoreAPISuccessSteps {
  StoreService storeService = new StoreService();
  StoreAPIAssert storeAPIAssert = new StoreAPIAssert();

  @Step(
      "Create Pet Store order with id:<orderId>, petId:<petId>, quantity:<quantity>, shipDate:<shipDate>, complete:<complete>.")
  public void createPetStoreOrder(
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
    storeAPIAssert.validateStatusCode("Expected status code 200", response, 200);
    StoreOrderBodyRequest orderResponse = response.then().extract().as(StoreOrderBodyRequest.class);
    List<Object> expectedValues = Arrays.asList(orderId, petId, quantity, shipDate, complete);
    storeAPIAssert.validateStoreOrderBodyResponse(orderResponse, expectedValues);
  }

  @Step("Get store by id:<orderId>.")
  public void getOrderById(int orderId) {
    Response response = storeService.getStoreOrder(orderId);
    Gauge.writeMessage("Response: " + response.then().extract().body().asString());
    Gauge.writeMessage("Status Code:" + response.getStatusCode());
    storeAPIAssert.validateStatusCode("Expected status code 200", response, 200);
    StoreOrderBodyRequest orderResponse = response.then().extract().as(StoreOrderBodyRequest.class);
    assertThat("Order ID should match", orderResponse.getId(), is(orderId));
  }

  @Step("Delete Store order By ID: <OrderId>")
  public void deleteStoreOrder(int orderId) {
    Response response = storeService.deleteStoreOrder(orderId);
    Gauge.writeMessage("Response: " + response.then().extract().body().asString());
    Gauge.writeMessage("Status Code:" + response.getStatusCode());
    storeAPIAssert.validateStatusCode("Expected status code 200", response, 200);
    StoreOrderDeleteresponse deleteresponse =
        response.then().extract().as(StoreOrderDeleteresponse.class);
    storeAPIAssert.validateStoreDeleteResponse(deleteresponse);
  }

  @Step("Get Store Inventory")
  public void getStoreInventory() {
    Response response = storeService.getStoreInventory();
    Gauge.writeMessage("Response: " + response.then().extract().body().asString());
    Gauge.writeMessage("Status Code:" + response.getStatusCode());
    storeAPIAssert.validateStatusCode("Expected status code 200", response, 200);
    StoreInventoryBodyResponse inventoryResponse =
        response.then().extract().as(StoreInventoryBodyResponse.class);
    storeAPIAssert.validateStoreInventoryResponse(inventoryResponse);
  }
}
