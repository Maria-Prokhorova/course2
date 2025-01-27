package pro.sky.java.course2.course2_ApplicationForExam.service;

import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    //Random random;

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        Set<Question> newQuestionsSet = new HashSet<>();
        if (amount < 0 || amount > questionService.getAll().size()) {
            throw new ValidataException("Задано неверное количество вопросов");
        } else if (amount == questionService.getAll().size()) {
            return questionService.getAll();
        } else {
            while (newQuestionsSet.size() < amount) {
                newQuestionsSet.add(questionService.getRandomQuestion());
            }
            return newQuestionsSet;
        }
    }
}
