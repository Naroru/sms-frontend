package com.example.mssfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SmsFrontendApplication {


  public static void main(String[] args) {
    SpringApplication.run(SmsFrontendApplication.class, args);
  }
/*

     @Bean
    public CommandLineRunner loadData(CustomerRepository repository) {
       return (args) -> {
         // save a couple of customers
         repository.save(new Customer("Jack", "Bauer", CustomerStatus.CORRECRT));
         repository.save(new Customer("Chloe", "O'Brian", CustomerStatus.CORRECRT));
         repository.save(new Customer("Kim", "Bauer", CustomerStatus.CORRECRT));
         repository.save(new Customer("David", "Palmer", CustomerStatus.INCORRECT));

       };
     }*/

}


