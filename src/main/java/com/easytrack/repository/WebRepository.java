package com.easytrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easytrack.model.Web;
import org.springframework.stereotype.Repository;

@Repository
public interface WebRepository extends JpaRepository<Web, String> {

    Web findByName(String name);
}
