package com.lpu.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
public class QuizDto {
    private String categoryName;
    private Integer numQuestions;
    private String title;
}
