package com.easytrack.repository;

import com.easytrack.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.easytrack.model.PriceHistory;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Item> {

    List<PriceHistory> getAllByItemIdOrderByDateDesc(Item item);
}
