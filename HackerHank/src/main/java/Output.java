import java.util.OptionalInt;

public class Output implements InputObject{
    private final String text;
    private final OptionalInt label;

    public Output(String text) {
        this.text = text;
        this.label = OptionalInt.empty();
    }

    public Output(String text, int label) {
        this.text = text;
        this.label = OptionalInt.of(label);
    }

    public String toString() {
        if(this.label.isPresent()) {
            return String.format("Output(text='%s', label=%d)", this.text, this.label.getAsInt());
        }
        return String.format("Output(text='%s')", this.text);
    }

    public String getText() {
        return this.text;
    }

    public OptionalInt getLabel() {
        return this.label;
    }
}
