package com.easytrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ToString(exclude = {"webId", "userItems"})
@NoArgsConstructor
@Entity
public class Item extends  AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double currentPrice;

    @NotNull
    private Double maxPrice;

    @NotNull
    private Double minPrice;

    @Column(unique = true)
    @NotNull
    private String url;

    @OneToMany(mappedBy = "item")
    private List<UserItem> userItems;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "web_id", nullable = false)
    @JsonIgnore
    private Web webId;

}
