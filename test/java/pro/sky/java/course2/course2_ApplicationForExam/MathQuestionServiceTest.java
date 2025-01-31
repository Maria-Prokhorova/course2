package pro.sky.java.course2.course2_ApplicationForExam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import pro.sky.java.course2.course2_ApplicationForExam.repository.MathQuestionRepositoryImpl;
import pro.sky.java.course2.course2_ApplicationForExam.service.MathQuestionService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MathQuestionServiceTest {
    @Mock
    private MathQuestionRepositoryImpl mathQuestionRepository;
    @InjectMocks
    private MathQuestionService out;

    @ParameterizedTest
    @MethodSource("provideCorrectDataForTestAdd")
    public void shouldResultAddQuestion_whenArgumentString(Question expectedQuestion, String actualQuestion, String actualAnswer) {
        when(mathQuestionRepository.add(new Question(actualQuestion, actualAnswer))).thenReturn(expectedQuestion);
        assertEquals(expectedQuestion, out.add(actualQuestion, actualAnswer));
    }

    @ParameterizedTest
    @MethodSource("provideCorrectDataForTestAdd")
    public void shouldResultAddQuestion_whenArgumentObject(Question expectedQuestion, String actualQuestion, String actualAnswer) {
        when(mathQuestionRepository.add(new Question(actualQuestion, actualAnswer))).thenReturn(expectedQuestion);
        assertEquals(expectedQuestion, out.add(new Question(actualQuestion, actualAnswer)));
    }

    @ParameterizedTest
    @MethodSource("provideCorrectDataForTestRemove")
    public void shouldResultRemoveQuestion(Set<Question> expectedSet, Question expectedQuestion) {
        when(mathQuestionRepository.getAll()).thenReturn(expectedSet);
        when(mathQuestionRepository.remove(expectedQuestion)).thenReturn(expectedQuestion);
        Question actual = out.remove(expectedQuestion);
        assertEquals(expectedQuestion, actual);
    }

    @ParameterizedTest
    @MethodSource("provideNotCorrectDataForTestRemove")
    public void shouldResultRemoveQuestionIsNot(Set<Question> expectedSet, Question expectedQuestion) {
        when(mathQuestionRepository.getAll()).thenReturn(expectedSet);
        assertThrows(ValidataException.class, () -> out.remove(expectedQuestion));
    }

    @Test
    public void shouldResultGetAllQuestions_whenDatabaseNull() {
        when(mathQuestionRepository.getAll()).thenReturn(new HashSet<>());
        Set<Question> actual = (Set<Question>) out.getAll();
        assertNotNull(actual);
    }

    @ParameterizedTest
    @MethodSource("provideCorrectDataForTestsGetAllQuestionsAndGetRandomQuestion")
    public void shouldResultGetRandomQuestion_whenDatabaseNotNull(Set<Question> expectedSet) {
        when(mathQuestionRepository.getAll()).thenReturn(expectedSet);
        Set<Question> actual = new HashSet<>(out.getAll());
        assertEquals(expectedSet, actual);
    }

    @ParameterizedTest
    @MethodSource("provideCorrectDataForTestsGetAllQuestionsAndGetRandomQuestion")
    public void shouldResultGetRandomQuestion(Set<Question> expectedSet) {
        when(mathQuestionRepository.getAll()).thenReturn(expectedSet);
        Question actual = out.getRandomQuestion();
        assertTrue(expectedSet.contains(actual));
    }

    public static Stream<Arguments> provideCorrectDataForTestAdd() {
        return Stream.of(
                Arguments.of(new Question("question 1", "answer 1"), "question 1", "answer 1"),
                Arguments.of(new Question("question 2", "answer 2"), "question 2", "answer 2"),
                Arguments.of(new Question("question 3", "answer 3"), "question 3", "answer 3"),
                Arguments.of(new Question("question 4", "answer 4"), "question 4", "answer 4"),
                Arguments.of(new Question("question 5", "answer 5"), "question 5", "answer 5"));
    }

    public static Stream<Arguments> provideCorrectDataForTestRemove() {
        return Stream.of(
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"))), new Question("question 1", "answer 1")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 4", "answer 4"),
                        new Question("question 5", "answer 5"))), new Question("question 5", "answer 5")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 6", "answer 6"),
                        new Question("question 7", "answer 7"),
                        new Question("question 8", "answer 8"),
                        new Question("question 9", "answer 9"))), new Question("question 7", "answer 7")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 10", "answer 10"),
                        new Question("question 11", "answer 11"),
                        new Question("question 12", "answer 12"),
                        new Question("question 13", "answer 13"),
                        new Question("question 14", "answer 14"))), new Question("question 10", "answer 10")));
    }

    public static Stream<Arguments> provideNotCorrectDataForTestRemove() {
        return Stream.of(
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3"))), new Question("question 5", "answer 5")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 4", "answer 4"),
                        new Question("question 5", "answer 5"))), new Question("question 1", "answer 1")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 6", "answer 6"),
                        new Question("question 7", "answer 7"),
                        new Question("question 8", "answer 8"),
                        new Question("question 9", "answer 9"))), new Question("question 3", "answer 3")),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 10", "answer 10"),
                        new Question("question 11", "answer 11"),
                        new Question("question 12", "answer 12"),
                        new Question("question 13", "answer 13"),
                        new Question("question 14", "answer 14"))), new Question("question 1", "answer 1")));
    }

    public static Stream<Arguments> provideCorrectDataForTestsGetAllQuestionsAndGetRandomQuestion() {
        return Stream.of(
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 1", "answer 1"),
                        new Question("question 2", "answer 2"),
                        new Question("question 3", "answer 3")))),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 4", "answer 4"),
                        new Question("question 5", "answer 5")))),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 6", "answer 6"),
                        new Question("question 7", "answer 7"),
                        new Question("question 8", "answer 8"),
                        new Question("question 9", "answer 9")))),
                Arguments.of(new HashSet<>(Set.of(
                        new Question("question 10", "answer 10"),
                        new Question("question 11", "answer 11"),
                        new Question("question 12", "answer 12"),
                        new Question("question 13", "answer 13"),
                        new Question("question 14", "answer 14")))));
    }
}
