package com.g4AppDev.FoundIT.service;

import com.g4AppDev.FoundIT.entity.Item;
import com.g4AppDev.FoundIT.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(int id) {
        return itemRepository.findById(id);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

   
    @SuppressWarnings("finally")
    public Item updateItem(int id, Item itemDetails) {
        Item item = new Item();
        try {
            item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item " + id + " not found"));
            item.setDescription(itemDetails.getDescription());
            item.setDateLostOrFound(itemDetails.getDateLostOrFound());
            item.setRegisteredBy(itemDetails.getRegisteredBy());
            item.setLocation(itemDetails.getLocation());
            item.setStatus(itemDetails.getStatus());
        } catch (NoSuchElementException nex) {
            throw new RuntimeException("Item " + id + " not found");
        } finally {
            return itemRepository.save(item);
        }
    }

    // Delete item
    public String deleteItem(int id) {
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
                .sorted((i1, i2) -> Long.compare(i2.getItemID(), i1.getItemID())) // Sort by Item ID descending
                .limit(count) // Limit to the specified count
                .collect(Collectors.toList());
    }
}