package gradebook;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubjectTest {

    public static final Rate VALID_RATE = new Rate(1);
    public static final String SUBJECT_NAME = "Math";

    public static final LocalDate VALID_RATE_DATE = LocalDate.of(2022, 2, 2);

    public static final String VALID_DESCRIPTION = "descripion";

    @Test
    public void createValidSubject() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addGrade(grade1);
        subject.addGrade(grade2);
        assertEquals(List.of(grade1, grade2), subject.getGrades());
    }

    @Test
    public void calculateAverageGradeRates() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addGrade(grade1);
        subject.addGrade(grade2);
        OptionalDouble expectedAvarage = Stream.of(grade1, grade2).mapToDouble(a -> a.rate().value()).average();
        assertEquals(expectedAvarage, subject.getAverageGradeRates());
    }

    @Test
    public void calculateMinGradeRate() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        subject.addAll(grades());
        Double expected = grades().stream().mapToDouble(a -> a.rate().value()).min().getAsDouble();
        assertEquals(expected, subject.getMinGradeRate());
    }

    @Test
    public void calculateMaxGradeRate() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        subject.addAll(grades());
        Double expected = grades().stream().mapToDouble(a -> a.rate().value()).max().getAsDouble();
        assertEquals(expected, subject.getMaxGradeRate());
    }

    @Test
    public void getLastAddedGrade() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(new Rate(2), VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade3 = new Grade(new Rate(3), VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addGrade(grade1);
        subject.addGrade(grade2);
        subject.addGrade(grade3);
        assertEquals(grade3, subject.getLast());
    }


    @Test
    public void getGradesFromProvidedMonth() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        subject.addAll(grades());
        Month month = Month.FEBRUARY;
        List<Grade> expected = grades().stream().filter(p -> p.rateDate().getMonth().equals(month)).toList();
        assertEquals(expected, subject.getGradesByMonth(month));
    }

    @Test
    public void getGradesFromProvidedDay() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        subject.addAll(grades());
        DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;
        List<Grade> expected = grades().stream().filter(p -> p.rateDate().getDayOfWeek().equals(dayOfWeek)).toList();
        assertEquals(expected, subject.getGradesByDay(dayOfWeek));
    }

    @Test
    public void getGradesFromProvidedWeek() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        subject.addAll(grades());
        int weekNumber = 5;
        TemporalField weekFields = WeekFields.of(Locale.getDefault()).weekOfYear();
        List<Grade> expected = grades().stream().filter(p -> p.rateDate().get(weekFields) == weekNumber).toList();
        assertEquals(expected, subject.getGradesByWeek(weekNumber));
    }

    @Test
    public void getGradesWithoutDescription() {

    }

    @Test
    public void countGradesByRate() {
        Subject subject = new Subject(SUBJECT_NAME, null);
        subject.addAll(grades());
        Rate lookingRate = new Rate(1);
        long expected = subject.getGrades().stream().filter(f -> f.rate().equals(lookingRate)).count();
        assertEquals(expected, subject.countGradesByRate(lookingRate));
    }

    private static List<Grade> grades() {
        return List.of(
                new Grade(new Rate(2), VALID_RATE_DATE, VALID_DESCRIPTION),
                new Grade(new Rate(3), VALID_RATE_DATE, VALID_DESCRIPTION),
                new Grade(new Rate(4), LocalDate.of(2023, 3, 2), VALID_DESCRIPTION),
                new Grade(new Rate(6), VALID_RATE_DATE, VALID_DESCRIPTION),
                new Grade(new Rate(3), LocalDate.of(2021, 4, 5), VALID_DESCRIPTION),
                new Grade(new Rate(2), VALID_RATE_DATE, VALID_DESCRIPTION),
                new Grade(new Rate(1), LocalDate.of(2020, 4, 7), VALID_DESCRIPTION),
                new Grade(new Rate(4), LocalDate.of(2021, 6, 2), VALID_DESCRIPTION)
//                new Grade(new Rate(6), VALID_RATE_DATE, ""),
//                new Grade(new Rate(3), VALID_RATE_DATE, "  "),
//                new Grade(new Rate(3), VALID_RATE_DATE, null)
        );
    }
}