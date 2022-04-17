package com.elraj.palaeotrek.service.mapper;

import com.elraj.palaeotrek.domain.PdfData;
import com.elraj.palaeotrek.service.dto.PdfDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PdfData} and its DTO {@link PdfDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface PdfDataMapper extends EntityMapper<PdfDataDTO, PdfData> {}
