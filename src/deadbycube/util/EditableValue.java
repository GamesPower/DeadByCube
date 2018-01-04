package deadbycube.util;

import java.util.ArrayList;

public class EditableValue {

    private final ArrayList<Modifier> modifiers = new ArrayList<>();
    private double baseValue;
    private double value;

    private Double forcedValue = null;

    public EditableValue(double baseValue) {
        this.baseValue = baseValue;
        this.value = baseValue;
    }

    private void updateValue() {
        if (forcedValue != null) {
            this.value = forcedValue;
        } else {
            this.value = baseValue;
            for (Modifier modifier : modifiers) {
                switch (modifier.operation) {
                    case ADD:
                        this.value += modifier.value;
                        break;
                    case MULTIPLY:
                        this.value += (baseValue * modifier.value);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void addModifier(String name, double value, Operation operation) {
        this.modifiers.add(new Modifier(name, value, operation));
        this.updateValue();
    }

    public void removeModifier(String name) {
        modifiers.removeIf(modifier -> modifier.name.equals(name));
        this.updateValue();
    }

    public void forceValue(double forcedValue) {
        this.forcedValue = forcedValue;
        this.updateValue();
    }

    public void resetValue() {
        this.forcedValue = null;
        this.updateValue();
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
        this.updateValue();
    }

    public double getValue() {
        return value;
    }

    class Modifier {

        private final String name;
        private final double value;
        private final Operation operation;

        Modifier(String name, double value, Operation operation) {
            this.name = name;
            this.value = value;
            this.operation = operation;
        }

        public String getName() {
            return name;
        }

    }

    public enum Operation {
        ADD, MULTIPLY
    }

}
