package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.Membership} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MembershipDTO implements Serializable {

    private Long id;

    private UserDTO user;

    private ChannelDTO channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipDTO)) {
            return false;
        }

        MembershipDTO membershipDTO = (MembershipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, membershipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipDTO{" +
            "id=" + getId() +
            ", user=" + getUser() +
            ", channel=" + getChannel() +
            "}";
    }
}
