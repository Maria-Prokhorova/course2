package pro.sky.java.course2.course2_ApplicationForExam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.java.course2.course2_ApplicationForExam.exception.ValidataException;
import pro.sky.java.course2.course2_ApplicationForExam.model.Question;
import pro.sky.java.course2.course2_ApplicationForExam.service.JavaQuestionService;
import pro.sky.java.course2.course2_ApplicationForExam.service.QuestionService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JavaQuestionServiceTest {

    private final QuestionService out = new JavaQuestionService();

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionForTests")
    public void shouldResultAddQuestion_whenArgumentString(Question expectedQuestion, String actualQuestion, String actualAnswer) {
        Question actual = out.add(actualQuestion, actualAnswer);
        assertEquals(expectedQuestion, actual);
    }

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionForTests")
    public void shouldResultAddQuestion_whenArgumentObject(Question expectedQuestion, String actualQuestion, String actualAnswer) {
        Question actual = out.add(new Question(actualQuestion, actualAnswer));
        assertEquals(expectedQuestion, actual);
    }

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionForTests")
    public void shouldResultRemoveQuestion(Question expectedQuestion, String actualQuestion, String actualAnswer) {
        out.add(actualQuestion, actualAnswer);
        Question actual = out.remove(new Question(actualQuestion, actualAnswer));
        assertEquals(expectedQuestion, actual);

        assertThrows(ValidataException.class, () -> out.remove(new Question(actualQuestion, actualAnswer)));
    }

    @Test
    public void shouldResultGetAllQuestions_whenDatabaseNull() {
        Set<Question> actual = (Set<Question>) out.getAll();
        assertNotNull(actual);
    }

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionSetForTests")
    public void shouldResultGetAllQuestions_whenDatabaseNotNull(Set<Question> expectedSet, ArrayList<Question> arrayQuestions) {
        for (Question arrayQuestion : arrayQuestions) {
            out.add(arrayQuestion);
        }
        Set<Question> actual = (Set<Question>) out.getAll();

        assertTrue(expectedSet.containsAll(actual) && actual.containsAll(expectedSet));
    }

    @ParameterizedTest
    @MethodSource("provideCorrectQuestionSetForTests")
    public void shouldResultGetRandomQuestion(Set<Question> expectedSet, ArrayList<Question> arrayQuestions) {
        for (Question arrayQuestion : arrayQuestions) {
            out.add(arrayQuestion);
        }
        Question actual = out.getRandomQuestion();
        assertTrue(expectedSet.contains(actual));
    }

    public static Stream<Arguments> provideCorrectQuestionForTests() {
        return Stream.of(
                Arguments.of(new Question("question 1", "answer 1"), "question 1", "answer 1"),
                Arguments.of(new Question("question 2", "answer 2"), "question 2", "answer 2"),
                Arguments.of(new Question("question 3", "answer 3"), "question 3", "answer 3"),
                Arguments.of(new Question("question 4", "answer 4"), "question 4", "answer 4"),
                Arguments.of(new Question("question 5", "answer 5"), "question 5", "answer 5")
        );

    }

    public static Stream<Arguments> provideCorrectQuestionSetForTests() {
        return Stream.of(
                Arguments.of(new HashSet<>(Set.of(
                                new Question("question 1", "answer 1"),
                                new Question("question 2", "answer 2"),
                                new Question("question 3", "answer 3"))),
                        new ArrayList<>(List.of(
                                new Question("question 1", "answer 1"),
                                new Question("question 2", "answer 2"),
                                new Question("question 3", "answer 3")))
                ),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("question 4", "answer 4"),
                                new Question("question 5", "answer 5"))),
                        new ArrayList<>(List.of(
                                new Question("question 4", "answer 4"),
                                new Question("question 5", "answer 5")))
                ), Arguments.of(new HashSet<>(Set.of(
                                new Question("question 6", "answer 6"),
                                new Question("question 7", "answer 7"),
                                new Question("question 8", "answer 8"),
                                new Question("question 9", "answer 9"))),
                        new ArrayList<>(List.of(
                                new Question("question 6", "answer 6"),
                                new Question("question 7", "answer 7"),
                                new Question("question 8", "answer 8"),
                                new Question("question 9", "answer 9")))
                ),
                Arguments.of(new HashSet<>(Set.of(
                                new Question("question 10", "answer 10"),
                                new Question("question 11", "answer 11"),
                                new Question("question 12", "answer 12"),
                                new Question("question 13", "answer 13"),
                                new Question("question 14", "answer 14"))),
                        new ArrayList<>(List.of(
                                new Question("question 10", "answer 10"),
                                new Question("question 11", "answer 11"),
                                new Question("question 12", "answer 12"),
                                new Question("question 13", "answer 13"),
                                new Question("question 14", "answer 14")))
                )
        );
    }
}
