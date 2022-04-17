package com.elraj.palaeotrek.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.elraj.palaeotrek.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PdfDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PdfDataDTO.class);
        PdfDataDTO pdfDataDTO1 = new PdfDataDTO();
        pdfDataDTO1.setId(1L);
        PdfDataDTO pdfDataDTO2 = new PdfDataDTO();
        assertThat(pdfDataDTO1).isNotEqualTo(pdfDataDTO2);
        pdfDataDTO2.setId(pdfDataDTO1.getId());
        assertThat(pdfDataDTO1).isEqualTo(pdfDataDTO2);
        pdfDataDTO2.setId(2L);
        assertThat(pdfDataDTO1).isNotEqualTo(pdfDataDTO2);
        pdfDataDTO1.setId(null);
        assertThat(pdfDataDTO1).isNotEqualTo(pdfDataDTO2);
    }
}
