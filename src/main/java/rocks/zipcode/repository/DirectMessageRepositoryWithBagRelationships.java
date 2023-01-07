package rocks.zipcode.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import rocks.zipcode.domain.DirectMessage;

public interface DirectMessageRepositoryWithBagRelationships {
    Optional<DirectMessage> fetchBagRelationships(Optional<DirectMessage> directMessage);

    List<DirectMessage> fetchBagRelationships(List<DirectMessage> directMessages);

    Page<DirectMessage> fetchBagRelationships(Page<DirectMessage> directMessages);
}
