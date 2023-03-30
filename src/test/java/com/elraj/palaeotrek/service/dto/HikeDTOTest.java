package com.elraj.palaeotrek.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.elraj.palaeotrek.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HikeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HikeDTO.class);
        HikeDTO hikeDTO1 = new HikeDTO();
        hikeDTO1.setId(1L);
        HikeDTO hikeDTO2 = new HikeDTO();
        assertThat(hikeDTO1).isNotEqualTo(hikeDTO2);
        hikeDTO2.setId(hikeDTO1.getId());
        assertThat(hikeDTO1).isEqualTo(hikeDTO2);
        hikeDTO2.setId(2L);
        assertThat(hikeDTO1).isNotEqualTo(hikeDTO2);
        hikeDTO1.setId(null);
        assertThat(hikeDTO1).isNotEqualTo(hikeDTO2);
    }
}
