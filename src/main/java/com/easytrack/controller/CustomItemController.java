package com.easytrack.controller;

import com.easytrack.model.*;
import com.easytrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CustomItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebRepository webRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private UserItemRepository userItemRepository;


    @PostMapping("/items/getItemsOfUser")
    public List<CustomItem> getItemsOfUser(@RequestBody User user){


        List<CustomItem> itemsWNAme = new ArrayList<>();

        User fullUser = userRepository.findByEmail(user.getEmail());

        List<UserItem> userItemList = userItemRepository.findByUser(fullUser);
        List<Long> itemIds = new ArrayList<>();
        for(UserItem userItem: userItemList){
            CustomItem customItem = new CustomItem();
            customItem.setName(userItem.getName());
            itemsWNAme.add(customItem);
            itemIds.add(userItem.getItem().getId());
        }
        List<Item> items = itemRepository.findAllById(itemIds);
        for(int i = 0; i < items.size(); i++) {
            itemsWNAme.get(i).setCurrentPrice(items.get(i).getCurrentPrice().toString());
            itemsWNAme.get(i).setMinPrice(items.get(i).getMinPrice().toString());
            itemsWNAme.get(i).setMaxPrice(items.get(i).getMaxPrice().toString());
            itemsWNAme.get(i).setUrl(items.get(i).getUrl());
        }

        return itemsWNAme;

    }

    @PostMapping("/items")
    public UserItem insertItem(@RequestBody CustomItem customItem) throws URISyntaxException{


        //Si el item no esta en la base de datos
        if(itemRepository.findByUrl(customItem.getUrl()) == null){
            return saveUserItemAndItem(customItem);
        } else {
            //Si el item esta en la base de datos
            return saveUserITem(customItem);
        }
    }
    @GetMapping("/items/getUrls")
    public List<Item> getUrls(){
        return itemRepository.findAll();
    }


    @PutMapping("/items")
    public void checkChangedPrice(@RequestBody CustomItem customItem){
        if(Double.parseDouble(customItem.getCurrentPrice()) != itemRepository.findByUrl(customItem.getUrl()).getCurrentPrice()){
            Item oldItem = itemRepository.findByUrl(customItem.getUrl());
            PriceHistory priceHistory = new PriceHistory();
            priceHistory.setItemId(oldItem);
            priceHistory.setPrice(oldItem.getCurrentPrice());
            priceHistory.setDate(oldItem.getUpdatedAt());
            priceHistoryRepository.save(priceHistory);
            oldItem.setCurrentPrice(Double.parseDouble(customItem.getCurrentPrice()));

            if(Double.parseDouble(customItem.getCurrentPrice()) > oldItem.getMaxPrice()){
                oldItem.setMaxPrice(Double.parseDouble(customItem.getCurrentPrice()));
            }
            if(Double.parseDouble(customItem.getCurrentPrice()) < oldItem.getMinPrice()){
                oldItem.setMinPrice(Double.parseDouble(customItem.getCurrentPrice()));
            }
            itemRepository.save(oldItem);
        }
    }

    @Transactional
    @DeleteMapping("/items")
    public ResponseEntity<?> deleteUserItem(@RequestParam String name){
        userItemRepository.deleteByName(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("items/chart")
    public List<CustomItem> getItemPriceHistory(@RequestParam String url){
        Item item = itemRepository.findByUrl(url);
        List<PriceHistory> priceHistoryList = getLastRecords(item);
        List<CustomItem> itemPriceHistory = new ArrayList<>();
        CustomItem customItem1 = new CustomItem();
        customItem1.setCurrentPrice(item.getCurrentPrice().toString());
        customItem1.setDate(item.getUpdatedAt());
        itemPriceHistory.add(customItem1);
        for(PriceHistory priceHistory: priceHistoryList){
            CustomItem iterable = new CustomItem();
            iterable.setDate(priceHistory.getDate());
            iterable.setCurrentPrice(priceHistory.getPrice().toString());
            itemPriceHistory.add(iterable);
        }
        return itemPriceHistory;

    }

    @GetMapping("/items/priceTag")
    public Web getWeb(@RequestParam String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        domain =  domain.startsWith("www.") ? domain.substring(4) : domain;
        domain = domain.endsWith(".es") ? domain.substring(0, domain.length() - 3) : domain;
        domain = domain.endsWith("com") ? domain.substring(0, domain.length() - 4) : domain;

        return webRepository.findByName(domain);

    }

    //Private methods

    private List<PriceHistory> getLastRecords(Item item){

        List<PriceHistory> priceHistoryList = priceHistoryRepository.getAllByItemIdOrderByDateDesc(item);
        if(priceHistoryList.size() >= 9){
            priceHistoryList.subList(0, 8);
        }
        return priceHistoryList;
    }

    private UserItem saveUserITem(@RequestBody CustomItem customItem) {
        User fullUser = userRepository.findByEmail(customItem.getEmail());
        Item fullItem = itemRepository.findByUrl(customItem.getUrl());
        List<UserItem> userItemList = userItemRepository.findByUser(fullUser);
        for(UserItem userItem: userItemList){
            //Si el usuario ya esta siguiendo el objecto devuelve null
            if(userItem.getItem().equals(fullItem)){
                return null;
            }
        }
        //Si el usuario no esta siguiendo el objeto, se crea la relacion en userItem
        UserItem userItem = new UserItem();
        userItem.setUser(fullUser);
        userItem.setItem(fullItem);
        userItem.setName(customItem.getName());
        return userItemRepository.save(userItem);
    }

    private UserItem saveUserItemAndItem(@RequestBody CustomItem customItem) throws URISyntaxException {
        Item item = saveItem(customItem);

        User fullUser = userRepository.findByEmail(customItem.getEmail());
        Item fullItem = itemRepository.findByUrl(item.getUrl());
        UserItem userItem = new UserItem();
        userItem.setItem(fullItem);
        userItem.setUser(fullUser);
        userItem.setName(customItem.getName());
        return userItemRepository.save(userItem);
    }

    private Item saveItem(@RequestBody CustomItem customItem) throws URISyntaxException {
        Item item = new Item();
        item.setCurrentPrice(Double.parseDouble(customItem.getCurrentPrice()));
        item.setMaxPrice(Double.parseDouble(customItem.getCurrentPrice()));
        item.setMinPrice(Double.parseDouble(customItem.getCurrentPrice()));
        item.setUrl(customItem.getUrl());

        Web web = getWeb(customItem.getUrl());
        item.setWebId(web);
        itemRepository.save(item);
        return item;
    }
}
