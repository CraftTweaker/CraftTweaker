/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.definitions;

import java.util.List;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class Import {
	private final ZenPosition position;
	private final List<String> name;
	private final String rename;
	
	public Import(ZenPosition position, List<String> name, String rename) {
		this.position = position;
		this.name = name;
		this.rename = rename;
	}
	
	public ZenPosition getPosition() {
		return position;
	}
	
	public List<String> getName() {
		return name;
	}
	
	public String getRename() {
		return rename;
	}
}
