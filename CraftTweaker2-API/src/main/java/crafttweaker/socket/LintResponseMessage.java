package crafttweaker.socket;

import java.util.List;

public class LintResponseMessage extends SocketMessage {
    
    public static final String MESSAGE_TYPE = "LintResponse";
    public List<SingleError> errors;
    
    public LintResponseMessage(List<SingleError> errors) {
        super(MESSAGE_TYPE);
        this.errors = errors;
    }
    
    @Override
    public void handleReceive() {
        System.out.println("I was received: " + toString());
    }
    
    @Override
    public String toString() {
        return "LintResponseMessage{" + "errors=" + errors + '}';
    }
}
