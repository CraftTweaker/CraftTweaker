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
public class AnyIntRange implements IAny {
	private IntRange value;
	
	public AnyIntRange(IntRange value) {
		this.value = value;
	}

	@Override
	public IAny not() {
		throw new ZenRuntimeException("Cannot invert an int range");
	}

	@Override
	public IAny neg() {
		throw new ZenRuntimeException("Cannot negate an int range");
	}

	@Override
	public IAny add(IAny value) {
		throw new ZenRuntimeException("Cannot add int ranges");
	}

	@Override
	public IAny sub(IAny value) {
		throw new ZenRuntimeException("Cannot subtract int ranges");
	}

	@Override
	public IAny cat(IAny value) {
		throw new ZenRuntimeException("Cannot concatenate int ranges");
	}

	@Override
	public IAny mul(IAny value) {
		throw new ZenRuntimeException("Cannot multiply int ranges");
	}

	@Override
	public IAny div(IAny value) {
		throw new ZenRuntimeException("Cannot divide int ranges");
	}

	@Override
	public IAny mod(IAny value) {
		throw new ZenRuntimeException("Cannot perform modulo on int ranges");
	}

	@Override
	public IAny and(IAny value) {
		throw new ZenRuntimeException("Cannot perform and on int ranges");
	}

	@Override
	public IAny or(IAny value) {
		throw new ZenRuntimeException("Cannot perform or on int ranges");
	}

	@Override
	public IAny xor(IAny value) {
		throw new ZenRuntimeException("Cannot perform xor on int ranges");
	}

	@Override
	public IAny range(IAny value) {
		throw new ZenRuntimeException("Already a range!");
	}

	@Override
	public int compareTo(IAny value) {
		throw new ZenRuntimeException("Cannot compare ranges");
	}

	@Override
	public boolean contains(IAny value) {
		int ivalue = value.asInt();
		return ivalue >= this.value.getFrom() && ivalue < this.value.getTo();
	}

	@Override
	public IAny member(String value) {
		if (value.equals("from")) {
			return new AnyInt(this.value.getFrom());
		} else if (value.equals("to")) {
			return new AnyInt(this.value.getTo());
		} else {
			throw new ZenRuntimeException("No member " + value + " in int range");
		}
	}

	@Override
	public IAny indexGet(IAny key) {
		int ivalue = key.asInt();
		if (ivalue < value.getTo() - value.getFrom()) {
			return new AnyInt(this.value.getFrom() + ivalue);
		} else {
			throw new ArrayIndexOutOfBoundsException("int range index out of bounds: " + ivalue);
		}
	}

	@Override
	public void indexSet(IAny key, IAny value) {
		throw new ZenRuntimeException("Cannot modify int ranges");
	}

	@Override
	public IAny call(IAny... values) {
		throw new ZenRuntimeException("Cannot call int ranges");
	}

	@Override
	public boolean asBool() {
		throw new ZenRuntimeException("Cannot cast int range to bool");
	}

	@Override
	public byte asByte() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public short asShort() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int asInt() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public long asLong() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public float asFloat() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public double asDouble() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String asString() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T as(Class<T> cls) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Iterator<IAny> iteratorSingle() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Iterator<IAny[]> iteratorMulti(int n) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
