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
import rocks.zipcode.repository.DirectMessageRepository;
import rocks.zipcode.service.DirectMessageService;
import rocks.zipcode.service.dto.DirectMessageDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.DirectMessage}.
 */
@RestController
@RequestMapping("/api")
public class DirectMessageResource {

    private final Logger log = LoggerFactory.getLogger(DirectMessageResource.class);

    private static final String ENTITY_NAME = "directMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DirectMessageService directMessageService;

    private final DirectMessageRepository directMessageRepository;

    public DirectMessageResource(DirectMessageService directMessageService, DirectMessageRepository directMessageRepository) {
        this.directMessageService = directMessageService;
        this.directMessageRepository = directMessageRepository;
    }

    /**
     * {@code POST  /direct-messages} : Create a new directMessage.
     *
     * @param directMessageDTO the directMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new directMessageDTO, or with status {@code 400 (Bad Request)} if the directMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/direct-messages")
    public ResponseEntity<DirectMessageDTO> createDirectMessage(@RequestBody DirectMessageDTO directMessageDTO) throws URISyntaxException {
        log.debug("REST request to save DirectMessage : {}", directMessageDTO);
        if (directMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new directMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DirectMessageDTO result = directMessageService.save(directMessageDTO);
        return ResponseEntity
            .created(new URI("/api/direct-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /direct-messages/:id} : Updates an existing directMessage.
     *
     * @param id the id of the directMessageDTO to save.
     * @param directMessageDTO the directMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated directMessageDTO,
     * or with status {@code 400 (Bad Request)} if the directMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the directMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/direct-messages/{id}")
    public ResponseEntity<DirectMessageDTO> updateDirectMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DirectMessageDTO directMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DirectMessage : {}, {}", id, directMessageDTO);
        if (directMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, directMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!directMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DirectMessageDTO result = directMessageService.update(directMessageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, directMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /direct-messages/:id} : Partial updates given fields of an existing directMessage, field will ignore if it is null
     *
     * @param id the id of the directMessageDTO to save.
     * @param directMessageDTO the directMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated directMessageDTO,
     * or with status {@code 400 (Bad Request)} if the directMessageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the directMessageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the directMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/direct-messages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DirectMessageDTO> partialUpdateDirectMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DirectMessageDTO directMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DirectMessage partially : {}, {}", id, directMessageDTO);
        if (directMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, directMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!directMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DirectMessageDTO> result = directMessageService.partialUpdate(directMessageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, directMessageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /direct-messages} : get all the directMessages.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of directMessages in body.
     */
    @GetMapping("/direct-messages")
    public List<DirectMessageDTO> getAllDirectMessages(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DirectMessages");
        return directMessageService.findAll();
    }

    /**
     * {@code GET  /direct-messages/:id} : get the "id" directMessage.
     *
     * @param id the id of the directMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the directMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/direct-messages/{id}")
    public ResponseEntity<DirectMessageDTO> getDirectMessage(@PathVariable Long id) {
        log.debug("REST request to get DirectMessage : {}", id);
        Optional<DirectMessageDTO> directMessageDTO = directMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(directMessageDTO);
    }

    /**
     * {@code DELETE  /direct-messages/:id} : delete the "id" directMessage.
     *
     * @param id the id of the directMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/direct-messages/{id}")
    public ResponseEntity<Void> deleteDirectMessage(@PathVariable Long id) {
        log.debug("REST request to delete DirectMessage : {}", id);
        directMessageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
