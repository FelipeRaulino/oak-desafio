package com.oak_desafio.produto_cadastro_listagem.utils.swagger;

import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.response.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ProductControllerSwaggerOptions {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
        summary = "Retrieve a list of products",
        description = "Fetches all products from the database",
        tags = { "Product" },
        responses = {
            @ApiResponse(
                description = "Successfully retrieved list of products",
                responseCode = "200",
                content = {
                    @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class))
                    )
                }
            ),
            @ApiResponse(description = "No products found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Invalid request format", responseCode = "400", content = @Content),
            @ApiResponse(description = "An unexpected error occurred", responseCode = "500", content = @Content)
        }
    )
    public @interface GetAllProductsOptions{}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
        summary = "Add a new product",
        description = "Add a product by passing the necessary information via JSON.",
        tags = { "Product" },
        responses = {
            @ApiResponse(
                description = "Product successfully added",
                responseCode = "201",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))
            ),
            @ApiResponse(description = "Invalid request format", responseCode = "404", content = @Content),
            @ApiResponse(description = "Resource not found", responseCode = "400", content = @Content),
            @ApiResponse(description = "An unexpected error occurred", responseCode = "500", content = @Content)
        }
    )
    public @interface CreateProductOptions{}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
        summary = "Update an existent product",
        description = "Update a product by passing the necessary information via JSON.",
        tags = { "Product" },
        responses = {
            @ApiResponse(
                description = "Product successfully updated",
                responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))
            ),
            @ApiResponse(description = "Product not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Invalid request format", responseCode = "400", content = @Content),
            @ApiResponse(description = "An unexpected error occurred", responseCode = "500", content = @Content)
        }
    )
    public @interface UpdateProductOptions{}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
        summary = "Delete a specific product",
        description = "Delete a product by passing its ID.",
        tags = { "Product" },
        responses = {
            @ApiResponse(
                description = "Product successfully deleted",
                responseCode = "204",
                content = @Content
            ),
            @ApiResponse(description = "Product not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Invalid request format", responseCode = "400", content = @Content),
            @ApiResponse(description = "An unexpected error occurred", responseCode = "500", content = @Content)
        }
    )
    public @interface DeleteProductOptions{}

}
