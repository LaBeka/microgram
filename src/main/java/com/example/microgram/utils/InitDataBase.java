package com.example.microgram.utils;

import com.example.microgram.dao.LikeDao;
import com.example.microgram.dao.RoleDao;
import com.example.microgram.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
public class InitDataBase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init(
            LikeDao likeDao){
        return (args) -> {
//            roleDao.dropTable();
//            typeDao.dropTable();
//            restDao.dropTable();
//            foodDao.dropTable();
//            orderDao.dropTable();
//            customerDao.dropTable();
            likeDao.createTable();
//            customerDao.createTable();
//            roleDao.createTable();
//            restDao.createTable();
//            foodDao.createTable();
//            typeDao.createTable();
//            orderDao.createTable();

//            CustomerDto customer = new CustomerDto(
//                    GenerateData.randomPersonName(),
//                    GenerateData.randomEmail(),
//                    passwordEncoder.encode("qwerty"));
//
//            Customer cust = Customer.builder()
//                    .customerName(customer.getCustomerName())
//                    .email(customer.getEmail())
//                    .password(passwordEncoder.encode(customer.getPassword()))
//                    .enabled(Boolean.TRUE)
//                    .roles(List.of(Roles.USER))
//                    .build();
//
//            customerDao.createNewUser(customer);
//            Optional<Customer> c1 = customerDao.ifIdenticalUserExists(customer);
//
//            roleDao.setRoleOfUser(cust, Roles.USER);
//            RestaurantDto restaurant = new RestaurantDto(
//                    GenerateData.randomPlace().name,
//                    GenerateData.randomPlace().description);
//            restDao.createNewRest(restaurant);
//
//            Optional<Restaurant> rest = restDao.ifIdenticalRestExists(restaurant);
//            Restaurant rest2 = rest.get();
//
//            FoodDtoSecond food = new FoodDtoSecond(GenerateData.randomDish().name,
//                    GenerateData.randomDish().type,
//                    ++price,
//                    rest2.getRest_id());
//            food.validateFoodData();
//            foodDao.createNewFood(food);
//
//            Optional<Food> foodID = foodDao.getFoodsID(food);
//            Food food1 = foodID.get();
//            orderDao.createNewOrder(
//                    c1.get().getCustomerId(),
//                    food1.getFood_id(),
//                    rest2.getRest_id());
        };
    }
}
