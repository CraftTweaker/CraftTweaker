package crafttweaker.socket;

import com.google.gson.*;
import crafttweaker.CraftTweakerAPI;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CrTSocketHandler {
    
    public static final int PORT = 24532;
    public static final AtomicBoolean shouldRun = new AtomicBoolean(true);
    private static final HashMap<String, Type> TYPE_HASH_MAP = new HashMap<>();
    static {
        registerType("LintRequest", LintRequestMessage.class);
        registerType("LintResponse", LintResponseMessage.class);
    }
    private Gson gson;
    private JsonParser jsonParser;
    
    public CrTSocketHandler() {
        new Thread(this::handleServerSocket).start();
        gson = new GsonBuilder().create();
        jsonParser = new JsonParser();
    }
    
    public static void registerType(String typeName, Type type) {
        TYPE_HASH_MAP.put(typeName, type);
    }
    
    public static Type getType(String typeName) {
        return TYPE_HASH_MAP.get(typeName);
    }
    
    private void handleServerSocket() {
        
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            
            while(shouldRun.get()) {
                final Socket clientSocket = serverSocket.accept();
                CraftTweakerAPI.logInfo("Reached connection from " + clientSocket);
                if(!clientSocket.getInetAddress().isLoopbackAddress()) {
                    CraftTweakerAPI.logInfo("Invalid connection, not from localhost, rejecting: " + clientSocket);
                }
                
                new Thread(() -> {
                    try {
                        this.handleClientSocket(clientSocket);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleClientSocket(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String inputLine;
        
        StringBuilder builder = new StringBuilder();
        
        // Socket:
        while((inputLine = in.readLine()) != null) {
            builder.setLength(0);
            builder.append(inputLine).append("\n");
            
            while(in.ready() && (inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }
    
            String message = builder.toString();
    
            System.out.println("message = " + message);
            if (!message.startsWith("{")) {
                System.out.println("Message is no valid json, skipping.");
                continue;
            }
    
            JsonElement json = jsonParser.parse(message);
            String messageType = json.getAsJsonObject().get("messageType").getAsString();
            
            Type type = TYPE_HASH_MAP.get(messageType);
            if(type == null) {
                CraftTweakerAPI.logError("Invalid type in json element: " + json);
                continue;
            }
            
            SocketMessage obj = gson.fromJson(json, type);
            out.println("Recieved: " + obj.toString());
            
            
            if(obj instanceof IRequestMessage) {
                SocketMessage res = ((IRequestMessage) obj).handleReceive();
                out.println(gson.toJson(res, getType(res.messageType)));
            } else {
                out.println("INVALID MESSAGE!!!");
            }
            
            if(inputLine.equals("Bye."))
                break;
        }
    }
}
