/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime.providers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.runtime.IScriptIterator;
import minetweaker.runtime.IScriptProvider;

/**
 *
 * @author Stan
 */
public class ScriptProviderDirectory implements IScriptProvider {
	private final File directory;

	public ScriptProviderDirectory(File directory) {
		if (directory == null)
			throw new IllegalArgumentException("directory cannot be null");

		this.directory = directory;
	}

	@Override
	public Iterator<IScriptIterator> getScriptIterators() {
		List<IScriptIterator> iterators = new ArrayList<IScriptIterator>();
		if (directory.exists()) {
			iterators.add(new ScriptIteratorDirectory(directory));
		}
		return iterators.iterator();
	}
}
