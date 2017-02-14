/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime.providers;

import minetweaker.runtime.IScriptIterator;
import minetweaker.runtime.IScriptProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 *
 * @author Stan
 */
public class ScriptProviderCustom implements IScriptProvider {
	private static final Charset UTF8 = Charset.forName("UTF-8");

	private final String moduleName;
	private final List<CustomScript> scripts;

	public ScriptProviderCustom(String moduleName) {
		this.moduleName = moduleName;
		scripts = new ArrayList<>();
	}

	public void add(String name, byte[] content) {
		scripts.add(new CustomScript(name, content));
	}

	public void add(String name, String content) {
		add(name, content.getBytes(UTF8));
	}

	@Override
	public Iterator<IScriptIterator> getScripts() {

		return Collections.<IScriptIterator>singleton(new CustomScriptIterator()).iterator();
	}

	private class CustomScript {
		private final String name;
		private final byte[] content;

		public CustomScript(String name, byte[] content) {
			this.name = name;
			this.content = content;
		}
		
		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("CustomScript{");
			sb.append("name='").append(name).append('\'');
			sb.append(", content=").append(Arrays.toString(content));
			sb.append('}');
			return sb.toString();
		}
	}

	private class CustomScriptIterator implements IScriptIterator {
		private CustomScript current;
		private final Iterator<CustomScript> iterator = scripts.iterator();

		@Override
		public String getGroupName() {
			return moduleName;
		}

		@Override
		public boolean next() {
			if (iterator.hasNext()) {
				current = iterator.next();
				return true;
			} else {
				return false;
			}
		}

		@Override
		public String getName() {
			return current.name;
		}

		@Override
		public InputStream open() throws IOException {
			return new ByteArrayInputStream(current.content);
		}
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ScriptProviderCustom{");
		sb.append("moduleName='").append(moduleName).append('\'');
		sb.append(", scripts=").append(scripts);
		sb.append('}');
		return sb.toString();
	}
}
