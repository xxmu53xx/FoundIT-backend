package com.g4AppDev.FoundIT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
// example
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
    
}
