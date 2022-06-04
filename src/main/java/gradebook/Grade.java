package gradebook;

import java.time.LocalDate;

public record Grade(Rate rate,LocalDate rateDate,String description) {
}
