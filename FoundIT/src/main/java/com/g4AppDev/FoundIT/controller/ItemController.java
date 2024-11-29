package com.g4AppDev.FoundIT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.g4AppDev.FoundIT.entity.Item;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.ItemRepository;
import com.g4AppDev.FoundIT.service.ItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/items")
public class ItemController {
// example
    @Autowired
    private ItemService itemService;
    private ItemRepository itemRepository;
    private Item item;
    
    @GetMapping("/getAllItems")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
   
    @GetMapping("/getItemDetails/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id); // Use the service to fetch the item
        if (item != null) {
            return ResponseEntity.ok(item); // Return the item if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        }
    }
 
    @PostMapping("/postItem")
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    
    @PutMapping("/putItemDetails/{id}")
    public Item putItemDetails(@PathVariable Long id, @RequestBody Item itemDetails) {
        return itemService.updateItem(id, itemDetails);
    }

    
    @DeleteMapping("/deleteItemDetails/{id}")
    public String deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);
    }
    @GetMapping("/getLatestItems")
    public List<Item> getLatestItems(@RequestParam(defaultValue = "5") int count) {
        return itemService.getLatestItems(count); // Fetch the latest items based on the count
    }
    @GetMapping("/getCountItem")
    public ResponseEntity<Map<String, Long>> getEntityCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("user_count", itemService.getItemCount());
        return ResponseEntity.ok(counts);
    }
    
    @GetMapping("/getFoundItemCount")
    public ResponseEntity<Map<String, Long>> getFoundItemCount() {
        long foundItemCount = itemService.countFoundItems();
        Map<String, Long> response = new HashMap<>();
        response.put("found_item_count", foundItemCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getLostItemCount")
    public ResponseEntity<Map<String, Long>> getLostItemCount() {
        long foundItemCount = itemService.countLostItem();
        Map<String, Long> response = new HashMap<>();
        response.put("lost_item_count", foundItemCount);
        return ResponseEntity.ok(response);
    }
    
}
