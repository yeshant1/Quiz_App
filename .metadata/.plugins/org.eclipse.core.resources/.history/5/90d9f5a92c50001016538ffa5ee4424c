package com.lpu.quiz_service.service;

import com.lpu.quiz_service.dao.QuizDao;
import com.lpu.quiz_service.exception.CustomException;
import com.lpu.quiz_service.feign.PaymentClient;
import com.lpu.quiz_service.feign.QuestionClient;
import com.lpu.quiz_service.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private QuizDao quizDao;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private QuestionClient questionClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz_Success() {
        List<Integer> questionIds = Arrays.asList(1, 2, 3);
        QuizDto quizDto = new QuizDto("java", 3, "Java Basics");

        when(questionClient.getQuestionsForQuiz("java", 3))
                .thenReturn(new ResponseEntity<>(questionIds, HttpStatus.OK));

        Quiz savedQuiz = new Quiz();
        savedQuiz.setTitle("Java Basics");
        savedQuiz.setQuestionIds(questionIds);

        when(quizDao.save(any(Quiz.class))).thenReturn(savedQuiz);

        // Mocking questionClient.getQuestionsFromId(...) to return a dummy list of QuestionWrapper
        List<QuestionWrapper> questionWrappers = Arrays.asList(
            new QuestionWrapper(/* initialize as needed */),
            new QuestionWrapper(/* ... */)
        );
        when(questionClient.getQuestionsFromId(questionIds))
                .thenReturn(new ResponseEntity<>(questionWrappers, HttpStatus.OK));

        ResponseEntity<List<QuestionWrapper>> response = quizService.createQuiz(quizDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionWrappers, response.getBody());
    }


    @Test
    void testCreateQuiz_NoQuestionsFound() {
        QuizDto quizDto = new QuizDto("java", 3, "Sample Title");

        when(questionClient.getQuestionsForQuiz("java", 3))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));

        CustomException exception = assertThrows(CustomException.class, () ->
                quizService.createQuiz(quizDto));

        assertEquals("No questions found for the given category", exception.getMessage());
    }


    @Test
    void testGetQuizQuestions_Premium_Success() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Premium Quiz");
        quiz.setQuestionIds(Arrays.asList(1, 2, 3));

        when(quizDao.findById(1)).thenReturn(Optional.of(quiz));

        PaymentRequest request = new PaymentRequest();
        request.setQuizTitle("Premium Quiz");
        request.setAmount(5000L);
        request.setCurrency("inr");
        request.setQuantity(1L);

        PaymentResponse paymentResponse = new PaymentResponse();
        when(paymentClient.checkout(any(PaymentRequest.class)))
                .thenReturn(new ResponseEntity<>(paymentResponse, HttpStatus.OK));

        ResponseEntity<?> response = quizService.getQuizQuestions(1, true, 5000L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCalculateResult_Success() {
        List<Response> responses = new ArrayList<>();
        when(questionClient.getScore(responses)).thenReturn(new ResponseEntity<>(3, HttpStatus.OK));

        ResponseEntity<Integer> result = quizService.calculateResult(responses);

        assertEquals(3, result.getBody());
    }
}
