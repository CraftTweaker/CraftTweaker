package crafttweaker.socket;

import com.google.gson.*;
import crafttweaker.socket.messages.*;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Type;
import java.util.HashMap;

public class JsonMessageHandler {
    
    private static final HashMap<String, Type> TYPE_HASH_MAP = new HashMap<>();
    private static Gson gson = new GsonBuilder().create();
    private static JsonParser jsonParser = new JsonParser();
    static {
        registerType("LintRequest", LintRequestMessage.class);
        registerType("LintResponse", LintResponseMessage.class);
    }
    
    /**
     * Registers a new type of message
     */
    public static void registerType(String typeName, Type type) {
        TYPE_HASH_MAP.put(typeName, type);
    }
    
    /**
     * Gets the message type
     */
    public static Type getType(String typeName) {
        return TYPE_HASH_MAP.get(typeName);
    }
    
    public static String handleJson(String message, ChannelHandlerContext ctx) {
        // parse into json to get the type of message before converting into Java Object
        JsonElement json = jsonParser.parse(message);
        String messageType = json.getAsJsonObject().get("messageType").getAsString();
        Type type = TYPE_HASH_MAP.get(messageType);
        if(type == null) {
            System.out.println("Invalid type in json element: " + json);
            return "Invalid Type.";
        }
        
        SocketMessage obj = gson.fromJson(json, type);
        
        
        if(obj instanceof IRequestMessage) {
            SocketMessage res = ((IRequestMessage) obj).handleReceive(ctx);
            if(res != null) {
                String jsonRes = gson.toJson(res, getType(res.messageType));
                
                System.out.println("jres = " + jsonRes);
                return jsonRes;
            } else {
                return "Null response!";
            }
        } else {
            return "INVALID MESSAGE!!!";
        }
    }
}
