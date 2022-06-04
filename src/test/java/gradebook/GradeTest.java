package gradebook;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {

    @Test
    public void createValidGrade(){
        Rate rate = new Rate(1);
        LocalDate localDate = LocalDate.of(2022, 2, 2);
        String description = "descripion";
        Grade grade = new Grade(rate, localDate, description);

        assertEquals(rate, grade.rate());
        assertEquals(localDate, grade.rateDate());
        assertEquals(description, grade.description());
    }
}