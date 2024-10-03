// package vn.hoangkhang.laptopshop.service.specification;

// import java.util.List;

// import org.springframework.data.jpa.domain.Specification;

// import vn.hoangkhang.laptopshop.domain.Product;
// import vn.hoangkhang.laptopshop.domain.Product_;

// public class ProductSpecs {
// public static Specification<Product> nameLike(String name) {
// return (root, query, criteriaBuilder) ->
// criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
// }

// // Case 1:
// public static Specification<Product> priceGreaterThanOrEqualTo(Double price)
// {
// return (root, query, criteriaBuilder) ->
// criteriaBuilder.ge(root.get(Product_.PRICE), price);
// }

// // Case 2:
// public static Specification<Product> priceLessThanOrEqualTo(Double price) {
// return (root, query, criteriaBuilder) ->
// criteriaBuilder.le(root.get(Product_.PRICE), price);
// }

// // Case 3:
// public static Specification<Product> factoryEqualTo(String factory) {
// return (root, query, criteriaBuilder) ->
// criteriaBuilder.equal(root.get(Product_.FACTORY), factory);
// }

// // Case 4:
// public static Specification<Product> factoryIn(List<String> factory) {
// return (root, query, criteriaBuilder) ->
// criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
// }

// public static Specification<Product> targetIn(List<String> target) {
// return (root, query, criteriaBuilder) ->
// criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
// }

// // Case 5:
// public static Specification<Product> priceRange(Double priceMin, Double
// priceMax) {
// // return (root, query, criteriaBuilder) ->
// criteriaBuilder.between(root.get(Product_.PRICE), priceMin, priceMax);
// return (root, query, criteriaBuilder) -> criteriaBuilder.and(
// criteriaBuilder.ge(root.get(Product_.PRICE), priceMin),
// criteriaBuilder.le(root.get(Product_.PRICE), priceMax)
// );
// }

// // Case 6:
// public static Specification<Product> priceMultiRanges(Double priceMin, Double
// priceMax) {
// return (root, query, criteriaBuilder) ->
// criteriaBuilder.between(root.get(Product_.PRICE), priceMin, priceMax);
// }
// }
