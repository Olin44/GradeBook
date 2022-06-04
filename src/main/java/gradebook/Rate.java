package gradebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Rate(double value) {
    static List<Double> validRates = Arrays.asList(1.0, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0);

    public Rate {
        if (!validRates.contains(value)) {
            throw new InvalidRateValueException("Rate must be one of 1, 2, 2.5, 3, 3.5, 4, 4.5, 5.5, 6");
        }
    }
}
