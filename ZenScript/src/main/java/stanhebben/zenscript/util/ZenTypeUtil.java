/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import stanhebben.zenscript.compiler.ITypeRegistry;
import stanhebben.zenscript.compiler.TypeRegistry;

/**
 *
 * @author Stanneke
 */
public class ZenTypeUtil {
	public static final ITypeRegistry EMPTY_REGISTRY = new TypeRegistry();
	
	private static final Map<String, String> SIGNATURE_MAP;
	
	static {
		SIGNATURE_MAP = new HashMap<String, String>();
		SIGNATURE_MAP.put(boolean.class.getName(), "Z");
		SIGNATURE_MAP.put(byte.class.getName(), "B");
		SIGNATURE_MAP.put(short.class.getName(), "S");
		SIGNATURE_MAP.put(int.class.getName(), "I");
		SIGNATURE_MAP.put(long.class.getName(), "J");
		SIGNATURE_MAP.put(float.class.getName(), "F");
		SIGNATURE_MAP.put(double.class.getName(), "D");
		SIGNATURE_MAP.put(char.class.getName(), "C");
		SIGNATURE_MAP.put(void.class.getName(), "V");
	}
	
	public static String signature(Class<?> cls) {
		if (SIGNATURE_MAP.containsKey(cls.getName())) {
			return SIGNATURE_MAP.get(cls.getName());
		} else if (cls.isArray()) {
			return "[" + signature(cls.getComponentType());
		} else {
			String signature = "L" + internal(cls) + ";";
			SIGNATURE_MAP.put(cls.getName(), signature);
			return signature;
		}
	}
	
	public static String internal(Class<?> cls) {
		return cls.getName().replace('.', '/');
	}
	
	public static String descriptor(Method method) {
		StringBuilder output = new StringBuilder();
		output.append('(');
		for (Class param : method.getParameterTypes()) {
			output.append(signature(param));
		}
		output.append(')').append(signature(method.getReturnType()));
		return output.toString();
	}
}
