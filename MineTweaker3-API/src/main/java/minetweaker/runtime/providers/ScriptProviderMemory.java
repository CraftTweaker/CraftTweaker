/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime.providers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import minetweaker.MineTweakerAPI;
import minetweaker.minecraft.util.FileUtil;
import minetweaker.runtime.IScriptIterator;
import minetweaker.runtime.IScriptProvider;

/**
 *
 * @author Stan
 */
public class ScriptProviderMemory {
	public static byte[] collect(Iterable<IScriptProvider> providers) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			DeflaterOutputStream deflater = new DeflaterOutputStream(output);
			DataOutputStream deflaterData = new DataOutputStream(deflater);
			Set<String> executed = new HashSet<String>();
			
			for (IScriptProvider provider : providers) {
				Iterator<IScriptIterator> scripts = provider.getScripts();
				while (scripts.hasNext()) {
					IScriptIterator script = scripts.next();
					
					if (!executed.contains(script.getGroupName())) {
						executed.add(script.getGroupName());
						
						deflaterData.writeUTF(script.getGroupName());
						
						while (script.next()) {
							String name = script.getName();
							byte[] data = FileUtil.read(script.open());
							deflaterData.writeUTF(name);
							deflaterData.writeInt(data.length);
							deflaterData.write(data);
						}
						
						deflaterData.writeUTF("");
					}
				}
			}
			
			deflaterData.writeUTF("");
			
			deflater.close();
		} catch (IOException ex) {
			MineTweakerAPI.logger.logError("Could not collect scripts: " + ex.getMessage());
		}
		return output.toByteArray();
	}
	
	public ScriptProviderMemory(byte[] scripts) {
		try {
			InflaterInputStream inflater = new InflaterInputStream(new ByteArrayInputStream(scripts));
			DataInputStream inflaterData = new DataInputStream(inflater);

			String moduleName = inflaterData.readUTF();
			while (moduleName.length() > 0) {
				List<MemoryFile> files = new ArrayList<MemoryFile>();
				
				String fileName = inflaterData.readUTF();
				while (fileName.length() > 0) {
					byte[] data = new byte[inflaterData.readInt()];
					inflaterData.readFully(data);
					files.add(new MemoryFile(fileName, data));
					
					fileName = inflaterData.readUTF();
				}
				
				moduleName = inflaterData.readUTF();
			}
			
			inflaterData.close();
		} catch (IOException ex) {
			MineTweakerAPI.logger.logError("Could not load transmitted scripts: " + ex.getMessage());
		}
	}
	
	private class MemoryFile {
		private final String name;
		private final byte[] data;
		
		public MemoryFile(String name, byte[] data) {
			this.name = name;
			this.data = data;
		}
	}
}
