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
import java.util.jar.JarOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    public static final Rate VALID_RATE = new Rate(3);
    public static final String SUBJECT_NAME = "Math";

    public static final LocalDate VALID_RATE_DATE = LocalDate.of(2022, 2, 2);

    public static final String VALID_DESCRIPTION = "descripion";

    private Grade createValidGrade() {
        return new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
    }

    private Grade createValidGradeByRate(Rate rate) {
        return new Grade(rate, VALID_RATE_DATE, VALID_DESCRIPTION);
    }

    private Grade createValidGradeByDayOfWeek(DayOfWeek dayOfWeek) {
        int dayOfMonth = 10;
        LocalDate localDate;
        do {
            localDate = LocalDate.of(2022, 5, dayOfMonth++);
        } while (localDate.getDayOfWeek() != dayOfWeek);
        return new Grade(VALID_RATE, localDate, VALID_DESCRIPTION);
    }

    private Grade createValidGradeByMonth(Month month) {
        int day = 10; //YearMonth.of(2022,month).lengthOfMonth();
        DayOfWeek dayOfWeek = LocalDate.of(2022, month, day).getDayOfWeek();
        int monthLenght = LocalDate.of(2022, month, day).lengthOfMonth();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            day = day + 2;
        }
        return new Grade(VALID_RATE, LocalDate.of(2022, month, day), VALID_DESCRIPTION);
    }

    private Grade createValidGradeByDescription(String description) {
        return new Grade(VALID_RATE, VALID_RATE_DATE, description);
    }

    private Grade createValidGradeByWeekOfYear(int weekOfYear) {
        int day = 1;
        int month = 1;
        TemporalField weekFields = WeekFields.of(Locale.getDefault()).weekOfYear();
        LocalDate localDate = LocalDate.of(2022, month, day);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            day += 2;
            localDate = LocalDate.of(2022, month, day);
        }
        while (localDate.get(weekFields) < weekOfYear) {
            day += 7;
            if (day > localDate.lengthOfMonth()) {
                month++;
                day = 1;
            }
            localDate = LocalDate.of(2022, month, day);
        }

        return new Grade(VALID_RATE, localDate, VALID_DESCRIPTION);
    }

    @Test
    public void createValidSubject() {
        //given
        Subject subject = new Subject(SUBJECT_NAME);
        //when
        String subjectName = subject.getName();
        //then
        assertNotEquals(null, subjectName);
    }

    @Test
    public void createValidSubjectWithNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Subject(null));
        assertEquals("Name can't be null", exception.getMessage());
    }

    @Test
    public void calculateAverageGradeRates() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByRate(new Rate(2.0)),
                createValidGradeByRate(new Rate(4.0)));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        OptionalDouble avarageGradeRates = subject.averageGradeRates();
        //then
        assertEquals(OptionalDouble.of(3), avarageGradeRates);
    }

    @Test
    public void calculateAverageGradesForEmptyGrades() {
        //given
        List<Grade> grades = List.of();
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        OptionalDouble avarageGradeRates = subject.averageGradeRates();
        //then
        assertEquals(OptionalDouble.empty(), avarageGradeRates);

    }

    @Test
    public void calculateAverageGradeRatesWithSameRate() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByRate(new Rate(3.5)),
                createValidGradeByRate(new Rate(3.5)));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        OptionalDouble averageGradeRates = subject.averageGradeRates();
        //then
        assertEquals(OptionalDouble.of(3.5), averageGradeRates);
    }

    @Test
    public void calculateAverageGradeRatesForOneElementList() {
        //given
        List<Grade> grades = List.of(createValidGradeByRate(new Rate(3)));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        OptionalDouble averageGradeRates = subject.averageGradeRates();
        //then
        assertEquals(OptionalDouble.of(3), averageGradeRates);
    }

    @Test
    public void shouldAllowAddGrades() {
        //given
        List<Grade> grades = List.of(
                createValidGrade(),
                createValidGrade(),
                createValidGrade());
        Subject subject = new Subject(SUBJECT_NAME);
        //when
        subject.addAll(grades);
        List<Grade> subjectGrades = subject.grades();
        //then
        assertEquals(grades, subjectGrades);
    }

    @Test
    public void shouldAllowAddOneGrade() {
        //given
        Grade grade = createValidGrade();
        Subject subject = new Subject(SUBJECT_NAME);
        //when
        subject.addGrade(grade);
        List<Grade> subjectGrades = subject.grades();
        //then
        assertEquals(List.of(grade), subjectGrades);
    }

    @Test
    public void shouldAllovAddGradesWithNull() {
        Subject subject = new Subject(VALID_DESCRIPTION);
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> subject.addGrade(null));
        assertEquals("Grade can't be null", exception.getMessage());
    }

    @Test
    public void calculateMinGradeRate() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByRate(new Rate(1)),
                createValidGrade(),
                createValidGrade(),
                createValidGrade());
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        OptionalDouble minGradeRate = subject.minGradeRate();
        //then
        assertEquals(OptionalDouble.of(1), minGradeRate);
    }

    @Test
    public void calculateMinGradeRateForOneElementList() {
        //given
        List<Grade> grades = List.of(createValidGradeByRate(new Rate(1)));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        OptionalDouble minGradeRate = subject.minGradeRate();
        //then
        assertEquals(OptionalDouble.of(1), minGradeRate);
    }

    @Test
    public void calculateMaxGradeRate() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByRate(new Rate(6)),
                createValidGrade(),
                createValidGrade(),
                createValidGrade());
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        OptionalDouble maxGradeRate = subject.maxGradeRate();
        //then
        assertEquals(OptionalDouble.of(6), maxGradeRate);
    }

    @Test
    public void getLastAddedGrade() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByRate(new Rate(6)),
                createValidGrade(),
                createValidGrade(),
                createValidGradeByRate(new Rate(4)));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        Grade grade = subject.lastGrade();
        //then
        assertEquals(createValidGradeByRate(new Rate(4)), grade);
    }

    @Test
    public void getLastAddedGradeWithNull() {
        Subject subject = new Subject(SUBJECT_NAME);
        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> subject.lastGrade());
        assertEquals("Grades can't be empty.", exception.getMessage());
    }

    @Test
    public void getGradesFromProvidedMonth() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByMonth(Month.JANUARY),
                createValidGradeByMonth(Month.NOVEMBER),
                createValidGradeByMonth(Month.FEBRUARY),
                createValidGradeByMonth(Month.FEBRUARY));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        List<Grade> gradesByMonth = subject.gradesByMonth(Month.FEBRUARY);
        //then
        assertEquals(List.of(
                        createValidGradeByMonth(Month.FEBRUARY),
                        createValidGradeByMonth(Month.FEBRUARY)),
                gradesByMonth);
    }

    @Test
    public void getGradesFromProvidedDay() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByDayOfWeek(DayOfWeek.WEDNESDAY),
                createValidGradeByDayOfWeek(DayOfWeek.FRIDAY),
                createValidGradeByDayOfWeek(DayOfWeek.TUESDAY),
                createValidGradeByDayOfWeek(DayOfWeek.TUESDAY));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        List<Grade> subjectGrades = subject.gradesByDay(DayOfWeek.TUESDAY);
        //then
        assertEquals(List.of(
                        createValidGradeByDayOfWeek(DayOfWeek.TUESDAY),
                        createValidGradeByDayOfWeek(DayOfWeek.TUESDAY)),
                subjectGrades);
    }

    @Test
    public void getGradesFromProvidedWeek() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByWeekOfYear(2),
                createValidGradeByWeekOfYear(1),
                createValidGradeByWeekOfYear(32),
                createValidGradeByWeekOfYear(1),
                createValidGradeByWeekOfYear(52));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        List<Grade> subjectGrades = subject.gradesByWeek(52);
        //then
        assertEquals(List.of(createValidGradeByWeekOfYear(52)), subjectGrades);
    }

    @Test
    public void getGradesWithoutDescription() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByDescription("test"),
                createValidGradeByDescription("test"),
                createValidGradeByDescription(null),
                createValidGradeByDescription(null));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        List<Grade> subjectGrades = subject.gradesWithoutDescription();
        //then
        assertEquals(List.of(
                        createValidGradeByDescription(null),
                        createValidGradeByDescription(null)),
                subjectGrades);
    }


    @Test
    public void countGradesByRate() {
        //given
        List<Grade> grades = List.of(
                createValidGradeByRate(new Rate(2)),
                createValidGradeByRate(new Rate(2)),
                createValidGradeByRate(new Rate(1)),
                createValidGradeByRate(new Rate(1)));
        Subject subject = new Subject(SUBJECT_NAME, grades);
        //when
        long numberOfGradesByRate = subject.countGradesByRate(new Rate(1));
        //then
        assertEquals(2, numberOfGradesByRate);
    }

}