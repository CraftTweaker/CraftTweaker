package crafttweaker.mc1120.logger;

import crafttweaker.api.player.IPlayer;
import crafttweaker.runtime.ILogger;
import net.minecraftforge.fml.common.*;

import java.io.*;
import java.util.regex.Pattern;

public class MCLogger implements ILogger {

    private static final Pattern FORMATTING_CODE_PATTERN = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
    private Writer writer;
    private PrintWriter printWriter;
    private boolean isDefaultDisabled = false;

    public MCLogger(File output) {
        try {
            if (output.isDirectory()) {
                boolean success = output.delete();
                if(!success) {
                    throw new RuntimeException("Failed to delete log file-as-directory " + output);
                }
            }
            writer = new OutputStreamWriter(new FileOutputStream(output), "utf-8");
            printWriter = new PrintWriter(writer);
        } catch(UnsupportedEncodingException ex) {
            throw new RuntimeException("What the heck?");
        } catch(FileNotFoundException ex) {
            
            throw new RuntimeException("Could not open log file " + output);
        }
    }

    @Override
    public void logCommand(String message) {
        try {
            writer.write(stripMessage(message) + "\n");
            writer.flush();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void logInfo(String message) {
        try {
            writer.write("[" + Loader.instance().getLoaderState() + "][" + FMLCommonHandler.instance().getEffectiveSide() + "][INFO] " + stripMessage(message) + "\n");
            writer.flush();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void logWarning(String message) {
        try {
            writer.write("[" + Loader.instance().getLoaderState() + "][" + FMLCommonHandler.instance().getEffectiveSide() + "][WARNING] " + stripMessage(message) + "\n");
            writer.flush();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void logError(String message) {
        logError(message, null);
    }

    @Override
    public void logError(String message, Throwable exception) {
        try {
            writer.write("[" + Loader.instance().getLoaderState() + "][" + FMLCommonHandler.instance().getEffectiveSide() + "][ERROR] " + stripMessage(message) + "\n");
            if(exception != null) {
                exception.printStackTrace(printWriter);
            }
            writer.flush();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public void logPlayer(IPlayer player) {
        //no-op
    }
    
    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     */
    public String stripMessage(String message) {
        return message == null ? null : FORMATTING_CODE_PATTERN.matcher(message).replaceAll("");
    }
    
    @Override
    public void logDefault(String message) {
        if(!isLogDisabled())
            logInfo(message);
    }
    
    @Override
    public boolean isLogDisabled() {
        return isDefaultDisabled;
    }
    
    @Override
    public void setLogDisabled(boolean logDisabled) {
        this.isDefaultDisabled = logDisabled;
    }
}
