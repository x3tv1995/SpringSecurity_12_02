package ru.lesson.springsecurity_12_02.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.lesson.springsecurity_12_02.model.entity.User;
import ru.lesson.springsecurity_12_02.model.dto.UserDTO;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userToEntity(UserDTO userDTO);

    UserDTO userToDTO(User user);
}
