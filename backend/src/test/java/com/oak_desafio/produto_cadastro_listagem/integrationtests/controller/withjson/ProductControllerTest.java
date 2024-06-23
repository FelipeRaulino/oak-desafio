package com.oak_desafio.produto_cadastro_listagem.integrationtests.controller.withjson;

import com.oak_desafio.produto_cadastro_listagem.config.TestConfig;
import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.request.ProductRequestDTO;
import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.response.ProductResponseDTO;
import com.oak_desafio.produto_cadastro_listagem.integrationtests.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification requestSpecification;

    private static String createdProductId;

    @BeforeAll
    static void setup(){
        requestSpecification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .setContentType(TestConfig.CONTENT_TYPE_JSON)
                .setBasePath("/api/v1/product")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(0)
    void shouldBeAbleToCreateANewProduct(){
        ProductRequestDTO productRequest = new ProductRequestDTO(
                "Teclado Mecânico",
                "Teclado mecânico com iluminação RGB",
                200.00,
                true
        );

        ProductResponseDTO newProduct = given().spec(requestSpecification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(productRequest)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(ProductResponseDTO.class);

        assertNotNull(newProduct);
        assertNotNull(newProduct.id());
        assertNotNull(newProduct.name());
        assertNotNull(newProduct.description());
        assertNotNull(newProduct.price());
        assertNotNull(newProduct.available());
        assertNotNull(newProduct.createdAt());

        assertEquals("Teclado Mecânico", newProduct.name());
        assertEquals("Teclado mecânico com iluminação RGB", newProduct.description());
        assertEquals(200.00, newProduct.price());
        assertTrue(newProduct.available());

        createdProductId = newProduct.id();
    }

    @Test
    @Order(1)
    void shouldBeAbleToUpdateAProduct(){
        ProductRequestDTO productRequest = new ProductRequestDTO(
                "Teclado Mecânico",
                "Teclado mecânico sem iluminação RGB",
                250.00,
                false
        );

        ProductResponseDTO updatedProduct = given().spec(requestSpecification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(productRequest)
                .pathParams("id", createdProductId)
                .when()
                .put("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ProductResponseDTO.class);

        assertNotNull(updatedProduct);
        assertNotNull(updatedProduct.id());
        assertNotNull(updatedProduct.name());
        assertNotNull(updatedProduct.description());
        assertNotNull(updatedProduct.price());
        assertNotNull(updatedProduct.available());
        assertNotNull(updatedProduct.createdAt());

        assertEquals(createdProductId, updatedProduct.id());
        assertEquals("Teclado Mecânico", updatedProduct.name());
        assertEquals("Teclado mecânico sem iluminação RGB", updatedProduct.description());
        assertEquals(250.00, updatedProduct.price());
        assertFalse(updatedProduct.available());
    }

    @Test
    @Order(2)
    void shouldBeAbleToGetAllProducts(){
        List<ProductResponseDTO> products = given().spec(requestSpecification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<List<ProductResponseDTO>>() {});

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    @Order(3)
    void shouldBeAbleToDeleteAProduct(){
        given().spec(requestSpecification)
            .contentType(TestConfig.CONTENT_TYPE_JSON)
            .pathParams("id", createdProductId)
            .when()
            .delete("{id}")
            .then()
            .statusCode(204);
    }

}
