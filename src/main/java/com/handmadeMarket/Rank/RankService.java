package com.handmadeMarket.Rank;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {
    private final RankRepository rankRepository;

    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    public List<Rank> getAllRanks() {
        return rankRepository.findAll();
    }

    public Rank getRankByNumber(int rankNumber) {
       Rank rank =  rankRepository.findByRankNumber(rankNumber);
        if (rank == null) {
               throw new ResourceNotFoundException("Rank not found with number: " + rankNumber);
        }
        return rank;
    }

    public Rank getRankById(String id) {
        return rankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rank not found with id: " + id));
    }

    public Rank createRank(Rank rank) {
        return rankRepository.save(rank);
    }

    public Rank updateRank(String id, Rank rank) {
        rankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rank not found with id: " + id));
        Rank updatedRank = new Rank();
        updatedRank.setId(id);
        updatedRank.setRankNumber(rank.getRankNumber());
        updatedRank.setRankName(rank.getRankName());
        updatedRank.setPoints(rank.getPoints());
        updatedRank.setRankDiscount(rank.getRankDiscount());

       return rankRepository.save(updatedRank);
    }

    public void deleteRank(String id) {
        rankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rank not found with id: " + id));
        rankRepository.deleteById(id);
    }
}
