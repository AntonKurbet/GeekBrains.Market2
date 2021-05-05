package ru.geekbrains.market2.msproduct.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.geekbrains.market2.msproduct.model.entities.Product;


public class ProductsSpecifications {

    private static Specification<Product> costGreaterOrEqualThen(int minPrice) {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minPrice);
    }

    private static Specification<Product> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%",title));
    }
    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);
        if (params.containsKey("min_cost") && !params.getFirst("min_cost").isBlank()) {
            spec = spec.and(costGreaterOrEqualThen(Integer.parseInt(params.getFirst("min_cost"))));
        }
        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(titleLike(params.getFirst("title")));
        }
        return spec;
    }
}
