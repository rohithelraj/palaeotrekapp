package com.elraj.palaeotrek.repository;

import com.elraj.palaeotrek.domain.PdfData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PdfData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PdfDataRepository extends JpaRepository<PdfData, Long> {}
