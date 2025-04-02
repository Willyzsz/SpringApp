package willyworking.com.willy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@ComponentScan(basePackages = "willyworking.com")
@EnableJpaRepositories(basePackages = "willyworking.com.repositories")
@EntityScan(basePackages = "willyworking.com.models")
@EnableMethodSecurity
public class WillyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WillyApplication.class, args);
	}

}
