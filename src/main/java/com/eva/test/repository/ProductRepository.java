package com.eva.test.repository;

import com.eva.test.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(@Param("name") String name,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);
}

