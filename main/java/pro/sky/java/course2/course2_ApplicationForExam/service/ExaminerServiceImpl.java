package pro.sky.java.course2.course2_ApplicationForExam.service;

import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final Random random = new Random();

    private final JavaQuestionService javaQuestionService;
    private final MathQuestionService mathQuestionService;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionService, MathQuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        int randomIndex = random.nextInt(amount);
        Set<Question> randomQuestionsSet = new HashSet<>();

        if (amount > (javaQuestionService.getAll().size() + mathQuestionService.getAll().size())) {
            throw new ValidataException("Задано неверное количество вопросов");
        } else if (amount == (javaQuestionService.getAll().size() + mathQuestionService.getAll().size())) {
            randomQuestionsSet.addAll(javaQuestionService.getAll());
            randomQuestionsSet.addAll(mathQuestionService.getAll());
            return randomQuestionsSet;
        } else {
            while (randomQuestionsSet.size() < randomIndex) {
                randomQuestionsSet.add(javaQuestionService.getRandomQuestion());
            }
            while (randomQuestionsSet.size() < amount) {
                randomQuestionsSet.add(mathQuestionService.getRandomQuestion());
            }
            return randomQuestionsSet;
        }
    }
}
