package com.elraj.palaeotrek.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PdfDataMapperTest {

    private PdfDataMapper pdfDataMapper;

    @BeforeEach
    public void setUp() {
        pdfDataMapper = new PdfDataMapperImpl();
    }
}
