package com.tqkien03.userservice;

import com.tqkien03.userservice.role.ERole;
import com.tqkien03.userservice.role.Role;
import com.tqkien03.userservice.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runner(RoleRepository roleRepository) {
//		return args -> {
//			if (roleRepository.findByName(ERole.USER).isEmpty()) {
//				roleRepository.save(Role.builder().name(ERole.USER).build());
//			}
//		};
//	}
}
