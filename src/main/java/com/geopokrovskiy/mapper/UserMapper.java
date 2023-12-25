package com.geopokrovskiy.mapper;

import com.geopokrovskiy.dto.UserDto;
import com.geopokrovskiy.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @InheritInverseConfiguration
    UserDto map(UserEntity userEntity);
    UserEntity map(UserDto userDto);

}
