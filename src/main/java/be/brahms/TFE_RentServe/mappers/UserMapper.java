package be.brahms.TFE_RentServe.mappers;

import be.brahms.TFE_RentServe.models.dtos.user.UserDTO;
import be.brahms.TFE_RentServe.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for user data
 * It converts User
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Create a UserDTO from a User
     *
     * @param user the user data
     * @return a User dto
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "birthdate", source = "birthdate")
    @Mapping(target = "pseudo", source = "pseudo")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "zipCode", source = "zipCode")
    @Mapping(target = "isActive", source = "isActive")
    UserDTO toDto(User user);
}
