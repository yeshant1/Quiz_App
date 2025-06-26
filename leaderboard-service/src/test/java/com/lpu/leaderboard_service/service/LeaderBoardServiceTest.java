package com.lpu.leaderboard_service.service;

import com.lpu.leaderboard_service.feign.QuizServiceClient;
import com.lpu.leaderboard_service.model.Response;
import com.lpu.leaderboard_service.model.ScoreEntry;
import com.lpu.leaderboard_service.repository.ScoreRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaderBoardServiceTest {

    @InjectMocks
    private LeaderBoardService leaderBoardService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private QuizServiceClient quizServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmitQuizAndSaveScore_Success() {
        List<Response> responses = new ArrayList<>();
        when(quizServiceClient.submitQuiz(responses)).thenReturn(new ResponseEntity<>(8, HttpStatus.OK));

        ScoreEntry savedEntry = new ScoreEntry();
        savedEntry.setQuizId(1);
        savedEntry.setUserId(101);
        savedEntry.setScore(8);
        savedEntry.setDateTime(LocalDateTime.now());

        when(scoreRepository.save(any(ScoreEntry.class))).thenReturn(savedEntry);

        ResponseEntity<ScoreEntry> response = leaderBoardService.submitQuizAndSaveScore(1, 101, responses);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(8, response.getBody().getScore());
        assertEquals(101, response.getBody().getUserId());
    }

    @Test
    void testGetTopScores_Success() {
        List<ScoreEntry> topScores = List.of(new ScoreEntry(), new ScoreEntry());
        when(scoreRepository.findTop10ByOrderByScoreDesc()).thenReturn(topScores);

        ResponseEntity<List<ScoreEntry>> response = leaderBoardService.getTopScores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetUserScores_Success() {
        List<ScoreEntry> userScores = List.of(new ScoreEntry());
        when(scoreRepository.findByUserIdOrderByDateTimeDesc(101)).thenReturn(userScores);

        ResponseEntity<List<ScoreEntry>> response = leaderBoardService.getUserScores(101);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
