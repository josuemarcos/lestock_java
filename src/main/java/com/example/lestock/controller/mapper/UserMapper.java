package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.UserDTO;
import com.example.lestock.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDTO(User user);
}
