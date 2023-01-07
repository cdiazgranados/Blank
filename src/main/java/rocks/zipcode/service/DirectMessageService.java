package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.DirectMessage;
import rocks.zipcode.repository.DirectMessageRepository;
import rocks.zipcode.service.dto.DirectMessageDTO;
import rocks.zipcode.service.mapper.DirectMessageMapper;

/**
 * Service Implementation for managing {@link DirectMessage}.
 */
@Service
@Transactional
public class DirectMessageService {

    private final Logger log = LoggerFactory.getLogger(DirectMessageService.class);

    private final DirectMessageRepository directMessageRepository;

    private final DirectMessageMapper directMessageMapper;

    public DirectMessageService(DirectMessageRepository directMessageRepository, DirectMessageMapper directMessageMapper) {
        this.directMessageRepository = directMessageRepository;
        this.directMessageMapper = directMessageMapper;
    }

    /**
     * Save a directMessage.
     *
     * @param directMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public DirectMessageDTO save(DirectMessageDTO directMessageDTO) {
        log.debug("Request to save DirectMessage : {}", directMessageDTO);
        DirectMessage directMessage = directMessageMapper.toEntity(directMessageDTO);
        directMessage = directMessageRepository.save(directMessage);
        return directMessageMapper.toDto(directMessage);
    }

    /**
     * Update a directMessage.
     *
     * @param directMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public DirectMessageDTO update(DirectMessageDTO directMessageDTO) {
        log.debug("Request to update DirectMessage : {}", directMessageDTO);
        DirectMessage directMessage = directMessageMapper.toEntity(directMessageDTO);
        directMessage = directMessageRepository.save(directMessage);
        return directMessageMapper.toDto(directMessage);
    }

    /**
     * Partially update a directMessage.
     *
     * @param directMessageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DirectMessageDTO> partialUpdate(DirectMessageDTO directMessageDTO) {
        log.debug("Request to partially update DirectMessage : {}", directMessageDTO);

        return directMessageRepository
            .findById(directMessageDTO.getId())
            .map(existingDirectMessage -> {
                directMessageMapper.partialUpdate(existingDirectMessage, directMessageDTO);

                return existingDirectMessage;
            })
            .map(directMessageRepository::save)
            .map(directMessageMapper::toDto);
    }

    /**
     * Get all the directMessages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DirectMessageDTO> findAll() {
        log.debug("Request to get all DirectMessages");
        return directMessageRepository.findAll().stream().map(directMessageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the directMessages with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DirectMessageDTO> findAllWithEagerRelationships(Pageable pageable) {
        return directMessageRepository.findAllWithEagerRelationships(pageable).map(directMessageMapper::toDto);
    }

    /**
     * Get one directMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DirectMessageDTO> findOne(Long id) {
        log.debug("Request to get DirectMessage : {}", id);
        return directMessageRepository.findOneWithEagerRelationships(id).map(directMessageMapper::toDto);
    }

    /**
     * Delete the directMessage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DirectMessage : {}", id);
        directMessageRepository.deleteById(id);
    }
}
