/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.iterator;

import java.util.Iterator;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import stanhebben.zenscript.type.IZenIterator;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;

/**
 *
 * @author Stan
 */
public class IteratorIterable implements IZenIterator {
	private final MethodOutput methodOutput;
	private final ZenType iteratorType;
	private int iterator;

	public IteratorIterable(MethodOutput methodOutput, ZenType iteratorType) {
		this.methodOutput = methodOutput;
		this.iteratorType = iteratorType;
	}

	@Override
	public void compileStart(int[] locals) {
		iterator = methodOutput.local(Type.getType(Iterator.class));
		methodOutput.invokeInterface(Iterable.class, "iterator", Iterator.class);
		methodOutput.storeObject(iterator);
	}

	@Override
	public void compilePreIterate(int[] locals, Label exit) {
		methodOutput.loadObject(iterator);
		methodOutput.invokeInterface(
				Iterator.class,
				"hasNext",
				boolean.class);
		methodOutput.ifEQ(exit);

		methodOutput.loadObject(iterator);
		methodOutput.invokeInterface(Iterator.class, "next", Object.class);
		methodOutput.checkCast(iteratorType.toASMType().getInternalName());
		methodOutput.store(iteratorType.toASMType(), locals[0]);
	}

	@Override
	public void compilePostIterate(int[] locals, Label exit, Label repeat) {
		methodOutput.goTo(repeat);
	}

	@Override
	public ZenType getType(int i) {
		return iteratorType;
	}

	@Override
	public void compileEnd() {
		methodOutput.pop();
	}
}
