package com.tintoll.admin.service;

import com.tintoll.admin.ifs.CrudInterface;
import com.tintoll.admin.model.entity.User;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.UserApiRequest;
import com.tintoll.admin.model.network.response.UserApiResponse;
import com.tintoll.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> header) {
        UserApiRequest userApiRequest = header.getData();

        User user = User.builder()
                        .email(userApiRequest.getEmail())
                        .account(userApiRequest.getAccount())
                        .password(userApiRequest.getPassword())
                        .phoneNumber(userApiRequest.getPhoneNumber())
                        .status(userApiRequest.getStatus())
                        .registeredAt(LocalDateTime.now())
                        .build();
        User newUser = userRepository.save(user);

        return Header.OK(respone(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        Optional<User> optional = userRepository.findById(id);

        return optional
                .map( user -> respone(user))
                .map( userApirespose -> Header.OK(userApirespose))
                .orElseGet( () -> Header.ERROR("사용자 정보 없음"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> header) {

        UserApiRequest userApiRequest = header.getData();
        Optional<User> optional = userRepository.findById(userApiRequest.getId());

        return optional
                .map( user -> {
                    user.setAccount(userApiRequest.getAccount())
                            .setPassword(userApiRequest.getPassword())
                            .setStatus(userApiRequest.getStatus())
                            .setPhoneNumber(userApiRequest.getPhoneNumber())
                            .setEmail(userApiRequest.getEmail())
                            .setRegisteredAt(userApiRequest.getRegisteredAt())
                            .setUnregisteredAt(userApiRequest.getUnregisteredAt());

                    return userRepository.save(user);
                }).map(user -> respone(user))
                .map( userApirespose -> Header.OK(userApirespose))
                .orElseGet( () -> Header.ERROR("사용자 정보 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional
                .map( user -> {
                    userRepository.delete(user);
                    return Header.OK();
                }).orElseGet(() -> Header.ERROR("사용자 정보 없음"));
    }

    // user -> userApiResponse
    private UserApiResponse respone(User user) {
        return UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .status(user.getStatus())
                .build();
    }
}
