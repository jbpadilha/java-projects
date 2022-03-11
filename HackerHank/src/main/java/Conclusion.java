import java.util.OptionalInt;

public class Conclusion implements InputObject{
    private final String text;

    public Conclusion(String text) {
        this.text = text;
    }

    public String toString() {
        return String.format("Conclusion(text='%s')", this.text);
    }

    public String getText() {
        return this.text;
    }

    public OptionalInt getLabel() {
        throw new UnsupportedOperationException();
    }
}
