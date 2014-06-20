/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.minecraft.item.IIngredient;
import minetweaker.runtime.providers.ScriptProviderMemory;
import stanhebben.zenscript.ZenModule;
import static stanhebben.zenscript.ZenModule.compileScripts;
import static stanhebben.zenscript.ZenModule.extractClassName;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.ParseException;

/**
 * 
 * 
 * @author Stan Hebben
 */
public class Tweaker implements IMineTweaker {
	private final List<IUndoableAction> actions = new ArrayList<IUndoableAction>();
	private final Set<IUndoableAction> wereStuck = new HashSet<IUndoableAction>();
	
	private IScriptProvider scriptProvider;
	private byte[] scriptData;
	
	@Override
	public void apply(IUndoableAction action) {
		MineTweakerAPI.logger.logInfo(action.describe());
		action.apply();
		actions.add(action);
	}

	@Override
	public void remove(IIngredient items) {
		GlobalRegistry.remove(items);
	}

	@Override
	public List<IUndoableAction> rollback() {
		List<IUndoableAction> stuck = new ArrayList<IUndoableAction>();
		for (int i = actions.size() - 1; i >= 0; i--) {
			IUndoableAction action = actions.get(i);
			if (action.canUndo()) {
				MineTweakerAPI.logger.logInfo(action.describeUndo());
				action.undo();
			} else {
				stuck.add(0, action);
				wereStuck.add(action);
			}
		}
		actions.clear();
		return stuck;
	}

	@Override
	public void setScriptProvider(IScriptProvider provider) {
		scriptProvider = provider;
	}

	@Override
	public void load() {
		scriptData = ScriptProviderMemory.collect(scriptProvider);
		Set<String> executed = new HashSet<String>();

		Iterator<IScriptIterator> scripts = scriptProvider.getScripts();
		while (scripts.hasNext()) {
			IScriptIterator script = scripts.next();

			if (!executed.contains(script.getGroupName())) {
				executed.add(script.getGroupName());

				Map<String, byte[]> classes = new HashMap<String, byte[]>();
				IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);

				List<ZenParsedFile> files = new ArrayList<ZenParsedFile>();

				while (script.next()) {
					Reader reader = null;
					try {
						reader = new InputStreamReader(new BufferedInputStream(script.open()));
						
						String filename = script.getName();
						String className = extractClassName(filename);
						
						ZenTokener parser = new ZenTokener(reader, environmentGlobal.getEnvironment());
						ZenParsedFile pfile = new ZenParsedFile(filename, className, parser, environmentGlobal);
						files.add(pfile);
					} catch (IOException ex) {
						MineTweakerAPI.logger.logError("Could not load script " + script.getName() + ": " + ex.getMessage());
					} catch (ParseException ex) {
						MineTweakerAPI.logger.logError("Error parsing " + ex.getFile() + ":" + ex.getLine() + " -- " + ex.getExplanation());
					} catch (Exception ex) {
						MineTweakerAPI.logger.logError("Error loading " + script.getName() + ": " + ex.getMessage());
						ex.printStackTrace();
					}
					
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException ex) {}
					}
				}

				try {
					String filename = script.getGroupName();
					System.out.println("MineTweaker: Loading " + filename);
					compileScripts(filename, files, environmentGlobal);

					// execute scripts
					ZenModule module = new ZenModule(classes, MineTweakerAPI.class.getClassLoader());
					module.getMain().run();
				} catch (Exception ex) {
					MineTweakerAPI.logger.logError("Error executing " + script.getGroupName() + ": " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		
		if (wereStuck.size() > 0) {
			MineTweakerAPI.logger.logWarning(Integer.toString(wereStuck.size()) + " modifications were stuck");
			for (IUndoableAction action : wereStuck) {
				MineTweakerAPI.logger.logInfo("Stuck: " + action.describe());
			}
		}
	}

	@Override
	public byte[] getScriptData() {
		return scriptData;
	}
}
