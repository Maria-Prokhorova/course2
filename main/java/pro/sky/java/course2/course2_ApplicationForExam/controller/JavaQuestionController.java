package pro.sky.java.course2.course2_ApplicationForExam.controller;

import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course2.course2_ApplicationForExam.service.QuestionService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/exam/java")
public class JavaQuestionController {
    private final QuestionService service;

    public JavaQuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping(path = "/add")
    public Question addQuestion(@RequestParam("question") String question, @RequestParam("answer") String answer) {
        return service.add(question, answer);
    }

    @GetMapping(path = "/remove")
    public Question removeQuestions(@RequestParam("question") String question, @RequestParam("answer") String answer) {
        Question newQuestion = new Question(question, answer);
        return service.remove(newQuestion);

    }

    @GetMapping
    public Collection<Question> getQuestions() {
        return service.getAll();
    }
}
