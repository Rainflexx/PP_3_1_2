package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findByUsername("ROLE_ADMIN")==null){
            Role adminRole = new Role();
            adminRole.setRole("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
        if(roleRepository.findByUsername("ROLE_USER")==null){
            Role userRole = new Role();
            userRole.setRole("ROLE_USER");
            roleRepository.save(userRole);
        }
        if (userRepository.findByUsername("admin")==null){
            User user =new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEmail("admin_mail@gamil.com");
            user.getRoles().add(roleRepository.findByUsername("ROLE_ADMIN"));
            user.getRoles().add(roleRepository.findByUsername("ROLE_USER"));
        }

    }
}
