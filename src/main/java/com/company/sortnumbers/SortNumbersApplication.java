package com.company.sortnumbers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SortNumbersApplication {

    public static void main(String... args) {
        new SpringApplicationBuilder(SortNumbersApplication.class)
            .headless(false)
            .run(args);
    }

}
