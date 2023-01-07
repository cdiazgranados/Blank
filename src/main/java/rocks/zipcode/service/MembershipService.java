package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Membership;
import rocks.zipcode.repository.MembershipRepository;
import rocks.zipcode.service.dto.MembershipDTO;
import rocks.zipcode.service.mapper.MembershipMapper;

/**
 * Service Implementation for managing {@link Membership}.
 */
@Service
@Transactional
public class MembershipService {

    private final Logger log = LoggerFactory.getLogger(MembershipService.class);

    private final MembershipRepository membershipRepository;

    private final MembershipMapper membershipMapper;

    public MembershipService(MembershipRepository membershipRepository, MembershipMapper membershipMapper) {
        this.membershipRepository = membershipRepository;
        this.membershipMapper = membershipMapper;
    }

    /**
     * Save a membership.
     *
     * @param membershipDTO the entity to save.
     * @return the persisted entity.
     */
    public MembershipDTO save(MembershipDTO membershipDTO) {
        log.debug("Request to save Membership : {}", membershipDTO);
        Membership membership = membershipMapper.toEntity(membershipDTO);
        membership = membershipRepository.save(membership);
        return membershipMapper.toDto(membership);
    }

    /**
     * Update a membership.
     *
     * @param membershipDTO the entity to save.
     * @return the persisted entity.
     */
    public MembershipDTO update(MembershipDTO membershipDTO) {
        log.debug("Request to update Membership : {}", membershipDTO);
        Membership membership = membershipMapper.toEntity(membershipDTO);
        membership = membershipRepository.save(membership);
        return membershipMapper.toDto(membership);
    }

    /**
     * Partially update a membership.
     *
     * @param membershipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MembershipDTO> partialUpdate(MembershipDTO membershipDTO) {
        log.debug("Request to partially update Membership : {}", membershipDTO);

        return membershipRepository
            .findById(membershipDTO.getId())
            .map(existingMembership -> {
                membershipMapper.partialUpdate(existingMembership, membershipDTO);

                return existingMembership;
            })
            .map(membershipRepository::save)
            .map(membershipMapper::toDto);
    }

    /**
     * Get all the memberships.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MembershipDTO> findAll() {
        log.debug("Request to get all Memberships");
        return membershipRepository.findAll().stream().map(membershipMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one membership by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MembershipDTO> findOne(Long id) {
        log.debug("Request to get Membership : {}", id);
        return membershipRepository.findById(id).map(membershipMapper::toDto);
    }

    /**
     * Delete the membership by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Membership : {}", id);
        membershipRepository.deleteById(id);
    }
}
