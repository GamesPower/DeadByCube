package deadbycube.util.attribute;

import java.util.ArrayList;
import java.util.List;

public class Attribute {

    private double baseValue;
    private final ArrayList<AttributeModifier> modifiers = new ArrayList<>();

    public double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }

    public double getValue() {
        double value = baseValue;
        for (AttributeModifier modifier : modifiers) {
            switch (modifier.getOperation()) {
                case ADD:
                    value += modifier.getValue();
                    break;
                case MULTIPLY:
                    value += (baseValue * modifier.getValue());
                    break;
                default:
                    break;
            }
        }
        return value;
    }

    public List<AttributeModifier> getModifiers() {
        return modifiers;
    }

}
