package ru.geekbrains.market2.msproduct;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.market2.msproduct.model.dtos.ProductDto;
import ru.geekbrains.market2.msproduct.model.entities.Product;
import ru.geekbrains.market2.msproduct.repositories.ProductRepository;
import ru.geekbrains.market2.msproduct.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {ProductRepository.class, ProductService.class, ModelMapper.class})

public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testGetProduct() {
        Product demoProduct = new Product();
        demoProduct.setTitle("Snickers");
        demoProduct.setPrice(BigDecimal.valueOf(50));
        demoProduct.setId(10L);

        Mockito
                .doReturn(Optional.of(demoProduct))
                .when(productRepository)
                .findById(10L);

        Product p = productService.findProductById(10L).get();
        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(10L));
        Assertions.assertEquals("Snickers", p.getTitle());

        Product demoProduct1 = new Product();
        demoProduct1.setTitle("Bounty");
        demoProduct1.setPrice(BigDecimal.valueOf(70));
        demoProduct1.setId(20L);

        List<Product> demoList = new ArrayList<>();
        demoList.add(demoProduct);
        demoList.add(demoProduct1);

        Mockito
                .doReturn(demoList)
                .when(productRepository)
                .findByIdIn(Arrays.asList(10L, 20L));

        List<ProductDto> productList = productService.getByIds(Arrays.asList(10L, 20L));
        Mockito.verify(productRepository, Mockito.times(1)).findByIdIn(ArgumentMatchers.eq(Arrays.asList(10L, 20L)));
        Assertions.assertEquals(2, productList.size());
    }
}
