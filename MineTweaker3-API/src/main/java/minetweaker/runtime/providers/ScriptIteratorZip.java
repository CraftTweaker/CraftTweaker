/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime.providers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import minetweaker.runtime.IScriptIterator;

/**
 *
 * @author Stan
 */
public class ScriptIteratorZip implements IScriptIterator {
	private final File file;
	private final ZipFile zipFile;
	private final Iterator<ZipEntry> entries;
	private ZipEntry current;

	public ScriptIteratorZip(File file) throws IOException {
		this.file = file;

		zipFile = new ZipFile(file);
		List<ZipEntry> entriesList = new ArrayList<ZipEntry>();
		Enumeration<? extends ZipEntry> original = zipFile.entries();
		while (original.hasMoreElements()) {
			ZipEntry entry = original.nextElement();
			if (entry.getName().startsWith("scripts/") && entry.getName().endsWith(".zs")) {
				entriesList.add(entry);
			}
		}

		entries = entriesList.iterator();
	}

	@Override
	public String getGroupName() {
		return file.getName().substring(0, file.getName().lastIndexOf('.'));
	}

	@Override
	public boolean next() {
		if (entries.hasNext()) {
			current = entries.next();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return current.getName().substring("scripts/".length());
	}

	@Override
	public InputStream open() throws IOException {
		return zipFile.getInputStream(current);
	}
}
