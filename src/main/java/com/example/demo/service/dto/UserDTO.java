package com.example.demo.service.dto;

import com.example.demo.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private String id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String userName;

    @Size(min = 5, max = 60)
    private String password;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
    }

}
