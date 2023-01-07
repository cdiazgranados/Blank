package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.domain.Membership;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.dto.MembershipDTO;

/**
 * Mapper for the entity {@link Channel} and its DTO {@link ChannelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChannelMapper extends EntityMapper<ChannelDTO, Channel> {
    @Mapping(target = "membership", source = "membership", qualifiedByName = "membershipId")
    ChannelDTO toDto(Channel s);

    @Named("membershipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MembershipDTO toDtoMembershipId(Membership membership);
}
