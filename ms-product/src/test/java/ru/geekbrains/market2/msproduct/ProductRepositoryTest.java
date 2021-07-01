package ru.geekbrains.market2.msproduct;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.geekbrains.market2.msproduct.model.entities.Category;
import ru.geekbrains.market2.msproduct.model.entities.Product;
import ru.geekbrains.market2.msproduct.repositories.ProductRepository;
import ru.geekbrains.market2.msproduct.repositories.specifications.ProductsSpecifications;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
//@SpringBootTest(classes=ProductRepository.class)

public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void initDbTest() {
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(2, productList.size());
        List<Long> ids = Arrays.asList(1L, 2L);
        productList = productRepository.findByIdIn(ids);
        Assertions.assertEquals(2, productList.size());
    }

    @Test
    public void productRepositoryTest() {
        Product product = new Product();
        product.setTitle("Test1");
        product.setPrice(BigDecimal.valueOf(100));
        Product product2 = new Product();
        product2.setTitle("Test2");
        product2.setPrice(BigDecimal.valueOf(1000));
        entityManager.persist(product);
        entityManager.persist(product2);
        entityManager.flush();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Test%");
        List<Product> productList = productRepository.findAll(ProductsSpecifications.build(params));

        Assertions.assertEquals(2, productList.size());
    }
}
