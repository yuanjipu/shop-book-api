package com.fh.shop.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shop.*.mapper")
public class ShopBookApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBookApiApplication.class, args);
    }

}
