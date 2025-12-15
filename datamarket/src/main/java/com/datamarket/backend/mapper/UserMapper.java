package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.UserResponse;
import com.datamarket.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", source = "role")
    UserResponse toUserResponse(User user);
}
