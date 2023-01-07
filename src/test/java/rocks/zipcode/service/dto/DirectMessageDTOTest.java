package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class DirectMessageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DirectMessageDTO.class);
        DirectMessageDTO directMessageDTO1 = new DirectMessageDTO();
        directMessageDTO1.setId(1L);
        DirectMessageDTO directMessageDTO2 = new DirectMessageDTO();
        assertThat(directMessageDTO1).isNotEqualTo(directMessageDTO2);
        directMessageDTO2.setId(directMessageDTO1.getId());
        assertThat(directMessageDTO1).isEqualTo(directMessageDTO2);
        directMessageDTO2.setId(2L);
        assertThat(directMessageDTO1).isNotEqualTo(directMessageDTO2);
        directMessageDTO1.setId(null);
        assertThat(directMessageDTO1).isNotEqualTo(directMessageDTO2);
    }
}
