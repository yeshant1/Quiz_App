package com.lpu.leaderboard_service.repository;

import com.lpu.leaderboard_service.model.ScoreEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<ScoreEntry, Integer> {

    // Fetch top 10 scores in descending order
    List<ScoreEntry> findTop10ByOrderByScoreDesc();

    // Fetch scores for a specific user, ordered by datetime (most recent first)
    List<ScoreEntry> findByUserIdOrderByDateTimeDesc(Integer userId);
}
