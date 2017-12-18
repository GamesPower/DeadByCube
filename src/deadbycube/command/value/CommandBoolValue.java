package deadbycube.command.value;

public class CommandBoolValue extends CommandValue<Boolean> {

    @Override
    protected void parse(String value) {
        this.setValue(Boolean.parseBoolean(value));
    }

}
