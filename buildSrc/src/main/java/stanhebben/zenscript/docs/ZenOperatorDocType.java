/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.docs;

/**
 *
 * @author Stan
 */
public enum ZenOperatorDocType {
	ADD("+ %s"),
	SUB("- %s"),
	MUL("* %s"),
	DIV("/ %s"),
	MOD("%% %s"),
	CAT("~ %s"),
	OR("| %s"),
	AND("& %s"),
	XOR("^ %s"),
	NEG("-"),
	NOT("~"),
	INDEXSET("[%s] = %s"),
	INDEXGET("[%s]"),
	RANGE(".. %s"),
	CONTAINS("%s in"),
	COMPARE("compareTo(%s)"),
	MEMBERGETTER(".member"),
	MEMBERSETTER(".member = %s"),
	EQUALS("== %s");
	
	private final String pattern;
	
	private ZenOperatorDocType(String pattern) {
		this.pattern = pattern;
	}
	
	public String fill(String... contents) {
		return String.format(pattern, (Object[]) contents);
	}
}
