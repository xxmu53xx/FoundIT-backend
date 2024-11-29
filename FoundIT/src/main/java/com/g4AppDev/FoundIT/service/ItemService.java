package com.g4AppDev.FoundIT.service;

import com.g4AppDev.FoundIT.entity.Item;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.ItemRepository;
import com.g4AppDev.FoundIT.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
   
    @Autowired
    private UserRepo userRepository;
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        // Use Optional to safely get the item or throw an exception if not found
        return itemRepository.findById(id)
                .orElse(null); // Return null if not found, or you can throw an exception
    }
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

   
    @SuppressWarnings("finally")
    public Item updateItem(Long id, Item itemDetails) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        // Update basic fields
        item.setDescription(itemDetails.getDescription());
        item.setDateLostOrFound(itemDetails.getDateLostOrFound());
        item.setLocation(itemDetails.getLocation());
        item.setStatus(itemDetails.getStatus());
        item.setImage(itemDetails.getImage());
        item.setisClaimed(itemDetails.getisClaimed());
        item.setIsVerified(itemDetails.getIsVerified());

        // Update user association if provided
        if (itemDetails.getUser() != null && itemDetails.getUser().getUserID() != null) {
            UserEntity newUser = userRepository.findById(itemDetails.getUser().getUserID())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
            item.setUser(newUser);
        }

        return itemRepository.save(item);
    }
    public String deleteItem(Long id) {
        String msg;

        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            msg = "Item record successfully deleted";
        } else {
            msg = id + " NOT found!";
        }
        return msg;
    }
    public List<Item> getLatestItems(int count) {
        return itemRepository.findAll().stream()
                .sorted((i1, i2) -> Long.compare(i2.getItemID(), i1.getItemID())) 
                .limit(count) 
                .collect(Collectors.toList());
    }
    
    public long getItemCount() {
        return itemRepository.count();
    }
    
    public long countFoundItems() {
    	 return itemRepository.countByStatusAndIsVerified("Found");// Assuming "state" is the field that stores the status (e.g., "Found" or "Lost")
    }
    
    public long countLostItem() {
    	 return itemRepository.countByStatusAndIsVerified("Lost");
    }
    
    
}