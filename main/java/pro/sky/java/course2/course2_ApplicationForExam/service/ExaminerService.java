package pro.sky.java.course2.course2_ApplicationForExam.service;

import pro.sky.java.course2.course2_ApplicationForExam.model.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}
