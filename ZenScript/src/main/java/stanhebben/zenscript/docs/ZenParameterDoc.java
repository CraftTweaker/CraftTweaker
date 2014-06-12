/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Type;

/**
 *
 * @author Stan
 */
public class ZenParameterDoc {
	private boolean optional;
	private final String name;
	private final Type type;
	
	public ZenParameterDoc(Parameter parameter) {
		optional = false;
		name = parameter.name();
		type = parameter.type();
		
		for (AnnotationDesc annotation : parameter.annotations()) {
			if (annotation.annotationType().qualifiedName().equals("stanhebben.zenscript.annotations.Optional")) {
				optional = true;
			}
		}
	}

	public boolean isOptional() {
		return optional;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}
}
