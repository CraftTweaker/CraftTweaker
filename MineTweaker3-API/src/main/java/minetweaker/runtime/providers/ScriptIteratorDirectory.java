/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime.providers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import minetweaker.MineTweakerAPI;
import minetweaker.runtime.IScriptIterator;

/**
 *
 * @author Stan
 */
public class ScriptIteratorDirectory implements IScriptIterator {
	private final File directory;
	private Iterator<File> contents;
	private File current;
	private String groupName;

	public ScriptIteratorDirectory(File scriptDir) {
		directory = scriptDir;
		List<File> contentsList = new ArrayList<File>();
		if (directory.exists()) {
			iterate(directory, contentsList);
		}
		contents = contentsList.iterator();

		groupName = directory.getName() + "_" + directory.hashCode();
		if (directory.getParentFile() != null) {
			groupName = directory.getParentFile().getName() + ":" + groupName;
		}
	}

	@Override
	public String getGroupName() {
		return groupName;
	}

	@Override
	public boolean next() {
		if (contents.hasNext()) {
			current = contents.next();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return current.getAbsolutePath().substring(directory.getAbsolutePath().length() + 1);
	}

	@Override
	public InputStream open() throws IOException {
		return new BufferedInputStream(new FileInputStream(current));
	}

	private static void iterate(File directory, List<File> contentsList) {
		File[] files = directory.listFiles();
		if (files == null) return;
		for (File file : files) {
			if (file.isDirectory() && !file.getName().equals("disabled")) {
				iterate(file, contentsList);
			} else if (file.isFile() && file.getName().endsWith(".zs")) {
				contentsList.add(file);
			}
		}
	}
}
