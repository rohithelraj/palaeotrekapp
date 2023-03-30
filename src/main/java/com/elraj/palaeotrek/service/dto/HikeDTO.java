package com.elraj.palaeotrek.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.elraj.palaeotrek.domain.Hike} entity.
 */
public class HikeDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate hikeDate;

    @NotNull
    private String komootMap;

    private String description1;

    private String description2;

    @NotNull
    private String trainConnection;

    private String imageData;

    @Lob
    private byte[] imageBlob;

    private String imageBlobContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHikeDate() {
        return hikeDate;
    }

    public void setHikeDate(LocalDate hikeDate) {
        this.hikeDate = hikeDate;
    }

    public String getKomootMap() {
        return komootMap;
    }

    public void setKomootMap(String komootMap) {
        this.komootMap = komootMap;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getTrainConnection() {
        return trainConnection;
    }

    public void setTrainConnection(String trainConnection) {
        this.trainConnection = trainConnection;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return imageBlobContentType;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HikeDTO)) {
            return false;
        }

        HikeDTO hikeDTO = (HikeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hikeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HikeDTO{" +
            "id=" + getId() +
            ", hikeDate='" + getHikeDate() + "'" +
            ", komootMap='" + getKomootMap() + "'" +
            ", description1='" + getDescription1() + "'" +
            ", description2='" + getDescription2() + "'" +
            ", trainConnection='" + getTrainConnection() + "'" +
            ", imageData='" + getImageData() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            "}";
    }
}
