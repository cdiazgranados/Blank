package rocks.zipcode.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import rocks.zipcode.domain.DirectMessage;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DirectMessageRepositoryWithBagRelationshipsImpl implements DirectMessageRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DirectMessage> fetchBagRelationships(Optional<DirectMessage> directMessage) {
        return directMessage.map(this::fetchUsers);
    }

    @Override
    public Page<DirectMessage> fetchBagRelationships(Page<DirectMessage> directMessages) {
        return new PageImpl<>(
            fetchBagRelationships(directMessages.getContent()),
            directMessages.getPageable(),
            directMessages.getTotalElements()
        );
    }

    @Override
    public List<DirectMessage> fetchBagRelationships(List<DirectMessage> directMessages) {
        return Optional.of(directMessages).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    DirectMessage fetchUsers(DirectMessage result) {
        return entityManager
            .createQuery(
                "select directMessage from DirectMessage directMessage left join fetch directMessage.users where directMessage is :directMessage",
                DirectMessage.class
            )
            .setParameter("directMessage", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DirectMessage> fetchUsers(List<DirectMessage> directMessages) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, directMessages.size()).forEach(index -> order.put(directMessages.get(index).getId(), index));
        List<DirectMessage> result = entityManager
            .createQuery(
                "select distinct directMessage from DirectMessage directMessage left join fetch directMessage.users where directMessage in :directMessages",
                DirectMessage.class
            )
            .setParameter("directMessages", directMessages)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
