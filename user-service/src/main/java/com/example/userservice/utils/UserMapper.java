package com.example.userservice.utils;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserEntity mapDtoToEntity(UserDto dto) {
        UserEntity entity = mapper.map(dto, UserEntity.class);
        entity.setEncryptedPwd(passwordEncoder.encode(dto.getPwd()));

        return entity;
    }

    public UserDto mapEntityToDto(UserEntity entity) {
        return mapper.map(entity, UserDto.class);
    }

    public UserDto mapRequestToDto(RequestUser user) {
        return mapper.map(user, UserDto.class);
    }

    public ResponseUser mapDtoToResponse(UserDto dto) {
        return mapper.map(dto, ResponseUser.class);
    }

    public ResponseUser mapEntityToResponse(UserEntity entity) {
        return mapper.map(entity, ResponseUser.class);
    }
}
