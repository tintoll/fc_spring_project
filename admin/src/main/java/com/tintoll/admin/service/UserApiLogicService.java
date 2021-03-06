package com.tintoll.admin.service;

import com.tintoll.admin.ifs.CrudInterface;
import com.tintoll.admin.model.entity.OrderGroup;
import com.tintoll.admin.model.entity.User;
import com.tintoll.admin.model.enumClass.UserStatus;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.Pagination;
import com.tintoll.admin.model.network.request.UserApiRequest;
import com.tintoll.admin.model.network.response.ItemApiResponse;
import com.tintoll.admin.model.network.response.OrderGroupResponse;
import com.tintoll.admin.model.network.response.UserApiResponse;
import com.tintoll.admin.model.network.response.UserOrderInfoApiResponse;
import com.tintoll.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderGroupApiService orderGroupApiService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> header) {
        UserApiRequest userApiRequest = header.getData();

        User user = User.builder()
                        .email(userApiRequest.getEmail())
                        .account(userApiRequest.getAccount())
                        .password(userApiRequest.getPassword())
                        .phoneNumber(userApiRequest.getPhoneNumber())
                        .status(UserStatus.REGISTERED)
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

    public Header<List<UserApiResponse>> search(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<UserApiResponse> userApiResponseList = page.stream()
                .map( user -> respone(user))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                                    .currentPage(page.getNumber())
                                    .currentElements(page.getNumberOfElements())
                                    .totalPages(page.getTotalPages())
                                    .totalElements(page.getTotalElements())
                                    .build();

        return Header.OK(userApiResponseList, pagination);
    }

    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {
        // User
        User user = userRepository.getOne(id);
        UserApiResponse userApiResponse = respone(user);

        // orderGorup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupResponse> orderGroupResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupResponse orderGroupResponse = orderGroupApiService.response(orderGroup);

                    // item api response
                    List<ItemApiResponse> itemApiResponses = orderGroup.getOrderDetailList().stream()
                            .map(orderDetail -> orderDetail.getItem())
                            .map(item -> itemApiLogicService.response(item))
                            .collect(Collectors.toList());

                    orderGroupResponse.setItemApiResponseList(itemApiResponses);
                    return orderGroupResponse;

                }).collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);
    }
}
