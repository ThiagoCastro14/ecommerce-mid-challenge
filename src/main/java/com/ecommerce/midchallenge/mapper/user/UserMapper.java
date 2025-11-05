package com.ecommerce.midchallenge.mapper.user;

import com.ecommerce.midchallenge.domain.user.User;
import com.ecommerce.midchallenge.dto.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(
        target = "roles",
        expression = "java(user.getRoles() != null ? " +
                     "user.getRoles().stream().map(r -> r.getName()).collect(java.util.stream.Collectors.toSet()) : null)"
    )
    UserDTO toDto(User user);
}
