package crafttweaker.socket.messages;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.CrTTweaker;
import crafttweaker.socket.SingleError;
import io.netty.channel.ChannelHandlerContext;

import java.util.*;

public class LintRequestMessage extends SocketMessage<LintRequestMessage> implements IRequestMessage<LintResponseMessage> {
    public LintRequestMessage() {
        super("LintRequest");
    }
    
    @Override
    public LintResponseMessage handleReceive(ChannelHandlerContext ctx) {
        CraftTweakerAPI.logInfo("Received a linting request, starting to lint.");
        
        List<SingleError> errors = new ArrayList<>();
        boolean didLoad = lint(errors);
        CraftTweakerAPI.logInfo("errors = " + errors);
        
        return new LintResponseMessage(errors, didLoad);
    }
    
    private boolean lint(List<SingleError> list) {
        if(!(CraftTweakerAPI.tweaker instanceof CrTTweaker)) {
            CraftTweakerAPI.logError("We currently load with an unsupported loader class: " + CraftTweakerAPI.tweaker.getClass());
            return false;
        }
        
        CrTTweaker tweaker = (CrTTweaker) CraftTweakerAPI.tweaker;
        
        return tweaker.loadScript(true, "NONE", list, true);
    }
    
    @Override
    public String toString() {
        return "LintRequestMessage{}";
    }
}
