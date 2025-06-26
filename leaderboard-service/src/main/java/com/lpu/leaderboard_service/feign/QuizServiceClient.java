package com.lpu.leaderboard_service.feign;

import com.lpu.leaderboard_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "quiz-service")
public interface QuizServiceClient {

    @PostMapping("/quiz/submit")
    ResponseEntity<Integer> submitQuiz(@RequestBody List<Response> responses);
}
