package com.g4AppDev.FoundIT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.g4AppDev.FoundIT.entity.Item;
import com.g4AppDev.FoundIT.service.ItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/getAllItems")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    
    @PostMapping("/postItem")
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    
    @PutMapping("/putItemDetails/{id}")
    public Item putItemDetails(@PathVariable int id, @RequestBody Item itemDetails) {
        return itemService.updateItem(id, itemDetails);
    }

    
    @DeleteMapping("/deleteItemDetails/{id}")
    public String deleteItem(@PathVariable int id) {
        return itemService.deleteItem(id);
    }
    @GetMapping("/getLatestItems")
    public List<Item> getLatestItems(@RequestParam(defaultValue = "5") int count) {
        return itemService.getLatestItems(count); // Fetch the latest items based on the count
    }
    
    
}
