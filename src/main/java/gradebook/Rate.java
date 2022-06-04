package gradebook;

public enum Rate{
    ONE (1),
    TWO (2),
    THREE (3),
    FOUR (4),
    FIVE (5),
    SIX (6);

    private final int value;
    Rate(int i) {
        this.value = i;
    }
    public int Value(){
        return this.value;
    }
}
