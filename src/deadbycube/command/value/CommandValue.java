package deadbycube.command.value;

public abstract class CommandValue<T> {

    private T value;

    protected abstract void parse(String value);

    public T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }

}
