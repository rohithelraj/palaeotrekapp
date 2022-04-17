package com.elraj.palaeotrek.domain;

import com.elraj.palaeotrek.domain.enumeration.DetailedType;
import com.elraj.palaeotrek.domain.enumeration.DnaType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PdfData.
 */
@Entity
@Table(name = "pdf_data")
public class PdfData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dna_type", nullable = false)
    private DnaType dnaType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "detailed_type", nullable = false)
    private DetailedType detailedType;

    @NotNull
    @Column(name = "pdf_id", nullable = false, unique = true)
    private String pdfId;

    @NotNull
    @Column(name = "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Lob
    @Column(name = "pdf")
    private byte[] pdf;

    @Column(name = "pdf_content_type")
    private String pdfContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PdfData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DnaType getDnaType() {
        return this.dnaType;
    }

    public PdfData dnaType(DnaType dnaType) {
        this.setDnaType(dnaType);
        return this;
    }

    public void setDnaType(DnaType dnaType) {
        this.dnaType = dnaType;
    }

    public DetailedType getDetailedType() {
        return this.detailedType;
    }

    public PdfData detailedType(DetailedType detailedType) {
        this.setDetailedType(detailedType);
        return this;
    }

    public void setDetailedType(DetailedType detailedType) {
        this.detailedType = detailedType;
    }

    public String getPdfId() {
        return this.pdfId;
    }

    public PdfData pdfId(String pdfId) {
        this.setPdfId(pdfId);
        return this;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }

    public LocalDate getDateOfCreation() {
        return this.dateOfCreation;
    }

    public PdfData dateOfCreation(LocalDate dateOfCreation) {
        this.setDateOfCreation(dateOfCreation);
        return this;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getUserId() {
        return this.userId;
    }

    public PdfData userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getPdf() {
        return this.pdf;
    }

    public PdfData pdf(byte[] pdf) {
        this.setPdf(pdf);
        return this;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public String getPdfContentType() {
        return this.pdfContentType;
    }

    public PdfData pdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
        return this;
    }

    public void setPdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdfData)) {
            return false;
        }
        return id != null && id.equals(((PdfData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PdfData{" +
            "id=" + getId() +
            ", dnaType='" + getDnaType() + "'" +
            ", detailedType='" + getDetailedType() + "'" +
            ", pdfId='" + getPdfId() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", userId='" + getUserId() + "'" +
            ", pdf='" + getPdf() + "'" +
            ", pdfContentType='" + getPdfContentType() + "'" +
            "}";
    }
}
