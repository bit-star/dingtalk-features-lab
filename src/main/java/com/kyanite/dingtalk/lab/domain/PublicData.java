package com.kyanite.dingtalk.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.dingtalk.lab.domain.enumeration.ItemType;
import com.kyanite.dingtalk.lab.domain.enumeration.TypesOfFee;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PublicData.
 */
@Entity
@Table(name = "public_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PublicData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fee", precision = 21, scale = 2)
    private BigDecimal fee;

    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    @Column(name = "types_of_fee")
    private TypesOfFee typesOfFee;

    @Column(name = "agree")
    private Boolean agree;

    @OneToMany(mappedBy = "publicData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicData", "ddUsers" }, allowSetters = true)
    private Set<PrivateData> privateData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublicData id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PublicData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public PublicData fee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getReason() {
        return this.reason;
    }

    public PublicData reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public PublicData itemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public TypesOfFee getTypesOfFee() {
        return this.typesOfFee;
    }

    public PublicData typesOfFee(TypesOfFee typesOfFee) {
        this.typesOfFee = typesOfFee;
        return this;
    }

    public void setTypesOfFee(TypesOfFee typesOfFee) {
        this.typesOfFee = typesOfFee;
    }

    public Boolean getAgree() {
        return this.agree;
    }

    public PublicData agree(Boolean agree) {
        this.agree = agree;
        return this;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public Set<PrivateData> getPrivateData() {
        return this.privateData;
    }

    public PublicData privateData(Set<PrivateData> privateData) {
        this.setPrivateData(privateData);
        return this;
    }

    public PublicData addPrivateData(PrivateData privateData) {
        this.privateData.add(privateData);
        privateData.setPublicData(this);
        return this;
    }

    public PublicData removePrivateData(PrivateData privateData) {
        this.privateData.remove(privateData);
        privateData.setPublicData(null);
        return this;
    }

    public void setPrivateData(Set<PrivateData> privateData) {
        if (this.privateData != null) {
            this.privateData.forEach(i -> i.setPublicData(null));
        }
        if (privateData != null) {
            privateData.forEach(i -> i.setPublicData(this));
        }
        this.privateData = privateData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicData)) {
            return false;
        }
        return id != null && id.equals(((PublicData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fee=" + getFee() +
            ", reason='" + getReason() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", typesOfFee='" + getTypesOfFee() + "'" +
            ", agree='" + getAgree() + "'" +
            "}";
    }
}
