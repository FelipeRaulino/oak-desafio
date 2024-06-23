package com.oak_desafio.produto_cadastro_listagem.unittests.services;

import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.request.ProductRequestDTO;
import com.oak_desafio.produto_cadastro_listagem.data.dtos.v1.product.response.ProductResponseDTO;
import com.oak_desafio.produto_cadastro_listagem.domain.product.Product;
import com.oak_desafio.produto_cadastro_listagem.repositories.ProductRepository;
import com.oak_desafio.produto_cadastro_listagem.services.ProductService;
import com.oak_desafio.produto_cadastro_listagem.unittests.mocks.ProductMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    ProductMock productMock;

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        productMock = new ProductMock();
    }

    @Test
    void shouldBeAbleToGetAListOfProducts(){
        List<Product> products = productMock.mockEntityList();

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDTO> result = this.productService.listAllProducts();
        ProductResponseDTO firstProduct = result.getFirst();

        assertNotNull(result);
        assertNotNull(firstProduct);
        assertEquals(10, result.size());

        assertEquals("Product 0", firstProduct.name());
        assertEquals("Product description 0", firstProduct.description());
        assertEquals(23d, firstProduct.price());
        assertTrue(firstProduct.available());
    }

    @Test
    void shouldBeAbleToCreateANewProduct(){
        UUID id = UUID.randomUUID();

        Product product = productMock.mockEntity(id);
        ProductRequestDTO productRequestDTO = productMock.mockDTO();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.createProduct(productRequestDTO);

        assertNotNull(result);
        assertEquals("Carregador Portátil", result.name());
        assertEquals("Carregador portátil de 10,000mAh", result.description());
        assertEquals(75D, result.price());
        assertTrue(result.available());

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        Product capturedProduct = productCaptor.getValue();

        assertNotNull(capturedProduct);
        assertEquals("Carregador Portátil", capturedProduct.getName());
        assertEquals("Carregador portátil de 10,000mAh", capturedProduct.getDescription());
        assertEquals(75D, capturedProduct.getPrice());
        assertTrue(capturedProduct.getAvailable());
    }

    @Test
    void shouldBeAbleToUpdateAProduct(){
        UUID id = UUID.randomUUID();
        Product product = productMock.mockEntity(id);
        ProductRequestDTO productRequest = productMock.mockDTO();

        when(productRepository.findById(id.toString())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponseDTO result = productService.updateProduct(id.toString(), productRequest);

        assertNotNull(result);
        assertEquals("Carregador Portátil", result.name());
        assertEquals("Carregador portátil de 10,000mAh", result.description());
        assertEquals(75D, result.price());
        assertTrue(result.available());
    }

    @Test
    void shouldBeAbleToDeleteAProduct(){
        UUID id = UUID.randomUUID();
        Product product = productMock.mockEntity(id);

        when(productRepository.findById(id.toString())).thenReturn(Optional.of(product));

        this.productService.deleteProduct(id.toString());
    }


}
