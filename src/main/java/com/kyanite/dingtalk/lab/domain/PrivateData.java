package com.kyanite.dingtalk.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.dingtalk.lab.domain.enumeration.ItemType;
import com.kyanite.dingtalk.lab.domain.enumeration.TypesOfFee;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PrivateData.
 */
@Entity
@Table(name = "private_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrivateData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fee")
    private Integer fee;

    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    @Column(name = "types_of_fee")
    private TypesOfFee typesOfFee;

    @ManyToOne
    @JsonIgnoreProperties(value = { "privateData" }, allowSetters = true)
    private PublicData publicData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrivateData id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PrivateData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFee() {
        return this.fee;
    }

    public PrivateData fee(Integer fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getReason() {
        return this.reason;
    }

    public PrivateData reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public PrivateData itemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public TypesOfFee getTypesOfFee() {
        return this.typesOfFee;
    }

    public PrivateData typesOfFee(TypesOfFee typesOfFee) {
        this.typesOfFee = typesOfFee;
        return this;
    }

    public void setTypesOfFee(TypesOfFee typesOfFee) {
        this.typesOfFee = typesOfFee;
    }

    public PublicData getPublicData() {
        return this.publicData;
    }

    public PrivateData publicData(PublicData publicData) {
        this.setPublicData(publicData);
        return this;
    }

    public void setPublicData(PublicData publicData) {
        this.publicData = publicData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrivateData)) {
            return false;
        }
        return id != null && id.equals(((PrivateData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrivateData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fee=" + getFee() +
            ", reason='" + getReason() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", typesOfFee='" + getTypesOfFee() + "'" +
            "}";
    }
}
