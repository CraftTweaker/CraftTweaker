/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import minetweaker.runtime.ILogger;

/**
 *
 * @author Stan Hebben
 */
public class FileLogger implements ILogger {
	private final Writer writer;
	private final PrintWriter printWriter;

	public FileLogger(File output) {
		try {
			writer = new OutputStreamWriter(new FileOutputStream(output), "utf-8");
			printWriter = new PrintWriter(writer);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("What the heck?");
		} catch (FileNotFoundException ex) {
			throw new RuntimeException("Could not open log file " + output);
		}
	}

	@Override
	public void logCommand(String message) {
		try {
			writer.write(message + "\n");
			writer.flush();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void logInfo(String message) {
		try {
			writer.write("INFO: " + message + "\n");
			writer.flush();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void logWarning(String message) {
		try {
			writer.write("WARNING: " + message + "\n");
			writer.flush();
		} catch (IOException ex) {
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
			writer.write("ERROR: " + message + "\n");
			if (exception != null) {
				exception.printStackTrace(printWriter);
			}
			writer.flush();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
