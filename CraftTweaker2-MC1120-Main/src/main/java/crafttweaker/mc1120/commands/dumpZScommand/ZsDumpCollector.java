package crafttweaker.mc1120.commands.dumpZScommand;

import com.google.gson.*;
import crafttweaker.mc1120.commands.dumpZScommand.types.DumpBracketHandler;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.TypeRegistry;
import stanhebben.zenscript.dump.*;
import stanhebben.zenscript.dump.types.*;
import stanhebben.zenscript.symbols.SymbolPackage;
import stanhebben.zenscript.util.Pair;

import java.util.*;

public class ZsDumpCollector {
    private transient final Gson gson;
    
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<DumpBracketHandler> bracketHandlerDumps = new ArrayList<>();
    private List<DumpZenType> zenTypeDumps = new ArrayList<>();
    private List<IDumpable> rootDumps = new ArrayList<>();
    
    public ZsDumpCollector() {
        
        Class<?> serializerClasses[] = {DumpDummy.class, DumpZenType.class, DumpClassBase.class, DumpBracketHandler.class, DumpZenTypeNative.class, DumpIJavaMethod.class,
        }; // this workaround has to be used because for whatever reason registerTypeHierarchyAdapter() doesn't work as intended
    
	
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        for(Class<?> serializerClass : serializerClasses) {
            builder.registerTypeAdapter(serializerClass, GSONDumpableSerializer.INSTANCE);
        }
        
        gson = builder.create();
    }
    
    void collectTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.getTypeMap().forEach((clazz, type) -> zenTypeDumps.add(type.asDumpedObject().get(0)));
    }
    
    void collectBracketHandlers(Set<Pair<Integer, IBracketHandler>> bracketHandlers){
        for(Pair<Integer, IBracketHandler> bracketHandler : bracketHandlers) {
            bracketHandlerDumps.add(new DumpBracketHandler(bracketHandler.getValue(), bracketHandler.getKey()));
        }
    }
    
    void collectRoot(SymbolPackage symbolPackage){
        rootDumps.addAll(symbolPackage.asDumpedObject());
    }
    
    String toJson(){
        return gson.toJson(this);
    }
}
