package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.Message} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageDTO implements Serializable {

    private Long id;

    private String text;

    private MembershipDTO membership;

    private ChannelDTO channel;

    private DirectMessageDTO directMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MembershipDTO getMembership() {
        return membership;
    }

    public void setMembership(MembershipDTO membership) {
        this.membership = membership;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    public DirectMessageDTO getDirectMessage() {
        return directMessage;
    }

    public void setDirectMessage(DirectMessageDTO directMessage) {
        this.directMessage = directMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", membership=" + getMembership() +
            ", channel=" + getChannel() +
            ", directMessage=" + getDirectMessage() +
            "}";
    }
}
