package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectMessageMapperTest {

    private DirectMessageMapper directMessageMapper;

    @BeforeEach
    public void setUp() {
        directMessageMapper = new DirectMessageMapperImpl();
    }
}
