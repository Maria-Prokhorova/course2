package pro.sky.java.course2.course2_ApplicationForExam.service;

import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class JavaQuestionService implements QuestionService {

    Set<Question> questions = new HashSet<>();

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question add(Question question) {
        questions.add(question);
        return question;
    }

    public Question remove(Question question) {
        if (questions.contains(question)) {
            questions.remove(question);
            return question;
        }
        throw new ValidataException("Такой вопрос отсутствует в базе");
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, questions.size());
        List<Question> questionssList = new ArrayList<>(questions);
        return questionssList.get(randomNumber);
    }
}
