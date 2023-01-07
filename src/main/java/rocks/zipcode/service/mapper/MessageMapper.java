package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.domain.DirectMessage;
import rocks.zipcode.domain.Membership;
import rocks.zipcode.domain.Message;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.dto.DirectMessageDTO;
import rocks.zipcode.service.dto.MembershipDTO;
import rocks.zipcode.service.dto.MessageDTO;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "membership", source = "membership", qualifiedByName = "membershipId")
    @Mapping(target = "channel", source = "channel", qualifiedByName = "channelId")
    @Mapping(target = "membership", source = "membership", qualifiedByName = "membershipId")
    @Mapping(target = "channel", source = "channel", qualifiedByName = "channelId")
    @Mapping(target = "directMessage", source = "directMessage", qualifiedByName = "directMessageId")
    MessageDTO toDto(Message s);

    @Named("membershipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MembershipDTO toDtoMembershipId(Membership membership);

    @Named("channelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChannelDTO toDtoChannelId(Channel channel);

    @Named("directMessageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DirectMessageDTO toDtoDirectMessageId(DirectMessage directMessage);
}
