package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MembershipMapperTest {

    private MembershipMapper membershipMapper;

    @BeforeEach
    public void setUp() {
        membershipMapper = new MembershipMapperImpl();
    }
}
