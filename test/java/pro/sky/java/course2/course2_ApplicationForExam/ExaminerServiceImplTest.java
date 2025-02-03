package pro.sky.java.course2.course2_ApplicationForExam;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import pro.sky.java.course2.course2_ApplicationForExam.service.ExaminerServiceImpl;
import pro.sky.java.course2.course2_ApplicationForExam.service.JavaQuestionService;
import pro.sky.java.course2.course2_ApplicationForExam.service.MathQuestionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;
    @Mock
    private MathQuestionService mathQuestionService;
    @InjectMocks
    private ExaminerServiceImpl out;

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionsForTest1")
    public void shouldResultGetQuestions_whenNotCorrectAmount(Set<Question> javaQuestions, Set<Question> mathQuestions, int amount) {
        //given
        when(javaQuestionService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionService.getAll()).thenReturn(mathQuestions);

        //then
        assertThrows(ValidataException.class, () -> out.getQuestions(amount));
        verify(javaQuestionService, never()).getRandomQuestion();
        verify(mathQuestionService, never()).getRandomQuestion();
    }

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionsForTest2")
    public void shouldResultGetQuestions_whenAmountIsEqualsSizeDataBase(Set<Question> javaQuestions, Set<Question> mathQuestions, int amount) {
        //given
        when(javaQuestionService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionService.getAll()).thenReturn(mathQuestions);
        Collection<Question> expected = new HashSet<>();
        expected.addAll(javaQuestions);
        expected.addAll(mathQuestions);

        //when
        Collection<Question> actual = out.getQuestions(amount);

        //then
        assertEquals(expected, actual);
        verify(javaQuestionService, never()).getRandomQuestion();
        verify(mathQuestionService, never()).getRandomQuestion();
    }

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionsForTest3")
    public void shouldResultGetQuestions_whenCorrectAmount(Set<Question> questions, int amount, int time, Question question) {
        //given
        when(javaQuestionService.getAll()).thenReturn(questions);
        when(javaQuestionService.getRandomQuestion()).thenReturn(question);

        //when
        Collection<Question> actual = out.getQuestions(amount);

        //then
        assertTrue(actual.contains(question));
        verify(javaQuestionService, times(time)).getRandomQuestion();
    }

    public static Stream<Arguments> provideCorrectQuestionsForTest1() {
        return Stream.of(
                Arguments.of(
                        new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"),
                                new Question("java question 2", "java answer 2"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"),
                                new Question("math question 2", "math answer 2"))), 7),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"),
                                new Question("java question 2", "java answer 2"),
                                new Question("java question 3", "java answer 3"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"),
                                new Question("math question 2", "math answer 2"))), 10),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"))), 3),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"),
                                new Question("java question 2", "java answer 2"),
                                new Question("java question 3", "java answer 3"),
                                new Question("java question 4", "java answer 4"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"),
                                new Question("math question 2", "math answer 2"),
                                new Question("math question 3", "answer 3"),
                                new Question("math question 4", "math answer 4"))), 15));
    }

    public static Stream<Arguments> provideCorrectQuestionsForTest2() {
        return Stream.of(
                Arguments.of(
                        new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"),
                                new Question("java question 2", "java answer 2"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"),
                                new Question("math question 2", "math answer 2"))), 4),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"),
                                new Question("java question 2", "java answer 2"),
                                new Question("java question 3", "java answer 3"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"),
                                new Question("math question 2", "math answer 2"))), 5),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"))), 2),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("java question 1", "java answer 1"),
                                new Question("java question 2", "java answer 2"),
                                new Question("java question 3", "java answer 3"),
                                new Question("java question 4", "java answer 4"))),
                        new HashSet<>(Set.of(
                                new Question("math question 1", "math answer 1"),
                                new Question("math question 2", "math answer 2"),
                                new Question("math question 3", "math answer 3"),
                                new Question("math question 4", "math answer 4"))), 8));
    }

    public static Stream<Arguments> provideCorrectQuestionsForTest3() {
        return Stream.of(
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"))), 1, 1, new Question("question 2", "answer 2")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"))), 1, 1, new Question("question 1", "answer 1")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"),
                        new Question("question 4", "answer 4"))), 1, 1, new Question("question 3", "answer 3"))
        );
    }
}
