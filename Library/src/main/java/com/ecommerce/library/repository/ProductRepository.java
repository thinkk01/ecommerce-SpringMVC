package com.ecommerce.library.repository;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select e from Product e where e.deleted=false and e.activated=true")
    List<Product> getAllProducts();

    @Query(value = "select p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted from products p where p.is_deleted = false and p.is_activated = true limit 4", nativeQuery = true)
    List<Product> listViewProducts();

    @Query("select p from Product p inner join Category c on c.id = p.id where p.category.id = ?1")
    List<Product> getRelatedProducts(Long categoryId);
    @Query("select p from Product p inner join Category c on c.id = p.category.id where c.id=?1 and p.activated=true and p.deleted=false")
    List<Product> getAllProductInCategory(Long id);
    @Query("select p from Product p where p.activated=true and p.deleted=false order by p.costPrice desc")
    List<Product> filterHighPrice();
    @Query("select p from Product p where p.activated=true and p.deleted=false order by p.costPrice ")
    List<Product> filterLowPrice();

}
