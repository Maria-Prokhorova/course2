package pro.sky.java.course2.course2_ApplicationForExam.repository;

import org.springframework.stereotype.Repository;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class MathQuestionRepositoryImpl implements QuestionRepository {

    private final Set<Question> questionsMath = new HashSet<>(Set.of(
           // new Question("math question 1", "math answer 1"),
           // new Question("math question 2", "math answer 2"),
           // new Question("math question 3", "math answer 3"),
           // new Question("math question 4", "math answer 4"),
           // new Question("math question 5", "math answer 5")
    ));

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        questionsMath.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question add(Question question) {
        questionsMath.add(question);
        return question;
    }

    public Question remove(Question question) {
        if (questionsMath.contains(question)) {
            questionsMath.remove(question);
            return question;
        }
        throw new ValidataException("Такой вопрос отсутствует в базе");
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet(questionsMath);
    }
}
