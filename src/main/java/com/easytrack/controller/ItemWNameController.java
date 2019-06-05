package com.easytrack.controller;

import com.easytrack.model.*;
import com.easytrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemWNameController {

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


    @PostMapping("/items/itemListByUser")
    public List<ItemWName> getItemsByUser(@Valid @RequestBody User user){


        List<ItemWName> itemsWNAme = new ArrayList<>();

        User fullUser = userRepository.findByEmail(user.getEmail());

        List<UserItem> userItemList = userItemRepository.findByUser(fullUser);
        List<Long> itemIds = new ArrayList<>();
        for(UserItem userItem: userItemList){
            ItemWName itemWName = new ItemWName();
            itemWName.setName(userItem.getName());
            itemsWNAme.add(itemWName);
            itemIds.add(userItem.getItem().getId());
        }
        List<Item> items = itemRepository.findAllById(itemIds);
        for(Item item: items){
            for(ItemWName namedItem: itemsWNAme){
                namedItem.setCurrentPrice(item.getCurrentPrice());
                namedItem.setMinPrice(item.getMinPrice());
                namedItem.setMaxPrice(item.getMaxPrice());
            }
            break;
        }

        return itemsWNAme;

    }

    @PostMapping("/items/insertItem")
    public UserItem insertItem(@RequestBody ItemWName itemWName){

        //Si el item no esta en la base de datos
        if(itemRepository.findByUrl(itemWName.getUrl()) == null){
            Item item = new Item();
            item.setCurrentPrice(itemWName.getCurrentPrice());
            item.setMaxPrice(itemWName.getMaxPrice());
            item.setMinPrice(itemWName.getMinPrice());
            item.setUrl(item.getUrl());

            //Cambiar al hacer metodo de web
            Web web = new Web();
            web.setName("Amazon");
            web.setPriceTag("sdrfgbadsr");
            item.setWebId(web);
            itemRepository.save(item);

            User fullUser = userRepository.findByEmail(itemWName.getEmail());
            Item fullItem = itemRepository.findByUrl(item.getUrl());
            UserItem userItem = new UserItem();
            userItem.setItem(fullItem);
            userItem.setUser(fullUser);
            userItem.setName(itemWName.getName());
            return userItemRepository.save(userItem);
        //Si el item esta en la base de datos
        } else {
            User fullUser = userRepository.findByEmail(itemWName.getEmail());
            Item fullItem = itemRepository.findByUrl(itemWName.getUrl());
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
            userItem.setName(itemWName.getName());
            return userItemRepository.save(userItem);
        }
    }


}
