package pro.sky.java.course2.course2_ApplicationForExam.repository;

import org.springframework.stereotype.Repository;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class JavaQuestionRepositoryImpl implements QuestionRepository {

    private final Set<Question> questionsJava = new HashSet<>(Set.of(
           // new Question("java question 1", "java answer 1"),
           // new Question("java question 2", "java answer 2"),
           // new Question("java question 3", "java answer 3"),
           // new Question("java question 4", "java answer 4"),
           // new Question("java question 5", "java answer 5")
    ));

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        questionsJava.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question add(Question question) {
        questionsJava.add(question);
        return question;
    }

    public Question remove(Question question) {
        if (questionsJava.contains(question)) {
            questionsJava.remove(question);
            return question;
        }
        throw new ValidataException("Такой вопрос отсутствует в базе");
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet(questionsJava);
    }
}
