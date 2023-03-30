package com.elraj.palaeotrek.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Hike.
 */
@Entity
@Table(name = "hike")
public class Hike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "hike_date", nullable = false)
    private LocalDate hikeDate;

    @NotNull
    @Column(name = "komoot_map", nullable = false)
    private String komootMap;

    @Column(name = "description_1")
    private String description1;

    @Column(name = "description_2")
    private String description2;

    @NotNull
    @Column(name = "train_connection", nullable = false)
    private String trainConnection;

    @Column(name = "image_data")
    private String imageData;

    @Lob
    @Column(name = "image_blob")
    private byte[] imageBlob;

    @Column(name = "image_blob_content_type")
    private String imageBlobContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hike id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHikeDate() {
        return this.hikeDate;
    }

    public Hike hikeDate(LocalDate hikeDate) {
        this.setHikeDate(hikeDate);
        return this;
    }

    public void setHikeDate(LocalDate hikeDate) {
        this.hikeDate = hikeDate;
    }

    public String getKomootMap() {
        return this.komootMap;
    }

    public Hike komootMap(String komootMap) {
        this.setKomootMap(komootMap);
        return this;
    }

    public void setKomootMap(String komootMap) {
        this.komootMap = komootMap;
    }

    public String getDescription1() {
        return this.description1;
    }

    public Hike description1(String description1) {
        this.setDescription1(description1);
        return this;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return this.description2;
    }

    public Hike description2(String description2) {
        this.setDescription2(description2);
        return this;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getTrainConnection() {
        return this.trainConnection;
    }

    public Hike trainConnection(String trainConnection) {
        this.setTrainConnection(trainConnection);
        return this;
    }

    public void setTrainConnection(String trainConnection) {
        this.trainConnection = trainConnection;
    }

    public String getImageData() {
        return this.imageData;
    }

    public Hike imageData(String imageData) {
        this.setImageData(imageData);
        return this;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public byte[] getImageBlob() {
        return this.imageBlob;
    }

    public Hike imageBlob(byte[] imageBlob) {
        this.setImageBlob(imageBlob);
        return this;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return this.imageBlobContentType;
    }

    public Hike imageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
        return this;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hike)) {
            return false;
        }
        return id != null && id.equals(((Hike) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hike{" +
            "id=" + getId() +
            ", hikeDate='" + getHikeDate() + "'" +
            ", komootMap='" + getKomootMap() + "'" +
            ", description1='" + getDescription1() + "'" +
            ", description2='" + getDescription2() + "'" +
            ", trainConnection='" + getTrainConnection() + "'" +
            ", imageData='" + getImageData() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobContentType='" + getImageBlobContentType() + "'" +
            "}";
    }
}
