package com.ram.blog;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.entities.Roles;
import com.ram.blog.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

/*
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepo userRepo;
*/

    @Autowired
    private RoleRepo roleRepo;


    public static void main(String[] args) {
        SpringApplication.run(BlogAppApiApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
//		User user = userRepo.findById(1).orElse(null);
//		System.out.println(encoder.matches("anu123456", user.getPassword()));
        try {
            Roles role = new Roles();
            role.setId(ApiConstants.ADMIN_ROLE);
            role.setRoleName("ADMIN_USER");
            Roles role1 = new Roles();
            role1.setId(ApiConstants.NORMAL_ROLE);
            role1.setRoleName("NORMAL_USER");

            List<Roles> rolesList = List.of(role, role1);
            List<Roles> roles = this.roleRepo.saveAll(rolesList);
            roles.forEach(r -> System.out.println(r.getRoleName()));

        } catch (Exception e) {

			        }


    }

}
