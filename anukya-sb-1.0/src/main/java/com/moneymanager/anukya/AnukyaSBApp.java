package com.moneymanager.anukya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.moneymanager.anukya.*" })
public class AnukyaSBApp {

	public static void main(String[] args) {
		SpringApplication.run(AnukyaSBApp.class, args);
	}

}
