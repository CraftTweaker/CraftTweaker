/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Type;
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
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.ADD", ADD);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.SUB", SUB);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MUL", MUL);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.DIV", DIV);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MOD", MOD);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.CAT", CAT);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.OR", OR);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.AND", AND);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.XOR", XOR);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.NEG", NEG);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.NOT", NOT);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.INDEXSET", INDEXSET);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.INDEXGET", INDEXGET);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.RANGE", RANGE);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.CONTAINS", CONTAINS);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.COMPARE", COMPARE);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MEMBERGETTER", MEMBERGETTER);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.MEMBERSETTER", MEMBERSETTER);
		TYPES.put("stanhebben.zenscript.annotations.OperatorType.EQUALS", EQUALS);
	}
	
	private final String operator;
	private final MethodDoc method;
	private final Type[] types;
	
	public ZenOperatorDoc(String operator, MethodDoc method, Type[] types) {
		this.operator = operator;
		this.method = method;
		this.types = types;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public Type[] getTypes() {
		return types;
	}
	
	public ZenOperatorDocType getOperatorType() {
		return TYPES.get(operator);
	}
	
	public MethodDoc getMethod() {
		return method;
	}
}
