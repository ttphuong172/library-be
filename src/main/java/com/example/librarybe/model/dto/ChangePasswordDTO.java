package com.example.librarybe.model.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String comfirmNewPassword;
}
