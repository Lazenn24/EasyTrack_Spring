package com.easytrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.easytrack.model.PriceHistory;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Long> {
}
