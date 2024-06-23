package com.oak_desafio.produto_cadastro_listagem.controllers;

import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.request.ProductRequestDTO;
import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.response.ProductResponseDTO;
import com.oak_desafio.produto_cadastro_listagem.services.ProductService;
import com.oak_desafio.produto_cadastro_listagem.utils.swagger.ProductControllerSwaggerOptions;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/product")
@Tag(name = "Product", description = "Endpoints for managing product operations")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    @ProductControllerSwaggerOptions.GetAllProductsOptions
    private ResponseEntity<List<ProductResponseDTO>> listAllProducts(){
        List<ProductResponseDTO> productsList = this.productService.listAllProducts();

        return ResponseEntity.ok().body(productsList);
    }

    @PostMapping
    @ProductControllerSwaggerOptions.CreateProductOptions
    private ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO productRequest
    ){
        ProductResponseDTO product = this.productService.createProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping(value = "/{id}")
    @ProductControllerSwaggerOptions.UpdateProductOptions
    private ResponseEntity<ProductResponseDTO> updateProduct(
            @RequestBody ProductRequestDTO productRequest,
            @PathVariable(value = "id") String id
    ){
        ProductResponseDTO product = this.productService.updateProduct(id, productRequest);

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(value = "/{id}")
    @ProductControllerSwaggerOptions.DeleteProductOptions
    private ResponseEntity<?> deleteProduct(
            @PathVariable(value = "id") String id
    ){
        this.productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

}
