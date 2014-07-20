package stanhebben.zenscript.type;

import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionArrayGet;
import stanhebben.zenscript.expression.ExpressionArrayLength;
import stanhebben.zenscript.expression.ExpressionArraySet;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

public class ZenTypeArrayBasic extends ZenTypeArray {
	private final Type asmType;
	
	public ZenTypeArrayBasic(ZenType base) {
		super(base);
		
		asmType = Type.getType("[" + base.toASMType().getDescriptor());
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof ZenTypeArrayBasic) {
			ZenTypeArrayBasic o = (ZenTypeArrayBasic) other;
			return o.getBaseType().equals(getBaseType());
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 23 * hash + (this.getBaseType() != null ? this.getBaseType().hashCode() : 0);
		return hash;
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (equals(type)) return value;
		
		return castExpansion(position, environment, value, type);
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		if (numValues == 1) {
			return new ValueIterator(methodOutput.getOutput());
		} else if (numValues == 2) {
			return new IndexValueIterator(methodOutput.getOutput());
		} else {
			return null;
		}
	}
	
	@Override
	public String getAnyClassName(IEnvironmentGlobal global) {
		return null;
	}

	@Override
	public Type toASMType() {
		return asmType;
	}

	@Override
	public Class toJavaClass() {
		try {
			return Class.forName("[" + getBaseType().toJavaClass().getName());
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String getSignature() {
		return "[" + getBaseType().getSignature();
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType toClass) {
		MethodOutput output = environment.getOutput();
		
		if (compileCastExpansion(position, environment, toClass)) {
			// OK
		} else if (toClass instanceof ZenTypeArrayList) {
			// convert array to list
			// Arrays.asList(value);
			output.invokeStatic(Arrays.class, "asList", List.class, Object[].class);
		} else if (toClass instanceof ZenTypeArrayBasic) {
			// convert elements
			if (toClass.equals(this)) {
				// do nothing
			} else {
				ZenType component = ((ZenTypeArrayBasic) toClass).getBaseType();
				Type componentType = component.toASMType();
				
				int result = output.local(componentType);
				
				output.dup();
				output.arrayLength();
				output.newArray(componentType);
				output.storeObject(result);
				
				output.iConst0();
				
				Label lbl = new Label();
				output.label(lbl);
				
				// stack: original index
				output.dupX1();
				output.dupX1();
				output.arrayLoad(componentType);
				
				// stack: original index value
				getBaseType().compileCast(position, environment, toClass);
				
				output.loadObject(result);
				output.dupX2();
				output.dupX2();
				output.arrayStore(componentType);
				output.pop();
				
				// stack: original index
				output.iConst1();
				output.iAdd();
				output.dupX1();
				output.arrayLength();
				output.ifICmpGE(lbl);
				
				output.pop();
				output.pop();
				
				output.loadObject(result);
			}
		} else {
			// TODO: error
		}
	}

	@Override
	public IPartialExpression getMemberLength(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value) {
		return new ExpressionArrayLength(position, value.eval(environment));
	}

	@Override
	public Expression indexGet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index) {
		return new ExpressionArrayGet(
				position,
				array,
				index.cast(position, environment, INT));
	}

	@Override
	public Expression indexSet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index, Expression value) {
		return new ExpressionArraySet(
				position,
				array,
				index.cast(position, environment, INT),
				value.cast(position, environment, getBaseType()));
	}
	
	private class ValueIterator implements IZenIterator {
		private final MethodOutput methodOutput;
		private int index;
		
		public ValueIterator(MethodOutput methodOutput) {
			this.methodOutput = methodOutput;
		}

		@Override
		public void compileStart(int[] locals) {
			index = methodOutput.local(Type.INT_TYPE);
			methodOutput.iConst0();
			methodOutput.storeInt(index);
		}

		@Override
		public void compilePreIterate(int[] locals, Label exit) {
			methodOutput.dup();
			methodOutput.arrayLength();
			methodOutput.loadInt(index);
			methodOutput.ifICmpGE(exit);
			
			methodOutput.dup();
			methodOutput.loadInt(index);
			methodOutput.arrayLoad(getBaseType().toASMType());
			methodOutput.store(getBaseType().toASMType(), locals[0]);
		}
		
		@Override
		public void compilePostIterate(int[] locals, Label exit, Label repeat) {
			methodOutput.iinc(index, 1);
			methodOutput.goTo(repeat);
		}
		
		@Override
		public void compileEnd() {
			// pop the array
			methodOutput.pop();
		}

		@Override
		public ZenType getType(int i) {
			return getBaseType();
		}
	}
	
	private class IndexValueIterator implements IZenIterator {
		private final MethodOutput methodOutput;
		
		public IndexValueIterator(MethodOutput methodOutput) {
			this.methodOutput = methodOutput;
		}

		@Override
		public void compileStart(int[] locals) {
			methodOutput.iConst0();
			methodOutput.storeInt(locals[0]);
		}

		@Override
		public void compilePreIterate(int[] locals, Label exit) {
			methodOutput.dup();
			methodOutput.arrayLength();
			methodOutput.loadInt(locals[0]);
			methodOutput.ifICmpGE(exit);
			
			methodOutput.dup();
			methodOutput.loadInt(locals[0]);
			methodOutput.arrayLoad(getBaseType().toASMType());
			methodOutput.store(getBaseType().toASMType(), locals[1]);
		}
		
		@Override
		public void compilePostIterate(int[] locals, Label exit, Label repeat) {
			methodOutput.iinc(locals[0]);
			methodOutput.goTo(repeat);
		}
		
		@Override
		public void compileEnd() {
			// pop the array
			methodOutput.pop();
		}

		@Override
		public ZenType getType(int i) {
			return getBaseType();
		}
	}
}
