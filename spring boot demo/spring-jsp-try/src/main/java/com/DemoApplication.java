package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hisen.dao")
public class DemoApplication {

//来自 hisenyuan 的https://github.com/hisenyuan/SSM_BookSystem
//这里尝试将其改造为springboot项目，以感受传统springMVC与spring boot的异同
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
