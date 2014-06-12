/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.MethodDoc;
import stanhebben.zenscript.annotations.OperatorType;

/**
 *
 * @author Stan
 */
public class ZenOperatorDoc {
	private final String operator;
	private final MethodDoc method;
	
	public ZenOperatorDoc(String operator, MethodDoc method) {
		this.operator = operator;
		this.method = method;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public MethodDoc getMethod() {
		return method;
	}
}
