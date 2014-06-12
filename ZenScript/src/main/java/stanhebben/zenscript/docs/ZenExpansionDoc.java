/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.ClassDoc;

/**
 *
 * @author Stan
 */
public class ZenExpansionDoc {
	private final ClassDoc cls;
	private final String type;
	
	public ZenExpansionDoc(ClassDoc cls, String type) {
		this.cls = cls;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public ClassDoc getDoc() {
		return cls;
	}
}
