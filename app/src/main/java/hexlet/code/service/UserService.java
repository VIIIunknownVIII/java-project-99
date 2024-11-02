package hexlet.code.service;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public UserDTO createUser(UserCreateDTO userBody) {
        User user = new User();
        user.setEmail(userBody.getEmail());
        user.setPassword(passwordEncoder.encode(userBody.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDTO updateUser(UserUpdateDTO userBody, long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (userBody.getEmail().isPresent()) user.setEmail(userBody.getEmail().get());
        if (userBody.getPassword().isPresent()) user.setPassword(passwordEncoder.encode(userBody.getPassword().get()));
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt().toLocalDate());
        userDTO.setUpdatedAt(user.getUpdatedAt().toLocalDate());
        return userDTO;
    }
}
