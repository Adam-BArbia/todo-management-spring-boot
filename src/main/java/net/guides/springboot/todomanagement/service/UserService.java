package net.guides.springboot.todomanagement.service;

import net.guides.springboot.todomanagement.model.User;
import net.guides.springboot.todomanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public java.util.List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void updateRoleAndEnabled(Long id, String role, boolean enabled) {
        User u = findById(id);
        if (u == null) return;
        u.setRole(role);
        u.setEnabled(enabled);
        userRepository.save(u);
    }

    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = findByUsername(username);
        if (user == null) {
            return false;
        }

        if (currentPassword != null && !encoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public void updateLastLogin(String username) {
        User user = findByUsername(username);
        if (user != null) {
            user.setLastLogin(new Date());
            userRepository.save(user);
        }
    }
}
