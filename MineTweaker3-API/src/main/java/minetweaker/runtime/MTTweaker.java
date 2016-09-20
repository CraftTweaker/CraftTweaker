/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.runtime.providers.ScriptProviderMemory;
import stanhebben.zenscript.ZenModule;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

import static stanhebben.zenscript.ZenModule.compileScripts;
import static stanhebben.zenscript.ZenModule.extractClassName;

/**
 * 
 * 
 * @author Stan Hebben
 */
public class MTTweaker implements ITweaker {
	private static final boolean DEBUG = false;

	private final List<IUndoableAction> actions = new ArrayList<IUndoableAction>();
	private final Set<IUndoableAction> wereStuck = new LinkedHashSet<IUndoableAction>();
	private final Map<Object, IUndoableAction> stuckOverridable = new HashMap<Object, IUndoableAction>();

	private IScriptProvider scriptProvider;
	private byte[] scriptData;

	@Override
	public byte[] getStagedScriptData() {
		return ScriptProviderMemory.collect(scriptProvider);
	}

	@Override
	public void apply(IUndoableAction action) {
		MineTweakerAPI.logInfo(action.describe());

		Object overrideKey = action.getOverrideKey();
		if (wereStuck.contains(action)) {
			wereStuck.remove(action);

			if (overrideKey != null) {
				stuckOverridable.remove(overrideKey);
			}
		} else {
			if (overrideKey != null && stuckOverridable.containsKey(overrideKey)) {
				wereStuck.remove(stuckOverridable.get(overrideKey));
				stuckOverridable.remove(overrideKey);
			}

			action.apply();
		}

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
				MineTweakerAPI.logInfo(action.describeUndo());
				action.undo();
			} else {
				MineTweakerAPI.logInfo("[Stuck] 1 " + action.describe());
                MineTweakerAPI.logInfo("[Stuck] 2 " + action.describeUndo());
                MineTweakerAPI.logInfo("[Stuck] 3 " + action.toString());

                stuck.add(0, action);
				wereStuck.add(action);

				Object overrideKey = action.getOverrideKey();
				if (overrideKey != null) {
					stuckOverridable.put(overrideKey, action);
				}
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
		System.out.println("Loading scripts");

		scriptData = ScriptProviderMemory.collect(scriptProvider);
		Set<String> executed = new HashSet<String>();

		Iterator<IScriptIterator> scriptIterators = scriptProvider.getScriptIterators();
		while (scriptIterators.hasNext()) {
			IScriptIterator scriptIterator = scriptIterators.next();
			String groupName = scriptIterator.getGroupName();

			if (!executed.contains(groupName)) {
				executed.add(groupName);

				Map<String, byte[]> classes = new HashMap<String, byte[]>();
				IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);

				List<ZenParsedFile> files = new ArrayList<ZenParsedFile>();

				while (scriptIterator.next()) {
					Reader reader = null;
					try {
						reader = new InputStreamReader(new BufferedInputStream(scriptIterator.open()), "UTF-8");

						String filename = scriptIterator.getName();
						String className = extractClassName(filename);

						ZenParsedFile pfile = new ZenParsedFile(filename, className, reader, environmentGlobal);
						files.add(pfile);
					} catch (IOException ex) {
						MineTweakerAPI.logError("Could not load script " + scriptIterator.getName() + ": " + ex.getMessage());
					} catch (ParseException ex) {
						MineTweakerAPI.logError("Error parsing " + ex.getFile().getFileName() + ":" + ex.getLine() + " -- " + ex.getExplanation());
					} catch (Exception ex) {
						MineTweakerAPI.logError("Error loading " + scriptIterator.getName() + ": " + ex.toString(), ex);
					}

					if (reader != null) {
						try {
							reader.close();
						} catch (IOException ignored) {
						}
					}
				}

				try {
					List<String> s = new ArrayList<String>();
					for(ZenParsedFile pf : files) {
						s.add(pf.getClassName());
					}
//					MineTweakerAPI.logInfo("MineTweaker: Loading " + groupName);
//					MineTweakerAPI.logInfo("MineTweaker: Classes " + String.join("; ", s));
					compileScripts(groupName, files, environmentGlobal, DEBUG);

					// execute scripts
					ZenModule module = new ZenModule(classes, MineTweakerAPI.class.getClassLoader());
					module.getMain().run();
				} catch (Throwable ex) {
					MineTweakerAPI.logError("Error executing " + groupName + ": " + ex.getMessage(), ex);
				}
			}
		}

		if (wereStuck.size() > 0) {
			MineTweakerAPI.logWarning(Integer.toString(wereStuck.size()) + " modifications were stuck");
			for (IUndoableAction action : wereStuck) {
				MineTweakerAPI.logInfo("Stuck: " + action.describe());
			}
		}
	}

	@Override
	public byte[] getScriptData() {
		return scriptData;
	}
}
