package com.handmadeMarket.Seaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class SearchSuggestionController {

    @Autowired
    private SearchSuggestionRepository searchSuggestionRepository;

    @GetMapping("/search-suggestions")
    public ResponseEntity<List<String>> getSearchSuggestions(@RequestParam String q) {
        if (q == null || q.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<String> suggestions = searchSuggestionRepository.getSearchSuggestions(q);
        return ResponseEntity.ok(suggestions);
    }


}
