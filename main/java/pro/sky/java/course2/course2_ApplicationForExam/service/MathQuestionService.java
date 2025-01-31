package pro.sky.java.course2.course2_ApplicationForExam.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import pro.sky.java.course2.course2_ApplicationForExam.repository.MathQuestionRepositoryImpl;

import java.util.*;

@Service
public class MathQuestionService implements QuestionService {

    public final MathQuestionRepositoryImpl mathQuestionRepository;

    public MathQuestionService(MathQuestionRepositoryImpl mathQuestionRepository) {
        this.mathQuestionRepository = mathQuestionRepository;
    }

    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        mathQuestionRepository.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question add(Question question) {
        mathQuestionRepository.add(question);
        return question;
    }

    public Question remove(Question question) {
        if (mathQuestionRepository.getAll().contains(question)) {
            mathQuestionRepository.remove(question);
            return question;
        }
        throw new ValidataException("Такой вопрос отсутствует в базе");
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet((Set<? extends Question>) mathQuestionRepository.getAll());
    }

    @Override
    public Question getRandomQuestion() {
        int randomNumber = random.nextInt(mathQuestionRepository.getAll().size());
        List<Question> questionssList = new ArrayList<>(mathQuestionRepository.getAll());
        return questionssList.get(randomNumber);
    }
}
