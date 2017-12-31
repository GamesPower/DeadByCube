package deadbycube.util.attribute;

public class AttributeModifier {

    private final String name;
    private final double value;
    private final Operation operation;

    public AttributeModifier(String name, double value, Operation operation) {
        this.name = name;
        this.value = value;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }

    public enum Operation {
        ADD, MULTIPLY
    }

}
