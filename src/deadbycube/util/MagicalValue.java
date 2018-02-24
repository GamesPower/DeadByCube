package deadbycube.util;

import java.util.ArrayList;

public class MagicalValue {

    private final ArrayList<Modifier> modifiers = new ArrayList<>();
    private double baseValue;
    private double value;

    private boolean forced = false;

    public MagicalValue(double baseValue) {
        this.baseValue = baseValue;
        this.value = baseValue;
        this.updateValue();
    }

    protected void updateValue() {
        if (!forced) {
            this.value = baseValue;
            for (Modifier modifier : modifiers) {
                switch (modifier.operation) {
                    case ADD:
                        this.value += modifier.value;
                        break;
                    case SUBTRACT:
                        this.value -= modifier.value;
                        break;
                    case PERCENTAGE:
                        this.value += (baseValue * (modifier.value / 100));
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
        this.value = forcedValue;
        this.forced = true;
        this.updateValue();
    }

    public void resetValue() {
        this.forced = false;
        this.updateValue();
    }

    public double getValue() {
        return value;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
        this.updateValue();
    }

    public enum Operation {
        ADD, SUBTRACT, PERCENTAGE
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

}
