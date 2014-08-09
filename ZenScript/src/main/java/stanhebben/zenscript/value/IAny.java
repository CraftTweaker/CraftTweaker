/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.value;

import java.util.Iterator;

/**
 *
 * @author Stan Hebben
 */
public interface IAny {
	public static final String NAME = "stanhebben/zenscript/value/IAny";
	
	public static final int NUM_BYTE = 1;
	public static final int NUM_SHORT = 2;
	public static final int NUM_INT = 3;
	public static final int NUM_LONG = 4;
	public static final int NUM_FLOAT = 5;
	public static final int NUM_DOUBLE = 6;
	
	public IAny not();
	
	public IAny neg();
	
	public IAny add(IAny value);
	
	public IAny sub(IAny value);
	
	public IAny cat(IAny value);
	
	public IAny mul(IAny value);
	
	public IAny div(IAny value);
	
	public IAny mod(IAny value);
	
	public IAny and(IAny value);
	
	public IAny or(IAny value);
	
	public IAny xor(IAny value);
	
	public IAny range(IAny value);
	
	public int compareTo(IAny value);
	
	public boolean contains(IAny value);
	
	public IAny memberGet(String member);
	
	public void memberSet(String member, IAny value);
	
	public IAny memberCall(String member, IAny... values);
	
	public IAny indexGet(IAny key);
	
	public void indexSet(IAny key, IAny value);
	
	public IAny call(IAny... values);
	
	public boolean asBool();
	
	public byte asByte();
	
	public short asShort();
	
	public int asInt();
	
	public long asLong();
	
	public float asFloat();
	
	public double asDouble();
	
	public String asString();
	
	public <T> T as(Class<T> cls);
	
	public boolean is(Class<?> cls);
	
	public boolean canCastImplicit(Class<?> cls);
	
	public int getNumberType();
	
	public Iterator<IAny> iteratorSingle();
	
	public Iterator<IAny[]> iteratorMulti(int n);
}
