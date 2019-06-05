package com.easytrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="item")
public class Item extends  AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Double currentPrice;

    @NotBlank
    private Double maxPrice;

    @NotBlank
    private Double minPrice;

    @Column(unique = true)
    @NotBlank
    private String url;

    @OneToMany(mappedBy = "item",
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    @OrderBy("checkedDate DESC")
    private List<UserItem> userItems;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "web_id", nullable = false)
    @NotBlank
    private Web webId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId().equals(item.getId()) &&
                Objects.equals(getCurrentPrice(), item.getCurrentPrice()) &&
                Objects.equals(getMaxPrice(), item.getMaxPrice()) &&
                Objects.equals(getMinPrice(), item.getMinPrice()) &&
                getUrl().equals(item.getUrl()) &&
                Objects.equals(getUserItems(), item.getUserItems()) &&
                Objects.equals(getWebId(), item.getWebId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCurrentPrice(), getMaxPrice(), getMinPrice(), getUrl(), getUserItems(), getWebId());
    }
}
