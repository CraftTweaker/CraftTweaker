package crafttweaker.socket;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.CrTTweaker;

import java.util.*;

public class LintRequestMessage extends SocketMessage implements IRequestMessage<LintResponseMessage> {
    // public String sourcePath;
    // public String comment;
    
    public LintRequestMessage() {
        super("LintRequest");
    }
    
    @Override
    public LintResponseMessage handleReceive() {
        System.out.println("Received a linting request, starting to lint.");
    
        List<SingleError> errors = new ArrayList<>();
        boolean didLoad = lint(errors);
        System.out.println("errors = " + errors);
        
        return new LintResponseMessage(errors, didLoad);
    }
    
    private boolean lint(List<SingleError> list) {
        if(!(CraftTweakerAPI.tweaker instanceof CrTTweaker)) {
            CraftTweakerAPI.logError("We currently load with an unsupported loader class: " + CraftTweakerAPI.tweaker.getClass());
            return false;
        }
        
        CrTTweaker tweaker = (CrTTweaker) CraftTweakerAPI.tweaker;
        
        tweaker.loadScript(true, "NONE", list, true);
        
        return false;
    }
    
    @Override
    public String toString() {
        return "LintRequestMessage{}";
    }
}
