package com.lpu.leaderboard_service.controller;

import com.lpu.leaderboard_service.model.Response;
import com.lpu.leaderboard_service.model.ScoreEntry;
import com.lpu.leaderboard_service.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("leaderboard")
public class LeaderBoardController {

    @Autowired
    private LeaderBoardService leaderboardService;

    // Add score
    @PostMapping("submit")
    public ResponseEntity<ScoreEntry> submitQuizAndSaveScore(@RequestParam Integer quizId,
            @RequestParam Integer userId,
            @RequestBody List<Response> responses) {
        return leaderboardService.submitQuizAndSaveScore(quizId, userId, responses);
}

    // Get top scores
    @GetMapping("top")
    public ResponseEntity<List<ScoreEntry>> getTopScores() {
        return leaderboardService.getTopScores();
    }

    // Get scores for a specific user
    @GetMapping("user/{id}")
    public ResponseEntity<List<ScoreEntry>> getUserScores(@PathVariable Integer id) {
        return leaderboardService.getUserScores(id);
    }
}
