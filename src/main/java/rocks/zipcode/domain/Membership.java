package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Membership.
 */
@Entity
@Table(name = "membership")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "membership")
    @OneToMany(mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "membership", "channel", "membership", "channel", "directMessage" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "memberships", "messages" }, allowSetters = true)
    private Channel channel;

    @OneToMany(mappedBy = "membership")
    @OneToMany(mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "membership", "channel", "membership", "channel", "directMessage" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Membership id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setMembership(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setMembership(this));
        }
        this.messages = messages;
    }

    public Membership messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public Membership addMessage(Message message) {
        this.messages.add(message);
        message.setMembership(this);
        return this;
    }

    public Membership removeMessage(Message message) {
        this.messages.remove(message);
        message.setMembership(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Membership user(User user) {
        this.setUser(user);
        return this;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Membership channel(Channel channel) {
        this.setChannel(channel);
        return this;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setMembership(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setMembership(this));
        }
        this.messages = messages;
    }

    public Membership messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public Membership addMessage(Message message) {
        this.messages.add(message);
        message.setMembership(this);
        return this;
    }

    public Membership removeMessage(Message message) {
        this.messages.remove(message);
        message.setMembership(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Membership)) {
            return false;
        }
        return id != null && id.equals(((Membership) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Membership{" +
            "id=" + getId() +
            "}";
    }
}
