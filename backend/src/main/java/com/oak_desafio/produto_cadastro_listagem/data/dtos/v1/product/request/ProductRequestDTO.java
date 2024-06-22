package com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.request;

public record ProductRequestDTO(
        String name,
        String description,
        Double price,
        Boolean available
) {
}
