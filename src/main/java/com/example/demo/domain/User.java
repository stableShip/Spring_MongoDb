package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Document(collection = "user")
public class User extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String userName;

    @JsonIgnore
    @NotNull
    @Size(min = 5, max = 60)
    private String password;
}
