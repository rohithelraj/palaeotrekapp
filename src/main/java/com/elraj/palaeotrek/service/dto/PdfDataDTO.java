package com.elraj.palaeotrek.service.dto;

import com.elraj.palaeotrek.domain.enumeration.DetailedType;
import com.elraj.palaeotrek.domain.enumeration.DnaType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.elraj.palaeotrek.domain.PdfData} entity.
 */
public class PdfDataDTO implements Serializable {

    private Long id;

    @NotNull
    private DnaType dnaType;

    @NotNull
    private DetailedType detailedType;

    @NotNull
    private String pdfId;

    @NotNull
    private LocalDate dateOfCreation;

    @NotNull
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DnaType getDnaType() {
        return dnaType;
    }

    public void setDnaType(DnaType dnaType) {
        this.dnaType = dnaType;
    }

    public DetailedType getDetailedType() {
        return detailedType;
    }

    public void setDetailedType(DetailedType detailedType) {
        this.detailedType = detailedType;
    }

    public String getPdfId() {
        return pdfId;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdfDataDTO)) {
            return false;
        }

        PdfDataDTO pdfDataDTO = (PdfDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pdfDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PdfDataDTO{" +
            "id=" + getId() +
            ", dnaType='" + getDnaType() + "'" +
            ", detailedType='" + getDetailedType() + "'" +
            ", pdfId='" + getPdfId() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
