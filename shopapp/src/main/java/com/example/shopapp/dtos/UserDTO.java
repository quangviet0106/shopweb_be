package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.awt.*;
import java.sql.Date;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "Password cannot be blank!")
    private String password;
    @JsonProperty("retype_password")
    private String retypePassword;
    @JsonProperty("date_of_birth")
    private Date dob;
    @JsonProperty("facebook_account_id")
    private int facebookAccountId;
    @JsonProperty("google_account_id")
    private int googleAccountId;
    @JsonProperty("role_id")
    @NotNull(message = "Role ID is required!")
    private Long roleId;

}
