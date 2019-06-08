package com.easytrack.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomItem {

    private String email;

    private String name;

    private Date date;

    private String currentPrice;

    private String minPrice;

    private String maxPrice;

    private List<PriceHistory> priceHistoryItem;

    private String url;


}
