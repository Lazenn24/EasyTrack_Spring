package com.easytrack.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserItemId implements Serializable {

    private Long user_id;

    private Long item_id;

}
