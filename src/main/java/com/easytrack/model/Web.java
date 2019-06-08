package com.easytrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="web")
@ToString(exclude = "items")
public class Web implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String priceTag;

    @OneToMany(mappedBy = "webId",
            fetch = FetchType.LAZY)
    private List<Item> items;
}
