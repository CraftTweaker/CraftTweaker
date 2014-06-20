/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.Type;

/**
 *
 * @author Stan
 */
public class ZenPropertyDoc {
	private Type type;
	private String name;
	private boolean canGet;
	private boolean canSet;
	private String[] comment;
	
	public ZenPropertyDoc(String name, Type type) {
		this.type = type;
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}

	public boolean isCanGet() {
		return canGet;
	}

	public void setCanGet(boolean canGet) {
		this.canGet = canGet;
	}

	public boolean isCanSet() {
		return canSet;
	}

	public void setCanSet(boolean canSet) {
		this.canSet = canSet;
	}

	public String[] getComment() {
		return comment;
	}

	public void setComment(String[] comment) {
		this.comment = comment;
	}
}
