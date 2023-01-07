package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Channel.
 */
@Entity
@Table(name = "channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "channel")
    @OneToMany(mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "membership", "channel", "membership", "channel", "directMessage" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "messages", "user", "channel", "messages" }, allowSetters = true)
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "channel")
    @OneToMany(mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "membership", "channel", "membership", "channel", "directMessage" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Channel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Channel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Channel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setChannel(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setChannel(this));
        }
        this.messages = messages;
    }

    public Channel messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public Channel addMessage(Message message) {
        this.messages.add(message);
        message.setChannel(this);
        return this;
    }

    public Channel removeMessage(Message message) {
        this.messages.remove(message);
        message.setChannel(null);
        return this;
    }

    public Set<Membership> getMemberships() {
        return this.memberships;
    }

    public void setMemberships(Set<Membership> memberships) {
        if (this.memberships != null) {
            this.memberships.forEach(i -> i.setChannel(null));
        }
        if (memberships != null) {
            memberships.forEach(i -> i.setChannel(this));
        }
        this.memberships = memberships;
    }

    public Channel memberships(Set<Membership> memberships) {
        this.setMemberships(memberships);
        return this;
    }

    public Channel addMembership(Membership membership) {
        this.memberships.add(membership);
        membership.setChannel(this);
        return this;
    }

    public Channel removeMembership(Membership membership) {
        this.memberships.remove(membership);
        membership.setChannel(null);
        return this;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setChannel(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setChannel(this));
        }
        this.messages = messages;
    }

    public Channel messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public Channel addMessage(Message message) {
        this.messages.add(message);
        message.setChannel(this);
        return this;
    }

    public Channel removeMessage(Message message) {
        this.messages.remove(message);
        message.setChannel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Channel)) {
            return false;
        }
        return id != null && id.equals(((Channel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Channel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
