package vn.hoangkhang.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vn.hoangkhang.laptopshop.domain.ProductMongo;

@Repository
public interface ProductMongoRepository extends MongoRepository<ProductMongo, String> {

    Page<ProductMongo> findAll(Pageable pageable);
}
