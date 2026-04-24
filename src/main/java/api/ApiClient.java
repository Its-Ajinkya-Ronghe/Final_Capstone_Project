package api;

import io.restassured.RestAssured;

public class ApiClient {

    static {
        RestAssured.baseURI = "https://parabank.parasoft.com";
        RestAssured.useRelaxedHTTPSValidation(); // ← handles SSL issues
    }
}