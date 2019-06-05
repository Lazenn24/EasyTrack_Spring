package com.easytrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class UserItemId implements Serializable {
    private Long userId;
    private Long itemId;

    public UserItemId(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserItemId)) return false;
        UserItemId that = (UserItemId) o;
        return getUserId().equals(that.getUserId()) &&
                getItemId().equals(that.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getItemId());
    }
}
