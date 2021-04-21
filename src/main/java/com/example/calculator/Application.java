package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 主程序
 * @author 李柯凡
 *
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	/**
	 * 为了打包springboot项目
	 */
	@Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
