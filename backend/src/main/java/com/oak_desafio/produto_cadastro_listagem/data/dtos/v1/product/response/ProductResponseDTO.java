package com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.response;

import java.time.LocalDateTime;

public record ProductResponseDTO(
        String id,
        String name,
        String description,
        Double price,
        Boolean available,
        LocalDateTime createdAt
) {
}
