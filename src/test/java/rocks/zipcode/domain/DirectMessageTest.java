package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class DirectMessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DirectMessage.class);
        DirectMessage directMessage1 = new DirectMessage();
        directMessage1.setId(1L);
        DirectMessage directMessage2 = new DirectMessage();
        directMessage2.setId(directMessage1.getId());
        assertThat(directMessage1).isEqualTo(directMessage2);
        directMessage2.setId(2L);
        assertThat(directMessage1).isNotEqualTo(directMessage2);
        directMessage1.setId(null);
        assertThat(directMessage1).isNotEqualTo(directMessage2);
    }
}
