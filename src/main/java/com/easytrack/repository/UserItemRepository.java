package com.easytrack.repository;

import com.easytrack.model.UserItem;
import com.easytrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, User> {

    List<UserItem> findByUser(User user);

}
