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
public class AnyString implements IAny {
	private final String value;
	
	public AnyString(String value) {
		this.value = value;
	}

	@Override
	public IAny not() {
		throw new ZenRuntimeException("cannot invert a string value");
	}

	@Override
	public IAny neg() {
		throw new ZenRuntimeException("cannot negate a string value");
	}

	@Override
	public IAny add(IAny value) {
		throw new ZenRuntimeException("cannot add to a string value (did you intend to use ~ instead?)");
	}

	@Override
	public IAny sub(IAny value) {
		throw new ZenRuntimeException("cannot subtract from a string");
	}

	@Override
	public IAny cat(IAny value) {
		return new AnyString(this.value + value.asString());
	}

	@Override
	public IAny mul(IAny value) {
		throw new ZenRuntimeException("cannot multiply a string value");
	}

	@Override
	public IAny div(IAny value) {
		throw new ZenRuntimeException("cannot divide a string value");
	}

	@Override
	public IAny mod(IAny value) {
		throw new ZenRuntimeException("cannot modulo a string value");
	}

	@Override
	public IAny and(IAny value) {
		throw new ZenRuntimeException("cannot and with a string value");
	}

	@Override
	public IAny or(IAny value) {
		throw new ZenRuntimeException("cannot or with a string value");
	}

	@Override
	public IAny xor(IAny value) {
		throw new ZenRuntimeException("cannot xor with a string value");
	}

	@Override
	public IAny range(IAny value) {
		throw new ZenRuntimeException("cannot range a string value");
	}

	@Override
	public int compareTo(IAny value) {
		return this.value.compareTo(value.asString());
	}

	@Override
	public boolean contains(IAny value) {
		return this.value.indexOf(value.asString()) >= 0;
	}

	@Override
	public IAny member(String value) {
		throw new ZenRuntimeException("strings have no members (yet)");
	}

	@Override
	public IAny indexGet(IAny key) {
		throw new ZenRuntimeException("cannot index a string (yet)");
	}

	@Override
	public void indexSet(IAny key, IAny value) {
		throw new ZenRuntimeException("cannot modify a string");
	}

	@Override
	public IAny call(IAny... values) {
		throw new ZenRuntimeException("cannot call a string");
	}

	@Override
	public boolean asBool() {
		return value != null;
	}

	@Override
	public byte asByte() {
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ex) {
			throw new ZenRuntimeException("Cannot convert " + value + " to a byte value");
		}
	}

	@Override
	public short asShort() {
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException ex) {
			throw new ZenRuntimeException("Cannot convert " + value + " to a short value");
		}
	}

	@Override
	public int asInt() {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			throw new ZenRuntimeException("Cannot convert " + value + " to an int value");
		}
	}

	@Override
	public long asLong() {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			throw new ZenRuntimeException("Cannot convert " + value + " to a long value");
		}
	}

	@Override
	public float asFloat() {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ex) {
			throw new ZenRuntimeException("Cannot convert " + value + " to a float value");
		}
	}

	@Override
	public double asDouble() {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			throw new ZenRuntimeException("Cannot convert " + value + " to a double value");
		}
	}

	@Override
	public String asString() {
		return value;
	}

	@Override
	public <T> T as(Class<T> cls) {
		throw new ZenRuntimeException("Cannot convert a string to an object");
	}

	@Override
	public Iterator<IAny> iteratorSingle() {
		throw new ZenRuntimeException("Cannot iterate over a string (yet)");
	}

	@Override
	public Iterator<IAny[]> iteratorMulti(int n) {
		throw new ZenRuntimeException("Cannot iterate over a string (yet)");
	}
}
