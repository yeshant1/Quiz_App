package com.lpu.quiz_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lpu.quiz_service.dao.QuizDao;
import com.lpu.quiz_service.exception.CustomException;
import com.lpu.quiz_service.feign.PaymentClient;
import com.lpu.quiz_service.feign.QuestionClient;
import com.lpu.quiz_service.model.PaymentRequest;
import com.lpu.quiz_service.model.PaymentResponse;
import com.lpu.quiz_service.model.QuestionWrapper;
import com.lpu.quiz_service.model.Quiz;
import com.lpu.quiz_service.model.QuizDto;
import com.lpu.quiz_service.model.Response;

import org.springframework.http.HttpStatus;

@Service
public class QuizService {
    
    @Autowired
    QuizDao quizDao;
    
    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    QuestionClient questionClient;

    
    public ResponseEntity<List<QuestionWrapper>> createQuiz(QuizDto quizDto) {
        List<Integer> questionIds = questionClient.getQuestionsForQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions()).getBody();

        if (questionIds == null || questionIds.isEmpty()) {
            throw new CustomException("No questions found for the given category", HttpStatus.NOT_FOUND);
        }

        Quiz quiz = new Quiz();
        quiz.setQuestionIds(questionIds);
        quiz.setTitle(quizDto.getTitle()); 
        quizDao.save(quiz);

        return questionClient.getQuestionsFromId(questionIds);
    }


    public ResponseEntity<?> getQuizQuestions(Integer id, boolean isPremium, Long amount) {
        Optional<Quiz> optionalQuiz = quizDao.findById(id);
        if (optionalQuiz.isEmpty()) {
        	throw new CustomException("Quiz not found",HttpStatus.NOT_FOUND);
        }

        Quiz quiz = optionalQuiz.get();

        if (isPremium) {
            if (amount == null || amount < 5000) {
            	throw new CustomException("Amount must be at least ₹50 for premium quiz", HttpStatus.BAD_REQUEST);
            }

            PaymentRequest request = new PaymentRequest();
            request.setQuizTitle(quiz.getTitle());
            request.setAmount(amount);
            request.setCurrency("inr");
            request.setQuantity(1L);

            ResponseEntity<PaymentResponse> paymentResponse = paymentClient.checkout(request);
            return ResponseEntity.ok(paymentResponse);
        }

        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = questionClient.getQuestionsFromId(questionIds);
        
        if (questions.getBody() == null || questions.getBody().isEmpty()) {
        	throw new CustomException("No questions found for this quiz", HttpStatus.NOT_FOUND);
        }
        
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(List<Response> responses) {
        ResponseEntity<Integer> score = questionClient.getScore(responses);
        
        if (score.getBody() == null) {

      new CustomException("Failed to calculate score", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        
        return score;
    }
}
