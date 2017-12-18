package deadbycube.command.value;

public class CommandStringValue extends CommandValue<String> {

    @Override
    protected void parse(String value) {
        this.setValue(value);
    }

}
