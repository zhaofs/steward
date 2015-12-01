package com.yihui.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/dubbo/consumer.xml")
public class TestApplication {

	public static void main(String[] args) {

		SpringApplication.run(TestApplication.class, args);

	}
}
