package petStore;

import javafx.scene.layout.Priority;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority = 1)
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

    given()
            .contentType("application/json")
            .log().all()
            .body(jsonBody)
    .when()
            .post(uri)
    .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Toto"))
            .body("status", is("available"))
            .body("category.name", is("MIGUEL10"))
            .body("tags.name", contains("pqp"))
    ;
    }

    @Test(priority = 2)
    public void consultarPet() {
        String petId = "1976110810";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Toto"))
                .body("status", is("available"))
                .body("category.name", is("MIGUEL10"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O token é " + token);
    }

    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Toto"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 4)
    public void excluirPet()  {
        String petId = "1976110810";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))

        ;
    }
}
