package pro.sky.java.course2.course2_ApplicationForExam;

import org.junit.jupiter.api.Test;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import pro.sky.java.course2.course2_ApplicationForExam.service.JavaQuestionService;
import pro.sky.java.course2.course2_ApplicationForExam.service.QuestionService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JavaQuestionServiceTest {

    private final QuestionService out = new JavaQuestionService();

    public static Question expectedQuestion = new Question("question 1", "answer 1");

    public static Set<Question> expectedSet = new HashSet<>(Set.of(
            new Question("question 1", "answer 1"),
            new Question("question 2", "answer 2"),
            new Question("question 3", "answer 3")
    ));

    @Test
    public void shouldResultAddQuestion_whenArgumentString() {
        Question actual = out.add("question 1", "answer 1");
        assertEquals(expectedQuestion, actual);
    }

    @Test
    public void shouldResultAddQuestion_whenArgumentObject() {
        Question actual = out.add(new Question("question 1", "answer 1"));
        assertEquals(expectedQuestion, actual);
    }

    @Test
    public void shouldResultRemoveQuestion() {
        out.add("question 1", "answer 1");
        Question actual = out.remove(new Question("question 1", "answer 1"));
        assertEquals(expectedQuestion, actual);

        // проверяем удаление вопроса, которого нет в базе (метод должен выбросить исключение)
        assertThrows(ValidataException.class, () -> out.remove(new Question("question 1", "answer 1")));
    }

    @Test
    public void shouldResultGetAllQuestions_whenDatabaseNotNull() {
        out.add("question 1", "answer 1");
        out.add("question 2", "answer 2");
        out.add("question 3", "answer 3");
        Set<Question> actual = (Set<Question>) out.getAll();

        assertTrue(expectedSet.containsAll(actual) && actual.containsAll(expectedSet));
    }

    @Test
    public void shouldResultGetAllQuestions_whenDatabaseNull() {
        Set<Question> actual = (Set<Question>) out.getAll();
        assertNotNull(actual);
    }

    @Test
    public void shouldResultGetRandomQuestion() {
        out.add("question 1", "answer 1");
        out.add("question 2", "answer 2");
        out.add("question 3", "answer 3");
        Question actual = out.getRandomQuestion();
        assertTrue(expectedSet.contains(actual));
    }
}
