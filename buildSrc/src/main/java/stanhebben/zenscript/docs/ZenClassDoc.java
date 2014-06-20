/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.ClassDoc;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stan
 */
public class ZenClassDoc {
	private final ClassDoc cls;
	private final String name;
	private final List<ZenExpansionDoc> expansions;
	
	public ZenClassDoc(ClassDoc cls, String name) {
		this.cls = cls;
		this.name = name;
		expansions = new ArrayList<ZenExpansionDoc>();
	}
	
	public String getName() {
		return name;
	}
	
	public ClassDoc getDoc() {
		return cls;
	}
	
	public void addExpansion(ZenExpansionDoc expansion) {
		expansions.add(expansion);
	}
	
	public List<ZenExpansionDoc> getExpansions() {
		return expansions;
	}
}
