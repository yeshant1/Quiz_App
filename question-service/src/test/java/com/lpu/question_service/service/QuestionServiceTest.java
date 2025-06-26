package com.lpu.question_service.service;

import com.lpu.question_service.dao.QuestionDao;
import com.lpu.question_service.exception.CustomException;
import com.lpu.question_service.model.Question;
import com.lpu.question_service.model.QuestionWrapper;
import com.lpu.question_service.model.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestions_Success() {
        List<Question> questions = List.of(new Question(), new Question());
        when(questionDao.findAll()).thenReturn(questions);

        ResponseEntity<List<Question>> response = questionService.getAllQuestions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllQuestions_EmptyList() {
        when(questionDao.findAll()).thenReturn(Collections.emptyList());

        assertThrows(CustomException.class, () -> questionService.getAllQuestions());
    }

    @Test
    void testAddQuestion_Success() {
        Question question = new Question();
        when(questionDao.save(question)).thenReturn(question);

        ResponseEntity<String> response = questionService.addQuestion(question);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Question added successfully", response.getBody());
    }

    @Test
    void testAddQuestion_Null() {
        assertThrows(CustomException.class, () -> questionService.addQuestion(null));
    }

    @Test
    void testGetQuestionsByCategory_Success() {
        List<Question> questions = List.of(new Question());
        when(questionDao.findByCategory("java")).thenReturn(questions);

        ResponseEntity<List<Question>> response = questionService.getQuestionsByCategory("java");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetQuestionsForQuiz_Success() {
        List<Integer> ids = List.of(1, 2, 3);
        when(questionDao.findRandomQuestionsByCategory("java", 3)).thenReturn(ids);

        ResponseEntity<List<Integer>> response = questionService.getQuestionsForQuiz("java", 3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void testGetQuestionsFromId_Success() {
        Question q = new Question();
        q.setId(1);
        q.setQuestionTitle("Title");
        q.setOption1("A");
        q.setOption2("B");
        q.setOption3("C");
        q.setOption4("D");

        when(questionDao.findById(1)).thenReturn(Optional.of(q));

        ResponseEntity<List<QuestionWrapper>> response = questionService.getQuestionsFromId(List.of(1));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Title", response.getBody().get(0).getQuestionTitle());
    }

    @Test
    void testGetScore_Success() {
        Question q = new Question();
        q.setId(1);
        q.setRightAnswer("A");

        Response r = new Response();
        r.setId(1);
        r.setResponse("A");

        when(questionDao.findById(1)).thenReturn(Optional.of(q));

        ResponseEntity<Integer> response = questionService.getScore(List.of(r));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }
}
