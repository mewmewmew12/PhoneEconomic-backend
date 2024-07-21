package org.example.test;

import org.example.test.entity.Role;
import org.example.test.entity.User;
import org.example.test.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
public class UserTest {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void save_roles() {
        List<Role> roles = List.of(
                new Role(null, "ADMIN"),
                new Role(null, "USER"),
                new Role(null, "AUTHOR")
        );
        roleRepository.saveAll(roles);
    }

    @Test
    void save_user1() {
        Role userRole = roleRepository.findByName("USER").orElse(null);
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
        Role authorRole = roleRepository.findByName("AUTHOR").orElse(null);

        List<User> users = List.of(
                new User(null, "Bui Hien", encoder.encode("123"), "hienbui@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Ha Noi", "12345", true, LocalDateTime.now(), List.of(userRole, authorRole)),
                new User(null, "Bui San", encoder.encode("123"), "sanbui@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Thai Bình", "123456", true, LocalDateTime.now(), List.of(userRole, adminRole)),
                new User(null, "Bui Thắng", encoder.encode("123"), "thangbui@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Ha Nam", "1234567", true, LocalDateTime.now(), List.of(adminRole, userRole, authorRole)),
                new User(null, "Bui Trung", encoder.encode("123"), "trungbui@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Phu Ly", "12345678", true, LocalDateTime.now(), List.of(authorRole)),
                new User(null, "Bui Hieu", encoder.encode("123"), "hieubui@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Tp Ho Chi Minh", "12345", true, LocalDateTime.now(), List.of(userRole, authorRole)),
                new User(null, "Bui Phong", encoder.encode("123"), "phongbui@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Ha Noi", "123456789", true, LocalDateTime.now(), List.of(userRole)),
                new User(null, "mewmew", encoder.encode("123"), "mewmew@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Ha Noi", "12345", true, LocalDateTime.now(), List.of(userRole, authorRole)),
                new User(null, "gaugau", encoder.encode("123"), "gaugau@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Thai Bình", "123456", true, LocalDateTime.now(), List.of(userRole, adminRole)),
                new User(null, "zozo", encoder.encode("123"), "zozo@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Ha Nam", "1234567", true, LocalDateTime.now(), List.of(adminRole, userRole, authorRole)),
                new User(null, "yiyi", encoder.encode("123"), "yiyi@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Phu Ly", "12345678", true, LocalDateTime.now(), List.of(authorRole)),
                new User(null, "yoyo", encoder.encode("123"), "yoyo@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Tp Ho Chi Minh", "12345", true, LocalDateTime.now(), List.of(userRole, authorRole)),
                new User(null, "kaka", encoder.encode("123"), "kaka@gmail.com", "https://img.pokemondb.net/artwork/large/mewtwo.jpg", "Ha Noi", "123456789", true, LocalDateTime.now(), List.of(userRole))
        );
        userRepository.saveAll(users);

    }
}
