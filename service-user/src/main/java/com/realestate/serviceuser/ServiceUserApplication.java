package com.realestate.serviceuser;

import com.realestate.serviceuser.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.createFromLine("Alex", "Mayham", "AlexMayham@example.com", "userpassword1", "0968854212", "CLIENT");
            userService.createFromLine("Amanda", "Coulman", "AmandaCoulman@example.com", "userpassword2", "0896642188", "REALTOR");
        };
    }
}
