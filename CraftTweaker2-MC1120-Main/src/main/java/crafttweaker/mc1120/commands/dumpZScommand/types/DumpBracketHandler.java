package crafttweaker.mc1120.commands.dumpZScommand.types;

import com.google.gson.*;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.dump.IDumpedObject;

public class DumpBracketHandler implements IDumpedObject {
    
    private final int priority;
    private final String javaPath;
    private final String regex;
    private final String returnedClass;
    
    public DumpBracketHandler(IBracketHandler bracketHandler, int priority) {
        this.priority = priority;
        this.javaPath = bracketHandler.getClass().getName();
        this.regex = bracketHandler.getRegexMatchingString();
        this.returnedClass = bracketHandler.getReturnedClass() != null ? bracketHandler.getReturnedClass().getCanonicalName() : "NO CLASS DEFINED";
    }
    
    public int getPriority() {
        return priority;
    }
    
    public String getJavaPath() {
        return javaPath;
    }
    
    public String getRegex() {
        return regex;
    }
    
    public String getReturnedClass() {
        return returnedClass;
    }
    
    @Override
    public JsonElement serialize(JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        
        object.addProperty("javaPath", javaPath);
        object.addProperty("priority", priority);
        object.addProperty("regex", regex);
        object.addProperty("returnedClass", returnedClass);
        
        return object;
    }
}
