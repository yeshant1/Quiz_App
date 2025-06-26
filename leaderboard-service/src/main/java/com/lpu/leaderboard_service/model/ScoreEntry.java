package com.lpu.leaderboard_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
@Entity
public class ScoreEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private Integer quizId;
    private Integer score;
    private LocalDateTime dateTime;
}
