package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.domain.Membership;
import rocks.zipcode.domain.User;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.dto.MembershipDTO;
import rocks.zipcode.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Membership} and its DTO {@link MembershipDTO}.
 */
@Mapper(componentModel = "spring")
public interface MembershipMapper extends EntityMapper<MembershipDTO, Membership> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "channel", source = "channel", qualifiedByName = "channelId")
    MembershipDTO toDto(Membership s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("channelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChannelDTO toDtoChannelId(Channel channel);
}
