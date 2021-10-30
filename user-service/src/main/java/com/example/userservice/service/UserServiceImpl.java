package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.utils.UserMapper;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final Environment env;
    private final OrderServiceClient orderServiceClient;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = mapper.mapDtoToEntity(userDto);
        UserEntity savedEntity = userRepository.save(userEntity);

        return mapper.mapEntityToDto(savedEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                                              .orElseThrow(IllegalArgumentException::new);
        UserDto userDto = mapper.mapEntityToDto(userEntity);

        // RestTemplate 활용
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//
//        ResponseEntity<List<ResponseOrder>> orderListResponse =
//                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                                        new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });

        // FeignClient 기본 예외처리
//        List<ResponseOrder> orders = null;
//        try {
//            orders = orderServiceClient.getOrdersByUserId(userId);
//        } catch (FeignException e) {
//            log.error(e.getMessage());
//        }

        userDto.setOrders(orderServiceClient.getOrdersByUserId(userId));

        return userDto;
    }

    @Override
    public List<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                                              .orElseThrow(() -> new UsernameNotFoundException(email));

        return mapper.mapEntityToDto(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).
                                               orElseThrow(() -> new UsernameNotFoundException(email));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
