package ru.geekbrains.market2.msproduct.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.market2.mscore.exceptions.ProductNotFoundException;
import ru.geekbrains.market2.msproduct.model.dtos.ProductDto;
import ru.geekbrains.market2.msproduct.model.entities.Product;
import ru.geekbrains.market2.msproduct.repositories.ProductRepository;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public Optional<ProductDto> getById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public List<ProductDto> getByIds(List<Long> ids) {
        return productRepository.findByIdIn(ids).stream().map(this::toDto).collect(Collectors.toList());
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductDto> getAll(Specification<Product> spec, Integer page,
                                   Integer size,
                                   Optional<String[]> sort) {
        Page<Product> p;
        if (sort.isPresent()) {
            List o = new ArrayList<Sort.Order>();
            for (int i = 0; i < sort.get().length; i++) {
                String[] s = sort.get()[i].split(",");
                if (s.length >= 2) {
                    o.add(new Sort.Order(Sort.Direction.fromString(s[1]), s[0]));
                } else o.add(new Sort.Order(Sort.DEFAULT_DIRECTION, s[0]));
            }
            p = productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(o)));
        } else
            p = productRepository.findAll(PageRequest.of(page, size));
        if (p.getContent().size() > 0)
            return p.map(ProductDto::new);
        else
            throw new ProductNotFoundException("");
    }

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    private ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product toEntity(ProductDto productDto) throws ParseException {
        return modelMapper.map(productDto, Product.class);
    }
}
