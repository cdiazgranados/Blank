package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "user", "channel", "messages" }, allowSetters = true)
    private Membership membership;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "memberships", "messages" }, allowSetters = true)
    private Channel channel;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "user", "channel", "messages" }, allowSetters = true)
    private Membership membership;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "memberships", "messages" }, allowSetters = true)
    private Channel channel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "users" }, allowSetters = true)
    private DirectMessage directMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Message id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public Message text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Message membership(Membership membership) {
        this.setMembership(membership);
        return this;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Message channel(Channel channel) {
        this.setChannel(channel);
        return this;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Message membership(Membership membership) {
        this.setMembership(membership);
        return this;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Message channel(Channel channel) {
        this.setChannel(channel);
        return this;
    }

    public DirectMessage getDirectMessage() {
        return this.directMessage;
    }

    public void setDirectMessage(DirectMessage directMessage) {
        this.directMessage = directMessage;
    }

    public Message directMessage(DirectMessage directMessage) {
        this.setDirectMessage(directMessage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            "}";
    }
}
