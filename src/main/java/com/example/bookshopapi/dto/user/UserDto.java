package com.example.bookshopapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String address;
    private String mobPhone;
    private LocalDate dateOfBirth;
    private String gender;
    private String avatar;
    private String status;
    private String roleUser;
}
