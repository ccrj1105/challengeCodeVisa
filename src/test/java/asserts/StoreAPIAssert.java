package asserts;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

import com.pet.store.dtos.StoreErrorResponse;
import com.pet.store.dtos.StoreInventoryBodyResponse;
import com.pet.store.dtos.StoreOrderBodyRequest;
import com.pet.store.dtos.StoreOrderDeleteresponse;
import io.restassured.response.Response;
import java.util.List;

public class StoreAPIAssert {

  public void validateStoreInventoryResponse(StoreInventoryBodyResponse inventoryResponse) {
    assertThat(
        "Sold should be an instance of Integer", inventoryResponse.getSold(), isA(Integer.class));
    assertThat(
        "Pending should be an instance of Integer",
        inventoryResponse.getPending(),
        isA(Integer.class));
    assertThat(
        "Available should be an instance of Integer",
        inventoryResponse.getAvailable(),
        isA(Integer.class));
  }

  public void validateStoreOrderBodyResponse(
      StoreOrderBodyRequest orderResponse, List<Object> expectedValues) {
    assertThat("Order ID should match", orderResponse.getId(), is(expectedValues.get(0)));
    assertThat("Pet ID should match", orderResponse.getPetId(), is(expectedValues.get(1)));
    assertThat("Quantity should match", orderResponse.getQuantity(), is(expectedValues.get(2)));

    String actualShipDate = orderResponse.getShipDate();
    String formattedActualShipDate = actualShipDate.split("T")[0];
    assertThat("Ship date should match", formattedActualShipDate, is(expectedValues.get(3)));

    assertThat(
        "Complete status should match", orderResponse.isComplete(), is(expectedValues.get(4)));
  }

  public void validateStoreDeleteResponse(StoreOrderDeleteresponse deleteresponse) {
    assertThat("Code should be an Integer", deleteresponse.getCode(), isA(Integer.class));
    assertThat("Type should be a String", deleteresponse.getType(), isA(String.class));
    assertThat("Message should be a String", deleteresponse.getMessage(), isA(String.class));
  }

  public void validateStatusCode(String message, Response response, int statusCode) {
    assertThat(message, response.getStatusCode(), is(statusCode));
  }

  public void validateErrorResponse(StoreErrorResponse storeErrorResponse) {
    assertThat("Code should be an Integer", storeErrorResponse.getCode(), isA(Integer.class));
    assertThat("Type should be a String", storeErrorResponse.getType(), isA(String.class));
    assertThat("Message should be a String", storeErrorResponse.getMessage(), isA(String.class));
  }
}
