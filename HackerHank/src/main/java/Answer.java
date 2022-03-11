import java.util.OptionalInt;

public class Answer implements InputObject{
    private final String text;

    public Answer(String text) {
        this.text = text;
    }

    public String toString() {
        return String.format("Answer(text='%s')", this.text);
    }

    public String getText() {
        return this.text;
    }

    public OptionalInt getLabel() {
        throw new UnsupportedOperationException();
    }
}
