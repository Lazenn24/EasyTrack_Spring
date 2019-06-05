package com.easytrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemWName {

    private String email;

    private String name;

    private Double currentPrice;

    private Double minPrice;

    private Double maxPrice;

    private List<PriceHistory> priceHistoryItem;

    private String url;

}
