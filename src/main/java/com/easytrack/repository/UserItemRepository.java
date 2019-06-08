package com.easytrack.repository;

import com.easytrack.model.UserItem;
import com.easytrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, User> {

    List<UserItem> findByUser(User user);

    @Transactional
    void deleteByName(String name);

}
