package dev.cacassiano.image_processing_api.infra.secutity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import dev.cacassiano.image_processing_api.entity.User;
import dev.cacassiano.image_processing_api.repository.UserRepository;

// Classe reponsavel por consultar os users
@Component
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired 
    private UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username).orElseThrow(() -> new RuntimeException("Custom details deu errado"));
        // User especifico do spring boot;
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
    
}
