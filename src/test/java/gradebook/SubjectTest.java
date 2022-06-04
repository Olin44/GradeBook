package gradebook;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    public static final Rate VALID_RATE = new Rate(1);

    public static final LocalDate VALID_RATE_DATE = LocalDate.of(2022, 2, 2);

    public static final String VALID_DESCRIPTION = "descripion";

    @Test
    public void createValidSubject() {
        Subject subject =  new Subject(SUBJECT_NAME);
        Grade grade1 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        Grade grade2 = new Grade(VALID_RATE, VALID_RATE_DATE, VALID_DESCRIPTION);
        subject.addGrade(grade1);
        subject.addGrade(grade2);
        assertEquals(List.of(grade1, grade2), subject.getGrades());
    }

    @Test
    public void calculateAverageGradeRates() {
    }

    @Test
    public void calculateMinGradeRate() {
    }

    @Test
    public void calculateMaxGradeRate() {
    }

    @Test
    public void getLastAddedGrade() {
    }

    @Test
    public void getGradesFromProvidedMonth() {
    }

    @Test
    public void getGradesFromProvidedDay() {
    }

    @Test
    public void getGradesFromProvidedWeek() {
    }

    @Test
    public void getGradesWithoutDescription() {
    }

    @Test
    public void countGradesByRate() {
    }
}