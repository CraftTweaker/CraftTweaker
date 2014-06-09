/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.value.builtin;

import java.util.Iterator;
import stanhebben.zenscript.ZenRuntimeException;
import stanhebben.zenscript.value.IAny;

/**
 *
 * @author Stanneke
 */
public class AnyByte implements IAny {
	public static AnyByte valueOf(byte value) {
		return new AnyByte(value);
	}
	
	private final byte value;
	
	public AnyByte(byte value) {
		this.value = value;
	}

	@Override
	public IAny not() {
		return new AnyByte((byte) ~value);
	}

	@Override
	public IAny neg() {
		return new AnyByte((byte) -value);
	}

	@Override
	public IAny add(IAny value) {
		return new AnyByte((byte) (this.value + value.asByte()));
	}

	@Override
	public IAny sub(IAny value) {
		return new AnyByte((byte) (this.value - value.asByte()));
	}

	@Override
	public IAny cat(IAny value) {
		return new AnyString(Byte.toString(this.value) + value.asString());
	}

	@Override
	public IAny mul(IAny value) {
		return new AnyByte((byte) (this.value * value.asByte()));
	}

	@Override
	public IAny div(IAny value) {
		return new AnyByte((byte) (this.value / value.asByte()));
	}

	@Override
	public IAny mod(IAny value) {
		return new AnyByte((byte) (this.value % value.asByte()));
	}

	@Override
	public IAny and(IAny value) {
		return new AnyByte((byte) (this.value & value.asByte()));
	}

	@Override
	public IAny or(IAny value) {
		return new AnyByte((byte) (this.value | value.asByte()));
	}

	@Override
	public IAny xor(IAny value) {
		return new AnyByte((byte) (this.value ^ value.asByte()));
	}

	@Override
	public IAny range(IAny value) {
		//return new AnyIntRange(this.value, value.asInt());
		return null; // TODO
	}

	@Override
	public int compareTo(IAny value) {
		return Integer.compare(this.value, value.asByte());
	}

	@Override
	public boolean contains(IAny value) {
		throw new ZenRuntimeException("byte has no in operator");
	}

	@Override
	public IAny member(String value) {
		if (value.equals("abs")) {
			return new AnyByte((byte) (Math.abs(this.value)));
		} else {
			throw new ZenRuntimeException("no such member in byte: " + value);
		}
	}

	@Override
	public IAny indexGet(IAny key) {
		throw new ZenRuntimeException("cannot index a byte value");
	}

	@Override
	public void indexSet(IAny key, IAny value) {
		throw new ZenRuntimeException("cannot index a byte value");
	}

	@Override
	public IAny call(IAny... values) {
		throw new ZenRuntimeException("cannot index a byte value");
	}

	@Override
	public boolean asBool() {
		throw new ZenRuntimeException("cannot convert a byte to a bool");
	}

	@Override
	public byte asByte() {
		return value;
	}

	@Override
	public short asShort() {
		return value;
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
		return Byte.toString(value);
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
