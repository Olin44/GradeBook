package gradebook;

import java.time.DayOfWeek;
import java.time.LocalDate;

public record Grade(Rate rate, LocalDate rateDate, String description) {
    public Grade {
        if (rate == null) {
            throw new NullPointerException("Rate can't be null");
        }
        if (rateDate == null) {
            throw new NullPointerException("Rate date can't be null");
        }
        if (description != null && description.isBlank()) {
            throw new InvalidGradeException("Description can't be empty");
        }
        DayOfWeek dayOfWeek = rateDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new InvalidGradeException("Grade can't be given in sunday and saturday");
        }
    }


}
