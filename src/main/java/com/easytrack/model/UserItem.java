package com.easytrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user_item")
public class UserItem implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private UserItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    private Item item;

    @NotBlank
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserItem)) return false;
        UserItem userItem = (UserItem) o;
        return Objects.equals(getId(), userItem.getId()) &&
                Objects.equals(getUser(), userItem.getUser()) &&
                Objects.equals(getItem(), userItem.getItem()) &&
                Objects.equals(getName(), userItem.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getItem(), getName());
    }
}
