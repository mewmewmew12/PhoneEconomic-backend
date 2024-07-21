package org.example.test;

import com.github.javafaker.Faker;
import org.example.test.entity.*;
import org.example.test.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
//@SpringBootTest
public class ProductTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository ;
    @Autowired
    private RateRepository rateRepository;
    @Test
    void save_category(){
        Category phone = new Category();
        phone.setName("Phones");
        phone.setThumbnail("https://cdn-icons-png.freepik.com/512/0/191.png");

        Category computer = new Category();
        computer.setName("Computers");
        computer.setThumbnail("https://cdn-icons-png.freepik.com/512/0/191.png");

        Category smartwatch = new Category();
        smartwatch.setName("SmartWatch");
        smartwatch.setThumbnail("https://cdn-icons-png.freepik.com/512/0/191.png");

        Category camera = new Category();
        camera.setName("Camera");
        camera.setThumbnail("https://cdn-icons-png.freepik.com/512/0/191.png");

        Category headPhone = new Category();
        headPhone.setName("HeadPhones");
        headPhone.setThumbnail("https://cdn-icons-png.freepik.com/512/0/191.png");

        Category game = new Category();
        game.setName("Gaming");
        game.setThumbnail("https://cdn-icons-png.freepik.com/512/0/191.png");

        categoryRepository.saveAll(List.of(phone,computer,smartwatch,camera,headPhone,game));

    }
    @Test
    void save_product() {
        Faker faker = new Faker();
        Random rd = new Random();
        List<String> nameProducts = List.of("Samsung Galaxy s22 ultra", "Mac Book", "Apple Watch", "Máy ảnh Canon EOS", "Tai nghe Bluetooth Chụp Tai Zadez GP-803B", "Màn hình Gaming Acer Nitro");
        List<Category> categoryList = categoryRepository.findAll();
        for (int i = 0; i < 36; i++) {
            Product product = Product.builder()
                    .name(nameProducts.get(rd.nextInt(nameProducts.size())))
                    .thumbnail("https://onewaymobile.vn/images/products/2023/04/17/large/samsung-s22-5_1681738728.webp")
                    .price((double) rd.nextInt(1000))
                    .category(categoryList.get(rd.nextInt(categoryList.size())))
                    .content("Content Product " + (i + 1 + 45))
                    .description(" description Product " + (i + 1 + 45))
                    .detail("Detail for Product " + (i + 1 + 45))
                    .nums(rd.nextInt(100))
                    .build();
            productRepository.save(product);
        }
    }
    // them ảnh cho product
    @Test
    void save_imgProduct(){
        List<String> listImg = new ArrayList<>(List.of("https://cdn-v2.didongviet.vn/files/media/catalog/product/s/a/samsung-galaxy-s22-128gb-didongviet_1.jpg",
                "https://gamalaptop.vn/wp-content/uploads/2021/09/Acer-Nitro-5-2020-i5-10300H-GTX-1650-01.jpg",
                "https://cdn.tgdd.vn/Products/Images/7077/321816/mi-band-8-pro-den-tn2-1-600x600.jpg",
                "https://cdn.vjshop.vn/may-anh/mirrorless/canon/canon-eos-r100/lens-18-45mm/canon-eos-r100-lens-18-45mm.jpg",
                "https://cdn.nguyenkimmall.com/images/detailed/791/10051622-tai-nghe-blth-philips-tah5205bk-00-den-1.jpg",
                "https://seve7.vn/wp-content/themes/yootheme/cache/3-7-7daa4e73.jpeg"
        ));
        List<Product> productList1 = productRepository.findByCategory_Id(1);
        for (int i = 0; i < productList1.size(); i++) {
            productList1.get(i).setThumbnail("https://cdn-v2.didongviet.vn/files/media/catalog/product/s/a/samsung-galaxy-s22-128gb-didongviet_1.jpg");
        }
        List<Product> productList2 = productRepository.findByCategory_Id(1);
        for (int i = 0; i < productList1.size(); i++) {
            productList2.get(i).setThumbnail("https://gamalaptop.vn/wp-content/uploads/2021/09/Acer-Nitro-5-2020-i5-10300H-GTX-1650-01.jpg");
        }
        List<Product> productList3 = productRepository.findByCategory_Id(1);
        for (int i = 0; i < productList1.size(); i++) {
            productList3.get(i).setThumbnail("https://cdn.tgdd.vn/Products/Images/7077/321816/mi-band-8-pro-den-tn2-1-600x600.jpg");
        }
        List<Product> productList4 = productRepository.findByCategory_Id(1);
        for (int i = 0; i < productList1.size(); i++) {
            productList4.get(i).setThumbnail("https://cdn.vjshop.vn/may-anh/mirrorless/canon/canon-eos-r100/lens-18-45mm/canon-eos-r100-lens-18-45mm.jpg");
        }
        List<Product> productList5 = productRepository.findByCategory_Id(1);
        for (int i = 0; i < productList1.size(); i++) {
            productList5.get(i).setThumbnail("https://cdn.nguyenkimmall.com/images/detailed/791/10051622-tai-nghe-blth-philips-tah5205bk-00-den-1.jpg");
        }
        List<Product> productList6 = productRepository.findByCategory_Id(1);
        for (int i = 0; i < productList1.size(); i++) {
            productList6.get(i).setThumbnail("https://seve7.vn/wp-content/themes/yootheme/cache/3-7-7daa4e73.jpeg");
        }
    }
    @Test
    void save_Numsold(){
        Random rd = new Random();
        List<Product> products = productRepository.findByCategory_Id(1);
        for (Product product : products) {
            product.setNumsSold(rd.nextInt(10));
        }
        List<Product> products1 = productRepository.findByCategory_Id(2);
        for (Product product1 : products) {
            product1.setNumsSold(rd.nextInt(10));
        }
        List<Product> products2 = productRepository.findByCategory_Id(3);
        for (Product product2 : products) {
            product2.setNumsSold(rd.nextInt(10));
        }
        List<Product> products3 = productRepository.findByCategory_Id(4);
        for (Product product3 : products) {
            product3.setNumsSold(rd.nextInt(10));
        }
        List<Product> products4 = productRepository.findByCategory_Id(5);
        for (Product product4 : products) {
            product4.setNumsSold(rd.nextInt(10));
        }
        List<Product> products5 = productRepository.findByCategory_Id(6);
        for (Product product5 : products) {
            product5.setNumsSold(rd.nextInt(10));
        }
    }
    @Test
    void save_Discount(){
        List<Discount> discounts = List.of(
                new Discount(1,5),
                new Discount(2,10),
                new Discount(3,15),
                new Discount(4,20),
                new Discount(5,25),
                new Discount(6,30),
                new Discount(7,0)
        );
        discountRepository.saveAll(discounts);

    }
    @Test
    void discout_Product(){
        Random rd = new Random();
        List<Discount> discounts = discountRepository.findAll();
        List<Product> products = productRepository.findAll();
        for (int i = 0; i < products.size(); i++) {
            int randomIndex = rd.nextInt(discounts.size());
            Discount randomDiscount = discounts.get(randomIndex);
            products.get(i).setDiscount(randomDiscount);
        }
    }
    @Test
    void save_comment(){
        Random rd = new Random();
        Faker faker = new Faker();
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll();
        for (int i = 0 ; i < 36 ; i++){
            Comment comment = Comment.builder()
                    .content(faker.leagueOfLegends().rank())
                    .user(users.get(rd.nextInt(users.size())))
                    .product(products.get(rd.nextInt(products.size())))
                    .build();
            commentRepository.save(comment);
        }
    }
    @Test
    void save_rate() {
        List<Rate> rates = List.of(
                new Rate(1, 1),
                new Rate(2, 2),
                new Rate(3, 3),
                new Rate(4, 4),
                new Rate(5, 5)
        );
        rateRepository.saveAll(rates);
    }
    @Test
    void rate_Product(){
        Random rd = new Random();
        List<Rate> rates = rateRepository.findAll();
        List<Product> products = productRepository.findAll();
        for (int i = 0; i < products.size(); i++) {
            int randomIndex = rd.nextInt(rates.size());
            Rate randomrate = rates.get(randomIndex);
            products.get(i).setRate(randomrate);
        }
    }
}
