package com.lpu.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lpu.question_service.dao.QuestionDao;
import com.lpu.question_service.exception.CustomException;
import com.lpu.question_service.model.Question;
import com.lpu.question_service.model.QuestionWrapper;
import com.lpu.question_service.model.Response;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionDao.findAll();
        if (questions.isEmpty()) {
            throw new CustomException("No questions found", HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to retrieve questions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        List<Question> questions = questionDao.findByCategory(category);
        if (questions.isEmpty()) {
            throw new CustomException("No questions found for category: " + category, HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to retrieve questions by category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        if (question == null) {
            throw new CustomException("Question cannot be null", HttpStatus.BAD_REQUEST);
        }

        try {
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to add question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        if (questions.isEmpty()) {
            throw new CustomException("Not enough questions available for category: " + categoryName, HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to retrieve questions for quiz", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        if (questionIds == null || questionIds.isEmpty()) {
            throw new CustomException("Question ID list cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            List<QuestionWrapper> wrappers = new ArrayList<>();
            List<Question> questions = new ArrayList<>();

            for (Integer id : questionIds) {
                questions.add(questionDao.findById(id).orElseThrow(() -> new CustomException("Question not found for ID: " + id, HttpStatus.NOT_FOUND)));
            }

            for (Question question : questions) {
                QuestionWrapper wrapper = new QuestionWrapper();
                wrapper.setId(question.getId());
                wrapper.setQuestionTitle(question.getQuestionTitle());
                wrapper.setOption1(question.getOption1());
                wrapper.setOption2(question.getOption2());
                wrapper.setOption3(question.getOption3());
                wrapper.setOption4(question.getOption4());
                wrappers.add(wrapper);
            }

            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to retrieve questions from IDs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        if (responses == null || responses.isEmpty()) {
            throw new CustomException("Response list cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            int right = 0;

            for (Response response : responses) {
                Question question = questionDao.findById(response.getId()).orElseThrow(() -> new CustomException("Question not found for ID: " + response.getId(), HttpStatus.NOT_FOUND));
                if (response.getResponse().equals(question.getRightAnswer())) {
                    right++;
                }
            }

            return new ResponseEntity<>(right, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to calculate score", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
