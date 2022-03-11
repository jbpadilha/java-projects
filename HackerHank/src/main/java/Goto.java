import java.util.OptionalInt;

public class Goto implements InputObject{
    private final int label;

    public Goto(int label) {
        this.label = label;
    }

    public String toString() {
        return String.format("Goto(label=%d)", this.label);
    }

    public String getText() {
        throw new UnsupportedOperationException();
    }

    public OptionalInt getLabel() {
        return OptionalInt.of(this.label);
    }
}
