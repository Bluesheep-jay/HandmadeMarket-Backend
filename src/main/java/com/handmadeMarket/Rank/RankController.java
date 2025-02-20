package com.handmadeMarket.Rank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ranks")
public class RankController {
    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping
    public List<Rank> getAllRanks() {
        return rankService.getAllRanks();
    }

    @GetMapping("/number/{rankNumber}")
    public ResponseEntity<Rank> getRankByNumber(@PathVariable int rankNumber) {
        Rank rank = rankService.getRankByNumber(rankNumber);
        return ResponseEntity.ok(rank);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rank> getRankById(@PathVariable String id) {
        Rank rank = rankService.getRankById(id);
        return ResponseEntity.ok(rank);
    }

    @PostMapping
    public ResponseEntity<Rank> createRank(@RequestBody Rank rank) {
        Rank createdRank = rankService.createRank(rank);
        return ResponseEntity.ok(createdRank);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rank> updateRank(@PathVariable String id, @RequestBody Rank rank) {
        return ResponseEntity.ok(rankService.updateRank(id, rank));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRank(@PathVariable String id) {
        rankService.deleteRank(id);
        return ResponseEntity.noContent().build();
    }
}
