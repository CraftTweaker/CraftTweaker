/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.annotations.OperatorType;

/**
 *
 * @author Stanneke
 */
public class ZenNativeOperator {
	private final OperatorType operator;
	private final JavaMethod method;
	
	public ZenNativeOperator(OperatorType operator, JavaMethod method) {
		this.operator = operator;
		this.method = method;
	}
	
	public OperatorType getOperator() {
		return operator;
	}
	
	public JavaMethod getMethod() {
		return method;
	}
}
