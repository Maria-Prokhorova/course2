package pro.sky.java.course2.course2_ApplicationForExam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import pro.sky.java.course2.course2_ApplicationForExam.service.ExaminerServiceImpl;
import pro.sky.java.course2.course2_ApplicationForExam.service.JavaQuestionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;
    @InjectMocks
    private ExaminerServiceImpl out;

    public static Collection<Question> QUESTIONS = new HashSet<>(Set.of(
            new Question("Java question 1", "Java answer 1"),
            new Question("Java question 2", "Java answer 2"),
            new Question("Java question 3", "Java answer 3"),
            new Question("Java question 4", "Java answer 4"),
            new Question("Java question 5", "Java answer 5")
    ));

    @BeforeEach
    public void setUp() {
        when(javaQuestionService.getAll()).thenReturn(QUESTIONS);
    }

    @Test
    public void shouldResultGetQuestions_whenNotCorrectAmount() {
        assertThrows(ValidataException.class, () -> out.getQuestions(-2));
        assertThrows(ValidataException.class, () -> out.getQuestions(8));

        verify(javaQuestionService, never()).getRandomQuestion();
    }

    @Test
    public void shouldResultGetQuestions_whenAmountIsEqualsSizeDataBase() {
        Collection<Question> actual = out.getQuestions(5);
        assertEquals(QUESTIONS, actual);

        verify(javaQuestionService, never()).getRandomQuestion();
    }

    @Test
    public void shouldResultGetQuestions_whenCorrectAmount() {
        when(javaQuestionService.getRandomQuestion()).thenReturn(new Question("question 1", "answer 1"),
                new Question("question 2", "answer 2"));

        Collection<Question> actual = out.getQuestions(2);
        Collection<Question> expected = new HashSet<>(Set.of(new Question("question 1", "answer 1"),
                new Question("question 2", "answer 2")));
        assertEquals(expected, actual);

        verify(javaQuestionService, times(2)).getRandomQuestion();
    }
}
