package pro.sky.java.course2.course2_ApplicationForExam.repository;

import pro.sky.java.course2.course2_ApplicationForExam.model.Question;

import java.util.Collection;

public interface QuestionRepository {

    Question add(String question, String answer);

    Question add(Question question);

    Question remove(Question question);

    Collection<Question> getAll();
}
