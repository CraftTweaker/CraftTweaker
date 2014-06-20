/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.MethodDoc;

/**
 *
 * @author Stan
 */
public class ZenCasterDoc {
	private final MethodDoc method;
	
	public ZenCasterDoc(MethodDoc method) {
		this.method = method;
	}

	public MethodDoc getMethod() {
		return method;
	}
}
