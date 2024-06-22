package com.oak_desafio.produto_cadastro_listagem.services;

import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.request.ProductRequestDTO;
import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.response.ProductResponseDTO;
import com.oak_desafio.produto_cadastro_listagem.domain.Product;
import com.oak_desafio.produto_cadastro_listagem.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductResponseDTO> listAllProducts(){
        List<Product> productsList = this.productRepository.findAll();

        return productsList
                .stream()
                .map(product ->
                        new ProductResponseDTO(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getAvailable(),
                            product.getCreatedAt()
                )).toList();
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequest){
        Product product = new Product();

        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setAvailable(productRequest.available());
        product.setCreatedAt(LocalDateTime.now());

        this.productRepository.save(product);

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailable(),
                product.getCreatedAt()
        );
    }

    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequest){
        Product productDB = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id: " + id + "not found!"));

        productDB.setName(productRequest.name());
        productDB.setDescription(productRequest.description());
        productDB.setPrice(productRequest.price());
        productDB.setAvailable(productRequest.available());

        this.productRepository.save(productDB);

        return new ProductResponseDTO(
                productDB.getId(),
                productDB.getName(),
                productDB.getDescription(),
                productDB.getPrice(),
                productDB.getAvailable(),
                productDB.getCreatedAt()
        );
    }

    public void deleteProduct(String id){
        Product productDB = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id: " + id + "not found!"));

        this.productRepository.delete(productDB);
    }

}
