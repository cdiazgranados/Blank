package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link rocks.zipcode.domain.DirectMessage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DirectMessageDTO implements Serializable {

    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private Set<UserDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectMessageDTO)) {
            return false;
        }

        DirectMessageDTO directMessageDTO = (DirectMessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, directMessageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectMessageDTO{" +
            "id=" + getId() +
            ", fromUserId=" + getFromUserId() +
            ", toUserId=" + getToUserId() +
            ", users=" + getUsers() +
            "}";
    }
}
