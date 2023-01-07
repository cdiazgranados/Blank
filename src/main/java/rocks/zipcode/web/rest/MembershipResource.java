package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.repository.MembershipRepository;
import rocks.zipcode.service.MembershipService;
import rocks.zipcode.service.dto.MembershipDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Membership}.
 */
@RestController
@RequestMapping("/api")
public class MembershipResource {

    private final Logger log = LoggerFactory.getLogger(MembershipResource.class);

    private static final String ENTITY_NAME = "membership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembershipService membershipService;

    private final MembershipRepository membershipRepository;

    public MembershipResource(MembershipService membershipService, MembershipRepository membershipRepository) {
        this.membershipService = membershipService;
        this.membershipRepository = membershipRepository;
    }

    /**
     * {@code POST  /memberships} : Create a new membership.
     *
     * @param membershipDTO the membershipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membershipDTO, or with status {@code 400 (Bad Request)} if the membership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/memberships")
    public ResponseEntity<MembershipDTO> createMembership(@RequestBody MembershipDTO membershipDTO) throws URISyntaxException {
        log.debug("REST request to save Membership : {}", membershipDTO);
        if (membershipDTO.getId() != null) {
            throw new BadRequestAlertException("A new membership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembershipDTO result = membershipService.save(membershipDTO);
        return ResponseEntity
            .created(new URI("/api/memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /memberships/:id} : Updates an existing membership.
     *
     * @param id the id of the membershipDTO to save.
     * @param membershipDTO the membershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipDTO,
     * or with status {@code 400 (Bad Request)} if the membershipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/memberships/{id}")
    public ResponseEntity<MembershipDTO> updateMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MembershipDTO membershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Membership : {}, {}", id, membershipDTO);
        if (membershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membershipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MembershipDTO result = membershipService.update(membershipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /memberships/:id} : Partial updates given fields of an existing membership, field will ignore if it is null
     *
     * @param id the id of the membershipDTO to save.
     * @param membershipDTO the membershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipDTO,
     * or with status {@code 400 (Bad Request)} if the membershipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the membershipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the membershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/memberships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MembershipDTO> partialUpdateMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MembershipDTO membershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Membership partially : {}, {}", id, membershipDTO);
        if (membershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membershipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MembershipDTO> result = membershipService.partialUpdate(membershipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membershipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /memberships} : get all the memberships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberships in body.
     */
    @GetMapping("/memberships")
    public List<MembershipDTO> getAllMemberships() {
        log.debug("REST request to get all Memberships");
        return membershipService.findAll();
    }

    /**
     * {@code GET  /memberships/:id} : get the "id" membership.
     *
     * @param id the id of the membershipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membershipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/memberships/{id}")
    public ResponseEntity<MembershipDTO> getMembership(@PathVariable Long id) {
        log.debug("REST request to get Membership : {}", id);
        Optional<MembershipDTO> membershipDTO = membershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membershipDTO);
    }

    /**
     * {@code DELETE  /memberships/:id} : delete the "id" membership.
     *
     * @param id the id of the membershipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/memberships/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        log.debug("REST request to delete Membership : {}", id);
        membershipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
