package com.elraj.palaeotrek.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.elraj.palaeotrek.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HikeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hike.class);
        Hike hike1 = new Hike();
        hike1.setId(1L);
        Hike hike2 = new Hike();
        hike2.setId(hike1.getId());
        assertThat(hike1).isEqualTo(hike2);
        hike2.setId(2L);
        assertThat(hike1).isNotEqualTo(hike2);
        hike1.setId(null);
        assertThat(hike1).isNotEqualTo(hike2);
    }
}
