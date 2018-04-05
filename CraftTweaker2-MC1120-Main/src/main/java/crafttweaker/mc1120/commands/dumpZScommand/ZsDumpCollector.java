package crafttweaker.mc1120.commands.dumpZScommand;

import com.google.gson.*;
import crafttweaker.mc1120.commands.dumpZScommand.types.DumpBracketHandler;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.TypeRegistry;
import stanhebben.zenscript.dump.*;
import stanhebben.zenscript.dump.CustomTypeAdapterFactory;
import stanhebben.zenscript.dump.types.*;
import stanhebben.zenscript.symbols.SymbolPackage;
import stanhebben.zenscript.util.Pair;

import java.util.*;

public class ZsDumpCollector {
    private transient final Gson gson;
    
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<DumpBracketHandler> bracketHandlerDumps = new ArrayList<>();
    private List<DumpZenType> zenTypeDumps = new ArrayList<>();
    private List<IDumpedObject> rootDumps = new ArrayList<>();
    
    public ZsDumpCollector() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(new CustomTypeAdapterFactory())
                /*.registerTypeAdapter(IDumpable.class, GSONDumpableSerializer.INSTANCE)
                .registerTypeAdapter(DumpDummy.class, GSONDumpableSerializer.INSTANCE)
                .registerTypeAdapter(DumpZenType.class, GSONDumpableSerializer.INSTANCE)
                .registerTypeAdapter(DumpClassBase.class, GSONDumpableSerializer.INSTANCE)
                .registerTypeAdapter(DumpBracketHandler.class, GSONDumpableSerializer.INSTANCE)*/
                .create();
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
