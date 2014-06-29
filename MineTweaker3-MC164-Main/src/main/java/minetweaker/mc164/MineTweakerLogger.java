/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import minetweaker.runtime.ILogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

/**
 *
 * @author Stan
 */
public class MineTweakerLogger implements ILogger {
	private Writer writer;
	private final List<String> unreportedErrors;
	private final List<EntityPlayer> players;
	
	public MineTweakerLogger() {
		File logFile = new File("minetweaker.log");
		if (logFile.exists()) logFile.delete();
		
		unreportedErrors = new ArrayList<String>();
		players = new ArrayList<EntityPlayer>();
		
		try {
			writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(logFile)));
		} catch (IOException ex) {
			
		}
	}
	
	public void clear() {
		unreportedErrors.clear();
	}
	
	public void addPlayer(EntityPlayer player) {
		players.add(player);
		
		if (!unreportedErrors.isEmpty()) {
			for (String error : unreportedErrors) {
				player.sendChatToPlayer(ChatMessageComponent.createFromText(error));
			}
			
			unreportedErrors.clear();
		}
	}
	
	public void removePlayer(EntityPlayer player) {
		players.remove(player);
	}
	
	@Override
	public void logCommand(String message) {
		try {
			writer.append("COMMAND: ");
			writer.append(message);
			writer.append('\n');
			writer.flush();
		} catch (IOException ex) {
			
		}
		
		System.out.println("COMMAND: " + message);
	}
	
	@Override
	public void logInfo(String message) {
		try {
			writer.append("INFO: ");
			writer.append(message);
			writer.append('\n');
			writer.flush();
		} catch (IOException ex) {
			
		}
		
		System.out.println("INFO: " + message);
	}

	@Override
	public void logWarning(String message) {
		report("WARNING: " + message);
	}

	@Override
	public void logError(String message) {
		report("ERROR: " + message);
	}
	
	private void report(String message) {
		try {
			writer.append(message);
			writer.append('\n');
			writer.flush();
		} catch (IOException ex) {
			
		}
		
		System.out.println(message);
		
		if (players.isEmpty()) {
			unreportedErrors.add(message);
		} else {
			ChatMessageComponent text = ChatMessageComponent.createFromText(message);
			for (EntityPlayer player : players) {
				player.sendChatToPlayer(text);
			}
		}
	}
}
