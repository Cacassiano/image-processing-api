package dev.cacassiano.image_processing_api.infra.secutity;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cacassiano.image_processing_api.entity.User;
import dev.cacassiano.image_processing_api.infra.secutity.dto.LoginDTO;
import dev.cacassiano.image_processing_api.infra.secutity.dto.RegisterDTO;
import dev.cacassiano.image_processing_api.infra.secutity.dto.TokenDTO;
import dev.cacassiano.image_processing_api.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TokenService tkService;
    @PostMapping("/login")
    public ResponseEntity<TokenDTO > loginUser(@RequestBody LoginDTO login) {
        User user = repository.findByEmail(login.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (encoder.matches(login.password(), user.getPassword())) {
            String token = tkService.createToken(user.getEmail());
            return ResponseEntity.ok().body(new TokenDTO(token));
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> createUser(@RequestBody RegisterDTO registerDTO) {
        Optional<User> user= this.repository.findByEmail(registerDTO.email());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        if (registerDTO.email() == null || registerDTO.password() == null || registerDTO.username() == null) {
            return ResponseEntity.badRequest().build();           
        }
        registerDTO = new RegisterDTO(encoder.encode(registerDTO.password()), registerDTO.email(), registerDTO.username());

        User nUser = new User(registerDTO);
        repository.save(nUser);

        String token = tkService.createToken(nUser.getEmail());
        return ResponseEntity.ok().body(new TokenDTO(token));
    }
}
