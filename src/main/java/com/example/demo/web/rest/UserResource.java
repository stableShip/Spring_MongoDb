package com.example.demo.web.rest;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@Slf4j
public class UserResource {

    private final UserService userService;

    private final UserRepository userRepository;


    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws 400                (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        log.info("REST request to save User : {}", userDTO);
        if (userRepository.findOneByUserName(userDTO.getUserName().toLowerCase()).isPresent()) {
            throw new Exception("User already exist");
        }
        User user = userService.createUser(userDTO);
        return ResponseEntity.created(new URI("/api/users/" + user.getUserName())).body(user);
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws 400 (Bad Request) if the email is already in use
     * @throws 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("REST request to update User : {}", userDTO);
        Optional<UserDTO> user = userService.updateUser(userDTO);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllUsers(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }


    /**
     * DELETE /users/:username : delete the "login" User.
     *
     * @param userName the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{userName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userName) {
        log.debug("REST request to delete User: {}", userName);
        userService.deleteUser(userName);
        HashMap body = new HashMap();
        body.put("status", 200);
        body.put("msg", "success");
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
