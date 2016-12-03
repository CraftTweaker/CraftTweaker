package minetweaker.mc11.providers;

import minetweaker.runtime.*;
import minetweaker.runtime.providers.*;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.util.*;

public class ScriptProviderModOnly implements IScriptProvider {
	
	private final File directory;
	
	public ScriptProviderModOnly(File directory) {
		if(directory == null)
			throw new IllegalArgumentException("directory cannot be null");
		
		this.directory = directory;
	}
	
	@Override
	public Iterator<IScriptIterator> getScripts() {
		List<IScriptIterator> scripts = new ArrayList<>();
		if(directory.exists()) {
			for(File file : directory.listFiles()) {
				if(file.isDirectory()) {
					scripts.add(new ScriptIteratorDirectoryModOnly(file));
				} else if(file.getName().endsWith(".zsm") && Loader.isModLoaded(file.getName().substring(0, file.getName().length() - ".zsm".length()))) {
					scripts.add(new ScriptIteratorSingle(file));
				}
			}
		}
		if(scripts.size() > 1)
			scripts.sort((sc, sc1) -> {
				if(sc == null || sc1 == null) {
					return -1;
				}
				return sc.getName().compareTo(sc1.getName());
			});
		return scripts.iterator();
	}
	
}
