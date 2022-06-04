package gradebook;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {

    @Test
    public void createValidGrade(){
        Rate rate = Rate.ONE;
        LocalDate localDate = LocalDate.of(2022, 2, 2);
        String description = "descripion";
        Grade grade = new Grade(rate, localDate, description);
        Grade grade2 = new Grade(rate, localDate, description);

        assertEquals(grade.hashCode(), grade2.hashCode());
//        assertEquals(rate, grade.rate());
//        assertEquals(localDate, grade.date());
//        assertEquals(description, grade.description());
    }
}