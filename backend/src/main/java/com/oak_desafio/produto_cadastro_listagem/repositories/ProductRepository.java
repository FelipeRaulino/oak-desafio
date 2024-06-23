package com.oak_desafio.produto_cadastro_listagem.repositories;

import com.oak_desafio.produto_cadastro_listagem.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
