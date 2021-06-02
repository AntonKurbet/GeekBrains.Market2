package ru.geekbrains.market2.msproduct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market2.mscore.exceptions.InvalidPageException;
import ru.geekbrains.market2.mscore.exceptions.ProductNotFoundException;
import ru.geekbrains.market2.msproduct.model.dtos.ProductDto;
import ru.geekbrains.market2.msproduct.model.entities.Product;
import ru.geekbrains.market2.msproduct.repositories.specifications.ProductsSpecifications;
import ru.geekbrains.market2.msproduct.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<ProductDto> getAll(
            @RequestParam MultiValueMap<String,String> params,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String[] sort) {
        if (page < 1) throw new InvalidPageException(page.toString());
        return productService.getAll(ProductsSpecifications.build(params), page - 1, size, Optional.of(sort));
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id).orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @GetMapping("/ids")
    List<ProductDto> findProductsByIds(@RequestParam List<Long> ids) {
        return productService.getByIds(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product add(@RequestBody Product product) {
        return productService.add(product);
    }

    @PutMapping
    public Product update(@RequestBody Product product) {
        return productService.saveOrUpdate(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

//    @ExceptionHandler
//    public ResponseEntity<ProductErrorResponse> handleException(ProductNotFoundException e) {
//        ProductErrorResponse per = new ProductErrorResponse();
//        per.setStatus(HttpStatus.NOT_FOUND.value());
//        per.setMessage(e.getMessage());
//        per.setTimestamp(System.currentTimeMillis());
//        return new ResponseEntity<>(per,HttpStatus.NOT_FOUND);
//    }
}
