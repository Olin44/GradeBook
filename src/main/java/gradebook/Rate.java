package gradebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record Rate(double value) {
    private static final List<Double> VALID_RATES = List.of(1.0, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0);

    public Rate {
        if (!VALID_RATES.contains(value)) {
            throw new InvalidRateValueException(createExceptionString());
        }
    }
    private String createExceptionString() {
        return VALID_RATES.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "Rate must be one of ", ""));
    }
}
