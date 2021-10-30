package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private String name;
    private String email;
    private String pwd;
    private String userId;
    private LocalDateTime createdAt;

    private String encryptedPwd;

    private List<ResponseOrder> orders = new ArrayList<>();
}
