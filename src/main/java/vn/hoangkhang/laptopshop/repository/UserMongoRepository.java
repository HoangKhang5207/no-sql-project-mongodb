package vn.hoangkhang.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import vn.hoangkhang.laptopshop.domain.UserMongo;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongo, String> {

    boolean existsByEmail(String email);

    UserMongo findByEmail(String email);

    @Query(value = "{ 'cart._id': ?0, 'cart.cartDetails.product._id': ?1 }", fields = "{ 'cart.cartDetails.$': 1, '_id': 0 }")
    Optional<UserMongo> findCartDetailByCartAndProduct(String cartId, String productId);

    @Query(value = "{ 'cart.cartDetails._id': ?0 }", fields = "{ 'cart.cartDetails.$': 1, '_id': 0 }")
    Optional<UserMongo> findCartDetailById(String cartDetailId);

}
