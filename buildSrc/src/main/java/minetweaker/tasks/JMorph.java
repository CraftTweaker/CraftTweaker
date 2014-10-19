package minetweaker.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * Java preprocessor. Enables conditional modification of Java source files.
 * 
 * @author Stan Hebben
 */
public class JMorph extends DefaultTask {
	@InputDirectory
	public File inputDir;
	
	@InputFile
	public File propertiesFile;
	
	@TaskAction
	public void doTask() {
		try {
			Properties properties = new Properties();
			BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
			properties.load(reader);
			reader.close();
			
			processDirectory(inputDir, properties);
		} catch (IOException ex) {
			Logger.getLogger(JMorph.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void processDirectory(File dir, Properties properties) throws IOException {
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				if (file.getName().endsWith(".java") || file.getName().endsWith(".java.d")) {
					processJavaFile(file, properties);
				}
			} else if (file.isDirectory()) {
				processDirectory(file, properties);
			}
		}
	}
	
	private static void processJavaFile(File file, Properties properties) throws IOException {
		StringBuilder output = new StringBuilder();
		
		File outputFile = file.getName().endsWith(".java.d")
					? new File(file.getParentFile(), file.getName().substring(0, file.getName().length() - 2))
					: file;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			if (line == null) return;
			
			Stack<Boolean> conditions = new Stack<>();
			conditions.push(Boolean.TRUE);
			do {
				String trimmed = line.trim();
				if (trimmed.startsWith("//#fileifdef")) {
					String id = getIdentifier(trimmed, 13);
					if (properties.containsKey(id)) {
						if (file.getName().endsWith(".java.d")) {
							outputFile = new File(file.getParentFile(), file.getName().substring(0, file.getName().length() - 2));
						} else {
							outputFile = file;
						}
					} else {
						if (file.getName().endsWith(".java")) {
							outputFile = new File(file.getParentFile(), file.getName() + ".d");
						} else {
							outputFile = file;
						}
					}
				} else if (trimmed.startsWith("//#fileifndef")) {
					String id = getIdentifier(trimmed, 14);
					if (!properties.containsKey(id)) {
						outputFile = file.getName().endsWith(".java.d")
								? new File(file.getParentFile(), file.getName().substring(0, file.getName().length() - 2))
								: file;
					} else {
						outputFile = file.getName().endsWith(".java")
								? new File(file.getParentFile(), file.getName() + ".d")
								: file;
					}
				} else if (trimmed.startsWith("//#ifdef")) {
					String id = getIdentifier(trimmed, 9);
					conditions.push(properties.containsKey(id));
				} else if (trimmed.startsWith("//#ifndef")) {
					String id = getIdentifier(trimmed, 10);
					conditions.push(!properties.containsKey(id));
				} else if (trimmed.startsWith("//#else")) {
					conditions.push(!conditions.pop());
				} else if (trimmed.startsWith("//#elseif")) {
					conditions.pop();
					String id = getIdentifier(trimmed, 10);
					conditions.push(properties.containsKey(id));
				} else if (trimmed.startsWith("//#endif")) {
					conditions.pop();
					if (conditions.isEmpty()) {
						System.err.println("Error: too many #endifs in " + file.getName());
						return;
					}
				} else if (trimmed.startsWith("//+")) {
					if (conditions.peek()) line = uncomment(line).toString();
				} else if (trimmed.length() != 0) {
					if (!conditions.peek()) line = comment(line).toString();
				}
				
				output.append(line).append('\n');
				
				line = reader.readLine();
			} while (line != null);
		}
		
		if (outputFile != file)
			file.delete();
		
		try (FileWriter writer = new FileWriter(outputFile)) {
			writer.append(output);
		}
	}
	
	private static CharSequence comment(String line) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ' || line.charAt(i) == '\t') {
				output.append(line.charAt(i));
				continue;
			}
			output.append("//+").append(line.substring(i));
			break;
		}
		return output;
	}
	
	private static CharSequence uncomment(String line) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ' || line.charAt(i) == '\t') {
				output.append(line.charAt(i));
			} else if (line.charAt(i) == '/' && line.charAt(i + 1) == '/' && line.charAt(i + 2) == '+') {
				output.append(line.substring(i + 3));
				break;
			} else {
				return line;
			}
		}
		return output;
	}
	
	private static String getIdentifier(String line, int offset) {
		int from = offset;
		int to = offset;
		if (!Character.isJavaIdentifierStart(line.charAt(to))) return null;
		to++;
		while (to < line.length() && Character.isJavaIdentifierPart(line.charAt(to))) to++;
		return line.substring(from, to);
	}
}
