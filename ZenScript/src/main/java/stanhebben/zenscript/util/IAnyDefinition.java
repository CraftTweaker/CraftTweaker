/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.util;

import org.objectweb.asm.ClassVisitor;

/**
 *
 * @author Stan
 */
public interface IAnyDefinition {
	public void defineMembers(ClassVisitor output);
	
	public void defineStaticCanCastImplicit(MethodOutput output);
	
	public void defineStaticAs(MethodOutput output);
	
	public void defineNot(MethodOutput output);
	
	public void defineNeg(MethodOutput output);
	
	public void defineAdd(MethodOutput output);
	
	public void defineSub(MethodOutput output);
	
	public void defineCat(MethodOutput output);
	
	public void defineMul(MethodOutput output);
	
	public void defineDiv(MethodOutput output);
	
	public void defineMod(MethodOutput output);
	
	public void defineAnd(MethodOutput output);
	
	public void defineOr(MethodOutput output);
	
	public void defineXor(MethodOutput output);
	
	public void defineRange(MethodOutput output);
	
	public void defineCompareTo(MethodOutput output);
	
	public void defineContains(MethodOutput output);
	
	public void defineMemberGet(MethodOutput output);
	
	public void defineMemberSet(MethodOutput output);
	
	public void defineMemberCall(MethodOutput output);
	
	public void defineIndexGet(MethodOutput output);
	
	public void defineIndexSet(MethodOutput output);
	
	public void defineCall(MethodOutput output);
	
	public void defineAsBool(MethodOutput output);
	
	public void defineAsByte(MethodOutput output);
	
	public void defineAsShort(MethodOutput output);
	
	public void defineAsInt(MethodOutput output);
	
	public void defineAsLong(MethodOutput output);
	
	public void defineAsFloat(MethodOutput output);
	
	public void defineAsDouble(MethodOutput output);
	
	public void defineAsString(MethodOutput output);
	
	public void defineAs(MethodOutput output);
	
	public void defineIs(MethodOutput output);
	
	public void defineGetNumberType(MethodOutput output);
	
	public void defineIteratorSingle(MethodOutput output);
	
	public void defineIteratorMulti(MethodOutput output);
	
	public void defineEquals(MethodOutput output);
	
	public void defineHashCode(MethodOutput output);
}
