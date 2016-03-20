package com.codesanook.example;

import com.codesanook.example.model.Product;
import com.codesanook.example.model.Review;
import com.codesanook.example.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
@SpringBootApplication
public class HomeController {

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HomeController.class, args);
    }

    @Transactional
    @RequestMapping("/test-review")
    @ResponseBody
    public String testReview() {
        User user = new User();
        user.setName("Pat");
        entityManager.persist(user);

        Product product = new Product();
        product.setName("Product 1");
        entityManager.persist(product);

        Review review = new Review();
        review.setContent("new review");


        review.setUser(user);
        user.getReviews().add(review);

        review.setProduct(product);
        product.getReviews().add(review);
        entityManager.persist(review);

        String result = String.format("user id %d, product id %d, review id %d",
                user.getId(), product.getId(), review.getId());
        return result;
    }


}