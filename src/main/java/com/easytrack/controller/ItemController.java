package com.easytrack.controller;

import com.easytrack.model.PriceHistory;
import com.easytrack.repository.ItemRepository;
import com.easytrack.repository.PriceHistoryRepository;
import com.easytrack.repository.UserRepository;
import com.easytrack.repository.WebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebRepository webRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;



}
