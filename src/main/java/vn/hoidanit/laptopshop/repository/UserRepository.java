package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    // Không có nhiều sự khác biệt giữa findOne, findBy, findAll. Để Spring biết
    // được là trả về nhiều hay một thì tùy thuộc vào Data Type trả về.
    // Nếu data không là "duy nhất", không nên dùng findOne...
    // Có thể dùng 2 cách sau:
    // + findFirstByEmail
    // + findTop1ByEmail để lấy 1 kết quả (giới hạn 1 kết quả trả về)
    List<User> findAllByEmail(String email);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
