package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.DirectMessage;
import rocks.zipcode.repository.DirectMessageRepository;
import rocks.zipcode.service.DirectMessageService;
import rocks.zipcode.service.dto.DirectMessageDTO;
import rocks.zipcode.service.mapper.DirectMessageMapper;

/**
 * Integration tests for the {@link DirectMessageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DirectMessageResourceIT {

    private static final Long DEFAULT_FROM_USER_ID = 1L;
    private static final Long UPDATED_FROM_USER_ID = 2L;

    private static final Long DEFAULT_TO_USER_ID = 1L;
    private static final Long UPDATED_TO_USER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/direct-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Mock
    private DirectMessageRepository directMessageRepositoryMock;

    @Autowired
    private DirectMessageMapper directMessageMapper;

    @Mock
    private DirectMessageService directMessageServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDirectMessageMockMvc;

    private DirectMessage directMessage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DirectMessage createEntity(EntityManager em) {
        DirectMessage directMessage = new DirectMessage().fromUserId(DEFAULT_FROM_USER_ID).toUserId(DEFAULT_TO_USER_ID);
        return directMessage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DirectMessage createUpdatedEntity(EntityManager em) {
        DirectMessage directMessage = new DirectMessage().fromUserId(UPDATED_FROM_USER_ID).toUserId(UPDATED_TO_USER_ID);
        return directMessage;
    }

    @BeforeEach
    public void initTest() {
        directMessage = createEntity(em);
    }

    @Test
    @Transactional
    void createDirectMessage() throws Exception {
        int databaseSizeBeforeCreate = directMessageRepository.findAll().size();
        // Create the DirectMessage
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);
        restDirectMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeCreate + 1);
        DirectMessage testDirectMessage = directMessageList.get(directMessageList.size() - 1);
        assertThat(testDirectMessage.getFromUserId()).isEqualTo(DEFAULT_FROM_USER_ID);
        assertThat(testDirectMessage.getToUserId()).isEqualTo(DEFAULT_TO_USER_ID);
    }

    @Test
    @Transactional
    void createDirectMessageWithExistingId() throws Exception {
        // Create the DirectMessage with an existing ID
        directMessage.setId(1L);
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);

        int databaseSizeBeforeCreate = directMessageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDirectMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDirectMessages() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        // Get all the directMessageList
        restDirectMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(directMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromUserId").value(hasItem(DEFAULT_FROM_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].toUserId").value(hasItem(DEFAULT_TO_USER_ID.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDirectMessagesWithEagerRelationshipsIsEnabled() throws Exception {
        when(directMessageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDirectMessageMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(directMessageServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDirectMessagesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(directMessageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDirectMessageMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(directMessageRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDirectMessage() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        // Get the directMessage
        restDirectMessageMockMvc
            .perform(get(ENTITY_API_URL_ID, directMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(directMessage.getId().intValue()))
            .andExpect(jsonPath("$.fromUserId").value(DEFAULT_FROM_USER_ID.intValue()))
            .andExpect(jsonPath("$.toUserId").value(DEFAULT_TO_USER_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDirectMessage() throws Exception {
        // Get the directMessage
        restDirectMessageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDirectMessage() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();

        // Update the directMessage
        DirectMessage updatedDirectMessage = directMessageRepository.findById(directMessage.getId()).get();
        // Disconnect from session so that the updates on updatedDirectMessage are not directly saved in db
        em.detach(updatedDirectMessage);
        updatedDirectMessage.fromUserId(UPDATED_FROM_USER_ID).toUserId(UPDATED_TO_USER_ID);
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(updatedDirectMessage);

        restDirectMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, directMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isOk());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
        DirectMessage testDirectMessage = directMessageList.get(directMessageList.size() - 1);
        assertThat(testDirectMessage.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
        assertThat(testDirectMessage.getToUserId()).isEqualTo(UPDATED_TO_USER_ID);
    }

    @Test
    @Transactional
    void putNonExistingDirectMessage() throws Exception {
        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();
        directMessage.setId(count.incrementAndGet());

        // Create the DirectMessage
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, directMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDirectMessage() throws Exception {
        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();
        directMessage.setId(count.incrementAndGet());

        // Create the DirectMessage
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDirectMessage() throws Exception {
        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();
        directMessage.setId(count.incrementAndGet());

        // Create the DirectMessage
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectMessageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDirectMessageWithPatch() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();

        // Update the directMessage using partial update
        DirectMessage partialUpdatedDirectMessage = new DirectMessage();
        partialUpdatedDirectMessage.setId(directMessage.getId());

        restDirectMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDirectMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDirectMessage))
            )
            .andExpect(status().isOk());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
        DirectMessage testDirectMessage = directMessageList.get(directMessageList.size() - 1);
        assertThat(testDirectMessage.getFromUserId()).isEqualTo(DEFAULT_FROM_USER_ID);
        assertThat(testDirectMessage.getToUserId()).isEqualTo(DEFAULT_TO_USER_ID);
    }

    @Test
    @Transactional
    void fullUpdateDirectMessageWithPatch() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();

        // Update the directMessage using partial update
        DirectMessage partialUpdatedDirectMessage = new DirectMessage();
        partialUpdatedDirectMessage.setId(directMessage.getId());

        partialUpdatedDirectMessage.fromUserId(UPDATED_FROM_USER_ID).toUserId(UPDATED_TO_USER_ID);

        restDirectMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDirectMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDirectMessage))
            )
            .andExpect(status().isOk());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
        DirectMessage testDirectMessage = directMessageList.get(directMessageList.size() - 1);
        assertThat(testDirectMessage.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
        assertThat(testDirectMessage.getToUserId()).isEqualTo(UPDATED_TO_USER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingDirectMessage() throws Exception {
        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();
        directMessage.setId(count.incrementAndGet());

        // Create the DirectMessage
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, directMessageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDirectMessage() throws Exception {
        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();
        directMessage.setId(count.incrementAndGet());

        // Create the DirectMessage
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDirectMessage() throws Exception {
        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();
        directMessage.setId(count.incrementAndGet());

        // Create the DirectMessage
        DirectMessageDTO directMessageDTO = directMessageMapper.toDto(directMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectMessageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(directMessageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDirectMessage() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        int databaseSizeBeforeDelete = directMessageRepository.findAll().size();

        // Delete the directMessage
        restDirectMessageMockMvc
            .perform(delete(ENTITY_API_URL_ID, directMessage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
