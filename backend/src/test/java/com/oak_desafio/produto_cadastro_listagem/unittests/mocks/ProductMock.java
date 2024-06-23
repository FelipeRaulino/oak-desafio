package com.oak_desafio.produto_cadastro_listagem.unittests.mocks;

import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.request.ProductRequestDTO;
import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.response.ProductResponseDTO;
import com.oak_desafio.produto_cadastro_listagem.domain.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductMock {

    public Product mockEntity(UUID id){
        Product product = new Product();

        product.setId(id.toString());
        product.setName("Carregador Portátil");
        product.setDescription("Carregador portátil de 10,000mAh");
        product.setPrice(75D);
        product.setAvailable(true);
        product.setCreatedAt(LocalDateTime.now());

        return product;
    }

    public ProductRequestDTO mockDTO(){
        return new ProductRequestDTO(
          "Carregador Portátil",
                "Carregador portátil de 10,000mAh",
                75D,
                true
        );
    }

    public List<Product> mockEntityList(){
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            Product product = new Product();

            product.setId(UUID.randomUUID().toString());
            product.setName("Product " +i);
            product.setDescription("Product description " +i);
            product.setPrice(23d);
            product.setAvailable(true);
            product.setCreatedAt(LocalDateTime.now());

            products.add(product);
        }

        return products;
    }

    public List<ProductResponseDTO> mockDTOList(){
        List<Product> products = mockEntityList();

        return products.stream().map(product -> {
            return new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getAvailable(),
                    product.getCreatedAt()
            );
        }).toList();
    }

}
