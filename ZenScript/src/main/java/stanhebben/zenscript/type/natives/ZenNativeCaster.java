/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.util.MethodOutput;

/**
 *
 * @author Stanneke
 */
public class ZenNativeCaster {
	private final Class owner;
	private final Class output;
	private final String methodName;
	
	public ZenNativeCaster(Class owner, Class output, String methodName) {
		this.owner = owner;
		this.output = output;
		this.methodName = methodName;
	}
	
	public Class getCasterClass() {
		return output;
	}
	
	public void compile(MethodOutput output) {
		output.invoke(owner, methodName, this.output);
	}
}
