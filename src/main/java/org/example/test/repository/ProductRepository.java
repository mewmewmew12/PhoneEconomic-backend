package org.example.test.repository;

import org.example.test.Dto.ProductDTO;
import org.example.test.Dto.projection.ProductInfo;
import org.example.test.entity.Category;
import org.example.test.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory_Id(Integer id);
//
////    List<Product> findBySale(Double sales);
//
//@Query("select p from Product p where p.discount is not null  ")
//List<Product> findByDiscountNotNull();

    @Query("select p from Product p where p.discount.percent is Null OR p.discount.percent = ?1")
    List<ProductInfo> findByDiscount_Percent(Integer percent);

//    @Query(nativeQuery = true,
//    value = "select * from Product p where (p.view is NULL or p.view = :view)" +
////            "and (p.nums_sold is null or p.nums_sold = :numsold)" +
//    "and (p.sales is null or p.sales = :sale)"+
//    "order by p.price DESC",
//    countQuery = "select count(*) from Product p  where (p.view is NULL or p.view = :view)"+
////    "and (p.nums_sold is NULL or p.nums_sold = :numsold)" +
//    "and (p.sales is null or p.sales = :sale)"+
//    "order by p.price DESC ")
//    Page<ProductDTO>  findSellProduct(@Param("view") Integer view ,@Param("sale") Double sale , Pageable pageable);

    @Query("select p from Product p where p.view = ?1 or p.sales = ?2")
    Page<Product> findByViewOrSales(Integer view, Double sales, Pageable pageable);


    @Query("select p from Product p where p.id = ?1")
    Page<ProductInfo> findProductInfoById(Integer id, Pageable pageable);

    @Query("""
            select p from Product p
            where upper(p.name) like upper(?1) and p.category.name = ?2 and p.discount.percent = ?3""")
    Page<Product> findByNameLikeIgnoreCaseAndCategory_NameAndDiscount_Percent(String name, String name1, Integer percent, Pageable pageable);

    @Query("select p from Product p where upper(p.category.name) = upper(?1)")
    List<Product> findByCategory_NameIgnoreCase(String name);

//    @Query("select p from Product p where p.category = ?1 and p.id = ?2")
//    List<Product> findByCategoryAndId(Category category, Integer productId);

    @Query("select p from Product p where p.category = ?1 and p.id <> ?2")
    List<Product> findByCategoryAndIdNot(Category category, Integer id);

    @Transactional
    @Modifying
    @Query("delete from Product p where p.category = ?1")
    void deleteByCategory(Category category);

}

