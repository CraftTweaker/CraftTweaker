package crafttweaker.socket;

import com.google.gson.*;

import java.io.*;
import java.net.Socket;

public class TestSocket {
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", CrTSocketHandler.PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
            LintRequestMessage lint = new LintRequestMessage();
            // lint.comment = "this is a multiline\ncomment";
            // lint.sourcePath = "D:\\Users\\jonas\\Documents\\GitHub\\CraftTweaker\\CraftTweaker2-API\\src\\main\\java\\crafttweaker\\socket";
            
            String j = gson.toJson(lint);
            out.println(j);
            
            
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                System.out.println("inputLine = " + inputLine);
            }
            
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
