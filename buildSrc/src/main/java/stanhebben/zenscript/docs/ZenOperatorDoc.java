/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.MethodDoc;
import java.util.HashMap;
import java.util.Map;
import static stanhebben.zenscript.docs.ZenOperatorDocType.*;

/**
 *
 * @author Stan
 */
public class ZenOperatorDoc {
	private static Map<String, ZenOperatorDocType> TYPES;
	
	static {
		TYPES = new HashMap<String, ZenOperatorDocType>();
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
	}
	
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
