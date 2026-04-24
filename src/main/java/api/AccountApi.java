package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AccountApi {

    // Inside AccountApi.java or your verifyBalance step
    public Response getAccounts(String customerId) {
        return given()
                .pathParam("id", customerId)
                .when()
                .get("https://parabank.parasoft.com/parabank/services/REST/bank/customers/{id}/accounts");
    }
}