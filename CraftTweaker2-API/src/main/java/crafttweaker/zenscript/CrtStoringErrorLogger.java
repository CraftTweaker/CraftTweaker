package crafttweaker.zenscript;

import crafttweaker.socket.SingleError;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

public class CrtStoringErrorLogger extends CrTErrorLogger {
    private List<SingleError> errors = new ArrayList<>();
    
    public void clear() {
        errors.clear();
    }
    
    public List<SingleError> getErrors() {
        return errors;
    }
    
    
    @Override
    public void error(ZenPosition position, String message) {
        super.error(position, message);
        errors.add(new SingleError(position.getFile().getFileName(), position.getLine(), position.getLineOffset(), message, SingleError.Level.ERROR));
    }
    
    @Override
    public void warning(ZenPosition position, String message) {
        super.warning(position, message);
        errors.add(new SingleError(position.getFile().getFileName(), position.getLine(), position.getLineOffset(), message, SingleError.Level.WARN));
    
    }
    
    @Override
    public void info(ZenPosition position, String message) {
        super.info(position, message);
        errors.add(new SingleError(position.getFile().getFileName(), position.getLine(), position.getLineOffset(), message, SingleError.Level.INFO));
    }
    
}
