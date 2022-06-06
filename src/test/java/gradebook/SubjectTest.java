package gradebook;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubjectTest {

    public static final Rate VALID_RATE = new Rate(3);
    public static final String SUBJECT_NAME = "Math";

    public static final LocalDate VALID_RATE_DATE = LocalDate.of(2022, 2, 2);

    public static final String VALID_DESCRIPTION = "descripion";

    @Test
    public void createValidSubject() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2));
        assertEquals(List.of(grade1, grade2), subject.grades());
    }

    @Test
    public void createValidSubjectWithNull() {

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Subject(null)
        );
        assertEquals("Name can't be null", exception.getMessage());
    }

    @Test
    public void calculateAverageGradeRates() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2));
        OptionalDouble expectedAvarage = Stream
                .of(grade1, grade2)
                .mapToDouble(m -> m.rate().value())
                .average();
        assertEquals(expectedAvarage, subject.averageGradeRates());
    }

    @Test
    public void calculateAverageGradeRatesForOneElementList() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1));
        OptionalDouble expectedAvarage = Stream
                .of(grade1)
                .mapToDouble(m -> m.rate().value())
                .average();
        assertEquals(expectedAvarage, subject.averageGradeRates());
    }

    @Test
    public void shouldAllovAddGrades() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addGrade(grade1);
        List<Grade> expected = List.of(grade1);
        assertEquals(expected, subject.grades());
    }

    @Test
    public void shouldAllovAddGradesWithNull() {
        Subject subject = new Subject(VALID_DESCRIPTION);
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> subject.addGrade(null)
        );
        assertEquals("Grade can't be null", exception.getMessage());
    }

    @Test
    public void calculateMinGradeRate() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(new Rate(1), VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade3 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade4 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2, grade3, grade4));
        OptionalDouble expected = Stream.of(grade1, grade2, grade3, grade4)
                .mapToDouble(m -> m.rate().value())
                .min();
        assertEquals(expected, subject.minGradeRate());
    }

    @Test
    public void calculateMinGradeRateForOneElementList() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(new Rate(1), VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1));
        OptionalDouble expected = Stream.of(grade1)
                .mapToDouble(m -> m.rate().value())
                .min();
        assertEquals(expected, subject.minGradeRate());
    }

    @Test
    public void calculateMaxGradeRate() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(new Rate(6), VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade3 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade4 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2, grade3, grade4));
        OptionalDouble expected = Stream.
                of(grade1, grade2, grade3, grade4)
                .mapToDouble(m -> m.rate().value())
                .max();
        assertEquals(expected, subject.maxGradeRate());
    }

    @Test
    public void getLastAddedGrade() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(new Rate(2), VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade3 = new Grade(new Rate(4), VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2, grade3));
        assertEquals(grade3, subject.lastGrade());
    }

    @Test
    public void getLastAddedGradeWithNull() {
        Subject subject = new Subject(SUBJECT_NAME);
        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> subject.lastGrade()
        );
        assertEquals("Grades can't be empty.", exception.getMessage());
    }

    @Test
    public void getGradesFromProvidedMonth() {
        Month month = Month.FEBRUARY;
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, LocalDate.of(2022, 1, 4), VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, LocalDate.of(2022, 2, 4), VALID_DESCRIPTION);
        Grade grade3 = new Grade(VALID_RATE, LocalDate.of(2022, 2, 4), VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2, grade3));
        List<Grade> expected = Stream
                .of(grade1, grade2, grade3)
                .filter(f -> Objects.equals(f.rateDate().getMonth(), month))
                .toList();
        assertEquals(expected, subject.gradesByMonth(month));
    }

    @Test
    public void getGradesFromProvidedDay() {
        DayOfWeek day = DayOfWeek.TUESDAY;
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, LocalDate.of(2022, 1, 4), VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, LocalDate.of(2022, 2, 4), VALID_DESCRIPTION);
        Grade grade3 = new Grade(VALID_RATE, LocalDate.of(2022, 2, 4), VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2, grade3));

        List<Grade> expected = Stream
                .of(grade1, grade2, grade3)
                .filter(f -> Objects.equals(f.rateDate().getDayOfWeek(), day))
                .toList();
        assertEquals(expected, subject.gradesByDay(day));
    }

    @Test
    public void getGradesFromProvidedWeek() {
        int weekNumber = 1;
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, LocalDate.of(2022, 1, 4), VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, LocalDate.of(2022, 1, 4), VALID_DESCRIPTION);
        Grade grade3 = new Grade(VALID_RATE, LocalDate.of(2022, 1, 4), VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2, grade3));
        TemporalField weekFields = WeekFields.of(Locale.getDefault()).weekOfYear();
        List<Grade> expected = Stream.of(grade1, grade2, grade3)
                .filter(p -> Objects.equals(p.rateDate().get(weekFields), weekNumber))
                .toList();
        assertEquals(expected, subject.gradesByWeek(weekNumber));
    }

    @Test
    public void getGradesWithoutDescription() {
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, null);
        Grade grade3 = new Grade(VALID_RATE, VALID_RATE_DATE, null);
        subject.addAll(List.of(grade1, grade2, grade3));
        List<Grade> expected = Stream.of(grade1, grade2, grade3)
                .filter(f -> Objects.equals(f.description(), null))
                .toList();
        assertEquals(expected, subject.getGradesWithoutDescription());
    }

    @Test
    public void countGradesByRate() {
        Rate lookingRate = new Rate(1);
        Subject subject = new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(new Rate(2), VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade3 = new Grade(new Rate(1), VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addAll(List.of(grade1, grade2, grade3));
        long expected = Stream.of(grade1, grade2, grade3)
                .filter(f -> f.rate().equals(lookingRate))
                .count();
        assertEquals(expected, subject.countGradesByRate(lookingRate));
    }

}