package ru.geekbrains.market2.routing.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market2.routing.dtos.ProductDto;

import java.util.List;

@FeignClient("ms-product")
@RequestMapping("/api/v1/products")
public interface ProductClient {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    ProductDto getById(@PathVariable Long id);

    @GetMapping("/ids")
    List<ProductDto> findProductsByIds(@RequestParam List<Long> ids);

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto add(@RequestBody ProductDto product);

    @PutMapping()
    ProductDto update(@RequestBody ProductDto product);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
