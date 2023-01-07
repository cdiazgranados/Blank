package rocks.zipcode.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import rocks.zipcode.domain.DirectMessage;
import rocks.zipcode.domain.User;
import rocks.zipcode.service.dto.DirectMessageDTO;
import rocks.zipcode.service.dto.UserDTO;

/**
 * Mapper for the entity {@link DirectMessage} and its DTO {@link DirectMessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface DirectMessageMapper extends EntityMapper<DirectMessageDTO, DirectMessage> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userLoginSet")
    DirectMessageDTO toDto(DirectMessage s);

    @Mapping(target = "removeUser", ignore = true)
    DirectMessage toEntity(DirectMessageDTO directMessageDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("userLoginSet")
    default Set<UserDTO> toDtoUserLoginSet(Set<User> user) {
        return user.stream().map(this::toDtoUserLogin).collect(Collectors.toSet());
    }
}
