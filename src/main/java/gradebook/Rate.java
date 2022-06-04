package gradebook;

public record Rate(double value) {
    public Rate {

        if(value>6 || value<1 || (value % 0.5!=0) || value == 1.5){
             throw new InvalidRateValueException("Rate must be one of 1, 2, 2.5, 3, 3.5, 4, 4.5, 5.5, 6");
        }
    }
}
