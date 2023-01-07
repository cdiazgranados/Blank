package rocks.zipcode.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.Membership;

/**
 * Spring Data JPA repository for the Membership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    @Query("select membership from Membership membership where membership.user.login = ?#{principal.username}")
    List<Membership> findByUserIsCurrentUser();
}
