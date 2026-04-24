package api;

import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class UserApi {

    public Response createUser(Map<String, String> userData) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                // Matching your lowercase Excel headers
                .queryParam("customer.firstName", userData.get("firstName"))
                .queryParam("customer.lastName", userData.get("lastName"))
                .queryParam("customer.username", userData.get("username"))
                .queryParam("customer.password", userData.get("password"))
                .queryParam("repeatedPassword", userData.get("password"))
                // Required fields not in your screenshot - using defaults
                .queryParam("customer.address.street", "YCCE Road")
                .queryParam("customer.address.city", "Nagpur")
                .queryParam("customer.address.state", "MH")
                .queryParam("customer.address.zipCode", "441110")
                .queryParam("customer.phoneNumber", "9876543210")
                .queryParam("customer.ssn", "SSN-" + System.currentTimeMillis() % 1000)
                .log().all()
                .when()
                .post("https://parabank.parasoft.com/parabank/register.htm");
    }
}