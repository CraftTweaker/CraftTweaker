/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import minetweaker.runtime.ILogger;

/**
 *
 * @author Stan
 */
public class MineTweakerLogger implements ILogger {
	private Writer writer;
	
	public MineTweakerLogger() {
		File logFile = new File("minetweaker.log");
		if (logFile.exists()) logFile.delete();
		
		try {
			writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(logFile)));
		} catch (IOException ex) {
			
		}
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
		try {
			writer.append("WARNING: ");
			writer.append(message);
			writer.append('\n');
			writer.flush();
		} catch (IOException ex) {
			
		}
		
		System.out.println("WARNING: " + message);
	}

	@Override
	public void logError(String message) {
		try {
			writer.append("ERROR: ");
			writer.append(message);
			writer.append('\n');
			writer.flush();
		} catch (IOException ex) {
			
		}
		
		System.out.println("ERROR: " + message);
	}
}
