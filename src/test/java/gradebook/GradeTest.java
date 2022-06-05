package gradebook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {

    public static final Rate VALID_RATE = new Rate(1);

    public static final LocalDate VALID_RATE_DATE = LocalDate.of(2022, 2, 2);

    public static final String VALID_DESCRIPTION = "descripion";

    @Test
    public void createValidGrade() {
        Grade grade = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        assertEquals(VALID_RATE, grade.rate());
        assertEquals(VALID_RATE_DATE, grade.rateDate());
        assertEquals(VALID_DESCRIPTION, grade.description());
    }

    @Test
    public void gradeInitializedWithNullRate() {
        var exception = assertThrows(NullPointerException.class,
                () -> new Grade(null, VALID_RATE_DATE, VALID_DESCRIPTION));
        assertEquals(exception.getMessage(), "Rate can't be null");
    }

    @Test
    public void gradeInitializedWithNullRateDate() {
        var exception = assertThrows(NullPointerException.class,
                () -> new Grade(VALID_RATE, null, VALID_DESCRIPTION));
        assertEquals(exception.getMessage(), "Rate date can't be null");
    }

    @Test
    public void createValidGradeWithoutDescription() {
        Grade grade = new Grade(VALID_RATE, VALID_RATE_DATE, null);
        assertEquals(VALID_RATE, grade.rate());
        assertEquals(VALID_RATE_DATE, grade.rateDate());
        assertNull(grade.description());
    }

    @Test
    public void createInvalidGradeWithWhiteSpaceDescription() {
        var exception = assertThrows(InvalidGradeException.class,
                () -> new Grade(VALID_RATE, VALID_RATE_DATE, "    "));
        assertEquals(exception.getMessage(), "Description can't be empty");
    }

    @Test
    public void createInvalidGradeWithEmptyDescription() {
        var exception = assertThrows(InvalidGradeException.class,
                () -> new Grade(VALID_RATE, VALID_RATE_DATE, ""));
        assertEquals(exception.getMessage(), "Description can't be empty");
    }

    @ParameterizedTest
    @MethodSource("gradeDates")
    public void gradeCantBeGivenInSundayAndSaturday(LocalDate gradeDate) {
        var exception = assertThrows(InvalidGradeException.class,
                () -> new Grade(VALID_RATE, gradeDate, VALID_DESCRIPTION));
        assertEquals(exception.getMessage(), "Grade can't be given in sunday and saturday");
    }

    public static List<LocalDate> gradeDates() {
        return List.of(
                LocalDate.of(2022, 6, 4),
                LocalDate.of(2022, 6, 5)
        );
    }
}