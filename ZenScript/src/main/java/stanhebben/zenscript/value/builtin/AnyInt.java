/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.value.builtin;

import java.util.Iterator;
import stanhebben.zenscript.ZenRuntimeException;
import stanhebben.zenscript.value.IAny;
import stanhebben.zenscript.value.IntRange;

/**
 *
 * @author Stanneke
 */
public class AnyInt implements IAny {
	public static AnyInt valueOf(byte value) {
		return new AnyInt(value);
	}
	
	private final int value;
	
	public AnyInt(int value) {
		this.value = value;
	}

	@Override
	public IAny not() {
		return new AnyInt(~value);
	}

	@Override
	public IAny neg() {
		return new AnyInt(-value);
	}

	@Override
	public IAny add(IAny value) {
		return new AnyInt(this.value + value.asInt());
	}

	@Override
	public IAny sub(IAny value) {
		return new AnyInt(this.value - value.asInt());
	}

	@Override
	public IAny cat(IAny value) {
		return new AnyString(Integer.toString(this.value) + value.asString());
	}

	@Override
	public IAny mul(IAny value) {
		return new AnyInt(this.value * value.asByte());
	}

	@Override
	public IAny div(IAny value) {
		return new AnyInt(this.value / value.asByte());
	}

	@Override
	public IAny mod(IAny value) {
		return new AnyInt(this.value % value.asByte());
	}

	@Override
	public IAny and(IAny value) {
		return new AnyInt(this.value & value.asByte());
	}

	@Override
	public IAny or(IAny value) {
		return new AnyInt(this.value | value.asByte());
	}

	@Override
	public IAny xor(IAny value) {
		return new AnyInt(this.value ^ value.asByte());
	}

	@Override
	public IAny range(IAny value) {
		return new AnyIntRange(new IntRange(this.value, value.asInt()));
	}

	@Override
	public int compareTo(IAny value) {
		return Integer.compare(this.value, value.asByte());
	}

	@Override
	public boolean contains(IAny value) {
		throw new ZenRuntimeException("int has no in operator");
	}

	@Override
	public IAny member(String value) {
		if (value.equals("abs")) {
			return new AnyInt(Math.abs(this.value));
		} else {
			throw new ZenRuntimeException("no such member in int: " + value);
		}
	}

	@Override
	public IAny indexGet(IAny key) {
		throw new ZenRuntimeException("cannot index an int value");
	}

	@Override
	public void indexSet(IAny key, IAny value) {
		throw new ZenRuntimeException("cannot index an int value");
	}

	@Override
	public IAny call(IAny... values) {
		throw new ZenRuntimeException("cannot call an int value");
	}

	@Override
	public boolean asBool() {
		throw new ZenRuntimeException("cannot convert an int to a bool");
	}

	@Override
	public byte asByte() {
		return (byte) value;
	}

	@Override
	public short asShort() {
		return (short) value;
	}

	@Override
	public int asInt() {
		return value;
	}

	@Override
	public long asLong() {
		return value;
	}

	@Override
	public float asFloat() {
		return value;
	}

	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public String asString() {
		return Integer.toString(value);
	}

	@Override
	public <T> T as(Class<T> cls) {
		throw new ZenRuntimeException("cannot convert a byte to an object");
	}

	@Override
	public Iterator<IAny> iteratorSingle() {
		throw new ZenRuntimeException("cannot iterate over a byte value");
	}

	@Override
	public Iterator<IAny[]> iteratorMulti(int n) {
		throw new ZenRuntimeException("cannot iterate over a byte value");
	}
}
