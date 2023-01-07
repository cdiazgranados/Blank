package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DirectMessage.
 */
@Entity
@Table(name = "direct_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DirectMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "to_user_id")
    private Long toUserId;

    @OneToMany(mappedBy = "directMessage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "membership", "channel", "membership", "channel", "directMessage" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_direct_message__user",
        joinColumns = @JoinColumn(name = "direct_message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DirectMessage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserId() {
        return this.fromUserId;
    }

    public DirectMessage fromUserId(Long fromUserId) {
        this.setFromUserId(fromUserId);
        return this;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return this.toUserId;
    }

    public DirectMessage toUserId(Long toUserId) {
        this.setToUserId(toUserId);
        return this;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setDirectMessage(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setDirectMessage(this));
        }
        this.messages = messages;
    }

    public DirectMessage messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public DirectMessage addMessage(Message message) {
        this.messages.add(message);
        message.setDirectMessage(this);
        return this;
    }

    public DirectMessage removeMessage(Message message) {
        this.messages.remove(message);
        message.setDirectMessage(null);
        return this;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public DirectMessage users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public DirectMessage addUser(User user) {
        this.users.add(user);
        return this;
    }

    public DirectMessage removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectMessage)) {
            return false;
        }
        return id != null && id.equals(((DirectMessage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectMessage{" +
            "id=" + getId() +
            ", fromUserId=" + getFromUserId() +
            ", toUserId=" + getToUserId() +
            "}";
    }
}
