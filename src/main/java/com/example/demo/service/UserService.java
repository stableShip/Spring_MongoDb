package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName().toLowerCase());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setUserName(userDTO.getUserName());
                    userRepository.save(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByUserName(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

}
