package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JpashopApplication {

	public static void main(String[] args) {
		
		Hello hello = new Hello();
		hello.setData("test");
		
		String data = hello.getData();
		System.out.println("test : " + data);
		
		SpringApplication.run(JpashopApplication.class, args);
	}

}
