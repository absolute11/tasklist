package com.example.tasklist2.web.mappers;

import com.example.tasklist2.domain.user.User;
import com.example.tasklist2.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {


}
