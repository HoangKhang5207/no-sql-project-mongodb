package vn.hoangkhang.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import vn.hoangkhang.laptopshop.domain.ProductMongo;

@Repository
public interface ProductMongoRepository extends MongoRepository<ProductMongo, String> {

    Page<ProductMongo> findAll(Pageable pageable);

    @Aggregation(pipeline = {
            "{ $match: { 'reviews': { $ne: [] } } }",
            "{ $unwind: '$reviews' }",
            "{ $count: 'totalReviews' }"
    })
    Long countAllNonEmptyReviews();

    @Query(value = "{ 'reviews._id': ?0 }", fields = "{ 'reviews.$': 1, _id: 0 }")
    Optional<ProductMongo> findReviewById(String reviewId);
}
