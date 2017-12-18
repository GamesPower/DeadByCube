package deadbycube.command.value;

public class CommandIntValue extends CommandValue<Integer> {

    @Override
    public void parse(String value) {
        try {
            this.setValue(Integer.parseInt(value));
        } catch (NumberFormatException ignored) {
        }
    }

}
