package org.acme;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.core.HttpHeaders;

import org.acme.utils.KeycloakRealmResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.DisabledOnNativeImage;

//import org.acme.utils.TestContainerResource;

//import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;


@QuarkusTest
@QuarkusTestResource(KeycloakRealmResource.class)
class CartResourceTest {


        static String ADMIN_BEARER_TOKEN;
        static String USER_BEARER_TOKEN;
    
        private static final String INSERT_WRONG_CART_IN_DB = "insert into carts values (999, current_timestamp, current_timestamp,'NEW', 3)";
        private static final String DELETE_WRONG_CART_IN_DB = "delete from carts where id = 999";
        
        @BeforeAll
        static void init() {
            ADMIN_BEARER_TOKEN = System.getProperty("quarkus-admin-access-token");
            USER_BEARER_TOKEN = System.getProperty("quarkus-test-access-token");
        }

        @Inject
        DataSource dataSource;

        @Test
        void testFindAllWithAdminRole() {
                Properties props = System.getProperties();

    //We want to loop through the entrys using the Keyset
    Set<Object> propKeySet = props.keySet();

   for (Object singleKey : propKeySet) {
   System.out.println(singleKey += props.getProperty((String) singleKey));    
   }
   
             System.out.println("Token Admin:"+ADMIN_BEARER_TOKEN);   
            given().when()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
                    .get("/carts")
                    .then()
                    .statusCode(OK.getStatusCode())
                    .body("size()", greaterThan(0));
        }
    

        @Test
        void testGetActiveCartForCustomerWhenThereAreTwoCartsInDB() {
                executeSql(INSERT_WRONG_CART_IN_DB);
                get("/carts/customer/3").then()
                                .statusCode(INTERNAL_SERVER_ERROR.getStatusCode())
                                .body(containsString("Many active carts detected !!!"));
                executeSql(DELETE_WRONG_CART_IN_DB);
        }

        private void executeSql(String query) {
                try (var connection = dataSource.getConnection()) {
                        var statement = connection.createStatement();
                        statement.executeUpdate(query);
                } catch (SQLException e) {
                        throw new IllegalStateException(
                                        "Error has occurred while trying to execute SQL Query: " + e.getMessage());
                }
        }

        @Test
        void testFindAll() {
                get("/carts")
                                .then()
                                .statusCode(OK.getStatusCode())
                                .body("size()", greaterThan(0));
        }

        @Test
        void testFindAllActiveCarts() {
                get("/carts/active").then()
                                .statusCode(OK.getStatusCode());
        }

        @Test
        void testGetActiveCartForCustomer() {
                get("/carts/customer/3").then()
                                .contentType(ContentType.JSON)
                                .statusCode(OK.getStatusCode())
                                .body(containsString("Peter"));
        }

        @Test
        void testFindById() {
                get("/carts/3").then()
                                .statusCode(OK.getStatusCode())
                                .body(containsString("status"))
                                .body(containsString("NEW"));
                get("/carts/100").then()
                                .statusCode(NO_CONTENT.getStatusCode());
        }

        @Test
        void testDelete() {
                get("/carts/active").then()
                                .statusCode(OK.getStatusCode())
                                .body(containsString("Jason"))
                                .body(containsString("NEW"));
                delete("/carts/1").then()
                                .statusCode(NO_CONTENT.getStatusCode());
                get("/carts/1").then()
                                .statusCode(OK.getStatusCode())
                                .body(containsString("Jason"))
                                .body(containsString("CANCELED"));
        }

}
