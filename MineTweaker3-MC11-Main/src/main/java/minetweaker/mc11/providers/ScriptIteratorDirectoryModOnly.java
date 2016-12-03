/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc11.providers;

import minetweaker.runtime.IScriptIterator;
import net.minecraftforge.fml.common.Loader;

import java.io.*;
import java.util.*;

/**
 * @author Stan
 */
public class ScriptIteratorDirectoryModOnly implements IScriptIterator {
	
	private final File directory;
	private Iterator<File> contents;
	private File current;
	
	public ScriptIteratorDirectoryModOnly(File directory) {
		this.directory = directory;
		
		File scriptDir = new File(directory, "scripts");
		List<File> contentsList = new ArrayList<>();
		if(scriptDir.exists()) {
			iterate(scriptDir, contentsList);
		}
		contents = contentsList.iterator();
	}
	
	@Override
	public String getGroupName() {
		return directory.getName();
	}
	
	@Override
	public boolean next() {
		if(contents.hasNext()) {
			current = contents.next();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String getName() {
		return current.getAbsolutePath().substring(directory.getAbsolutePath().length());
	}
	
	@Override
	public InputStream open() throws IOException {
		return new BufferedInputStream(new FileInputStream(current));
	}
	
	private static void iterate(File directory, List<File> contentsList) {
		for(File file : directory.listFiles()) {
			if(file.isDirectory()) {
				iterate(file, contentsList);
			} else if(file.isFile() && file.getName().endsWith(".zsm") && Loader.isModLoaded(file.getName().substring(0, file.getName().length() - ".zsm".length()))) {
				contentsList.add(file);
			}
		}
	}
}
