package com.elraj.palaeotrek.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.elraj.palaeotrek.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PdfDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PdfData.class);
        PdfData pdfData1 = new PdfData();
        pdfData1.setId(1L);
        PdfData pdfData2 = new PdfData();
        pdfData2.setId(pdfData1.getId());
        assertThat(pdfData1).isEqualTo(pdfData2);
        pdfData2.setId(2L);
        assertThat(pdfData1).isNotEqualTo(pdfData2);
        pdfData1.setId(null);
        assertThat(pdfData1).isNotEqualTo(pdfData2);
    }
}
