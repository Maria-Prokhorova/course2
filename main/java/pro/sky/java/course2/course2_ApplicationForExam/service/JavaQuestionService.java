package pro.sky.java.course2.course2_ApplicationForExam.service;

import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import org.springframework.stereotype.Service;
import pro.sky.java.course2.course2_ApplicationForExam.repository.JavaQuestionRepositoryImpl;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    public final JavaQuestionRepositoryImpl javaQuestionRepository;

    public JavaQuestionService(JavaQuestionRepositoryImpl javaQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
    }

    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        javaQuestionRepository.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question add(Question question) {
        javaQuestionRepository.add(question);
        return question;
    }

    public Question remove(Question question) {
        if (javaQuestionRepository.getAll().contains(question)) {
            javaQuestionRepository.remove(question);
            return question;
        }
        throw new ValidataException("Такой вопрос отсутствует в базе");
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet((Set<? extends Question>) javaQuestionRepository.getAll());
    }

    @Override
    public Question getRandomQuestion() {
        int randomNumber = random.nextInt(javaQuestionRepository.getAll().size());
        List<Question> questionssList = new ArrayList<>(javaQuestionRepository.getAll());
        return questionssList.get(randomNumber);
    }
}
