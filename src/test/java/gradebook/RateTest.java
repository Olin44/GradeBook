package gradebook;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RateTest {

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 2.5, 3, 3.5, 4, 4.5, 5.5, 6})
    public void validRates(double rateValue) {
        Rate rate = new Rate(rateValue);
        assertEquals(rateValue, rate.value());
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.MAX_VALUE, Double.MIN_VALUE, 23, 67.67, 4.999999})
    public void invalidRates(double rateValue) {
        var exception = assertThrows(InvalidRateValueException.class,
                () -> new Rate(rateValue));
        assertEquals(exception.getMessage(), "Rate must be one of 1, 2, 2.5, 3, 3.5, 4, 4.5, 5.5, 6");
    }
}