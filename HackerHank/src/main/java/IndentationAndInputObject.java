public class IndentationAndInputObject {
    private final int indentation;
    private final InputObject inputObject;

    public IndentationAndInputObject(int indentation, InputObject inputObject) {
        this.indentation = indentation;
        this.inputObject = inputObject;
    }

    public String toString() {
        return String.format("IndentationAndInputObject(indentation=%d, inputObject=%s)", this.indentation, this.inputObject);
    }

    public int getIndentation() {
        return this.indentation;
    }

    public InputObject getInputObject() {
        return this.inputObject;
    }
}
