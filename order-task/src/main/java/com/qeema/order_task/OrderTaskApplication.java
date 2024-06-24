package com.qeema.order_task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.qeema.order_task.user.Role;
import com.qeema.order_task.user.User;
import com.qeema.order_task.user.UserRepository;

@SpringBootApplication
public class OrderTaskApplication implements CommandLineRunner {


	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(OrderTaskApplication.class, args);
	}

	public void run(String... args){
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount){
			User user  = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setLastname("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}

}
