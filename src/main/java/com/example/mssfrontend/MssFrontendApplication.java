package com.example.mssfrontend;

import com.example.mssfrontend.model.employee.entity.Customer;
import com.example.mssfrontend.model.employee.entity.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MssFrontendApplication {



    public static void main(String[] args) {
      SpringApplication.run(MssFrontendApplication.class, args);
    }


      @Bean
    public CommandLineRunner loadData(CustomerRepository repository) {
      return (args) -> {
        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));


      };
    }
  }


