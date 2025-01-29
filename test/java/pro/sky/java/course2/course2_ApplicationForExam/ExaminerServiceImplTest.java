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
    @InjectMocks
    private ExaminerServiceImpl out;

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionsForTest1")
    public void shouldResultGetQuestions_whenNotCorrectAmount(Set<Question> questions, int minSizeSet, int maxSizeSet) {
        //given
        when(javaQuestionService.getAll()).thenReturn(questions);

        //then
        assertThrows(ValidataException.class, () -> out.getQuestions(minSizeSet));
        assertThrows(ValidataException.class, () -> out.getQuestions(maxSizeSet));
        verify(javaQuestionService, never()).getRandomQuestion();
    }

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionsForTest2")
    public void shouldResultGetQuestions_whenAmountIsEqualsSizeDataBase(Set<Question> questions, int amount) {
        //given
        when(javaQuestionService.getAll()).thenReturn(questions);

        //when
        Collection<Question> actual = out.getQuestions(amount);

        //then
        assertEquals(questions, actual);
        verify(javaQuestionService, never()).getRandomQuestion();
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
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"))), -2, 3),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"))), -11, 5),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"))), -1, 2),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"),
                        new Question("question 4", "answer 4"))), -4, 9)
        );
    }

    public static Stream<Arguments> provideCorrectQuestionsForTest2() {
        return Stream.of(
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"))), 2),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"))), 3),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"))), 1),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"),
                        new Question("question 4", "answer 4"))), 4)
        );
    }

    public static Stream<Arguments> provideCorrectQuestionsForTest3() {
        return Stream.of(
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"))), 1, 1, new Question ("question 2", "answer 2")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"))), 1, 1, new Question ("question 1", "answer 1")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"),
                        new Question("question 4", "answer 4"))), 1, 1, new Question ("question 3", "answer 3"))
        );
    }
}
