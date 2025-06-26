package com.lpu.leaderboard_service.service;

import com.lpu.leaderboard_service.feign.QuizServiceClient;
import com.lpu.leaderboard_service.model.Response;
import com.lpu.leaderboard_service.model.ScoreEntry;
import com.lpu.leaderboard_service.repository.ScoreRepository;
import com.lpu.leaderboard_service.exception.CustomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class LeaderBoardService {

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private QuizServiceClient quizServiceClient;

    // Add score to the leaderboard
    public ResponseEntity<ScoreEntry> submitQuizAndSaveScore(Integer quizId, Integer userId, List<Response> responses) {
        if (Objects.isNull(quizId) || Objects.isNull(userId) || Objects.isNull(responses)) {
            throw new CustomException("Quiz ID, User ID, and Responses must not be null", HttpStatus.BAD_REQUEST);
        }

        try {
            ResponseEntity<Integer> response = quizServiceClient.submitQuiz(responses);
            Integer score = response.getBody();

            if (Objects.isNull(score)) {
                throw new CustomException("Score must not be null", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            ScoreEntry scoreEntry = new ScoreEntry();
            scoreEntry.setQuizId(quizId);
            scoreEntry.setUserId(userId);
            scoreEntry.setScore(score);
            scoreEntry.setDateTime(LocalDateTime.now());

            return new ResponseEntity<>(scoreRepository.save(scoreEntry), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomException("Failed to submit quiz and save score",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ScoreEntry>> getTopScores() {
        try {
            List<ScoreEntry> topScores = scoreRepository.findTop10ByOrderByScoreDesc();
            return new ResponseEntity<>(topScores, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Failed to fetch top scores", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ScoreEntry>> getUserScores(Integer userId) {
        if (Objects.isNull(userId)) {
            throw new CustomException("User ID must not be null", HttpStatus.BAD_REQUEST);
        }

        try {
            List<ScoreEntry> userScores = scoreRepository.findByUserIdOrderByDateTimeDesc(userId);
            return new ResponseEntity<>(userScores, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Failed to fetch user scores", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
