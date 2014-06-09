/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionAs;
import stanhebben.zenscript.expression.ExpressionCompareGeneric;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.MethodCompiler;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;
import stanhebben.zenscript.value.IAny;

/**
 *
 * @author Stanneke
 */
public class ZenTypeAny extends ZenType {
	public static final ZenTypeAny INSTANCE = new ZenTypeAny();
	
	private static final Map<String, MethodCompiler> CASTERS;
	
	static {
		CASTERS = new HashMap<String, MethodCompiler>();
		CASTERS.put("bool", new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asBool", boolean.class);
			}
		});
		CASTERS.put("byte", new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asByte", byte.class);
			}
		});
		CASTERS.put("short", new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asShort", short.class);
			}
		});
		CASTERS.put("int", new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asInt", int.class);
			}
		});
		CASTERS.put("long", new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asLong", long.class);
			}
		});
		CASTERS.put("float", new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asFloat", float.class);
			}
		});
		CASTERS.put("double", new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asDouble", double.class);
			}
		});
		CASTERS.put(Boolean.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asBool", boolean.class);
				output.invokeStatic(Boolean.class, "valueOf", Boolean.class, boolean.class);
			}
		});
		CASTERS.put(Byte.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asByte", boolean.class);
				output.invokeStatic(Byte.class, "valueOf", Byte.class, byte.class);
			}
		});
		CASTERS.put(Short.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asShort", short.class);
				output.invokeStatic(Short.class, "valueOf", Short.class, short.class);
			}
		});
		CASTERS.put(Integer.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asInt", int.class);
				output.invokeStatic(Integer.class, "valueOf", Integer.class, int.class);
			}
		});
		CASTERS.put(Long.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asLong", long.class);
				output.invokeStatic(Long.class, "valueOf", Long.class, long.class);
			}
		});
		CASTERS.put(Float.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asFloat", float.class);
				output.invokeStatic(Float.class, "valueOf", Float.class, float.class);
			}
		});
		CASTERS.put(Double.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asDouble", double.class);
				output.invokeStatic(Double.class, "valueOf", Double.class, double.class);
			}
		});
		CASTERS.put(String.class.getName(), new MethodCompiler() {
			@Override
			public void compile(MethodOutput output) {
				output.invokeInterface(IAny.class, "asString", String.class);
			}
		});
	}
	
	private ZenTypeAny() {}
	
	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		return null;
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return null;
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null; // TODO: how to handle iteration on any-values
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return true; // true or not?
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return true;
	}
	
	@Override
	public Class toJavaClass() {
		return IAny.class;
	}

	@Override
	public Type toASMType() {
		return Type.getType(IAny.class);
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public String getSignature() {
		return signature(IAny.class);
	}

	@Override
	public boolean isPointer() {
		return true;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType toType) {
		if (CASTERS.containsKey(toType.getName())) {
			CASTERS.get(toType.getName()).compile(environment.getOutput());
		} else {
			if (!compileCastExpansion(position, environment, toType)) {
				environment.getOutput().constant(toType);
				environment.getOutput().invokeInterface(IAny.class, "as", Class.class, Object.class);
			}
		}
	}
	
	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		//try {
			switch (operator) {
				/*case NEG:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("neg"),
							ZenTypeAny.INSTANCE,
							value);
				case NOT:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("not"),
							ZenTypeAny.INSTANCE,
							value);*/
				default:
					return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
			}
		/*} catch (NoSuchMethodException ex) {
			throw new RuntimeException("could not find method", ex);
		}*/
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		//try {
			switch (operator) {
				/*case ADD:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("add", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case CAT:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("cat", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case SUB:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("sub", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case MUL:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("mul", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case DIV:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("div", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case MOD:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("mod", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case AND:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("and", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case OR:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("or", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case XOR:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("xor", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case CONTAINS:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("contains", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case INDEXGET:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("indexGet", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case RANGE:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("range", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));
				case COMPARE:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("compareTo", IAny.class),
							ZenTypeAny.INSTANCE,
							left,
							right.cast(position, environment, ANY));*/
				default:
					return new ExpressionInvalid(position, ANY);
			}
		/*} catch (NoSuchMethodException ex) {
			throw new RuntimeException("method not found", ex);
		}*/
	}

	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		//try {
			switch (operator) {
				/*case INDEXSET:
					return new ExpressionJavaCallVirtual(
							position,
							IAny.class, 
							IAny.class.getMethod("indexSet", IAny.class, IAny.class),
							ZenTypeAny.INSTANCE,
							first,
							second.cast(position, environment, ANY),
							third.cast(position, environment, ANY));*/
				default:
					return new ExpressionInvalid(position, ANY);
			}
		/*} catch (NoSuchMethodException ex) {
			throw new RuntimeException("method not found", ex);
		}*/
	}

	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		Expression comparator = JavaMethod
				.get(environment, IAny.class, "compareTo", IAny.class)
				.callVirtual(position, environment, left, right);
		
		return new ExpressionCompareGeneric(
				position,
				comparator,
				type);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		try {
			return JavaMethod
					.get(environment, IAny.class, "call", Class.forName("[" + signature(IAny.class)))
					.callVirtual(position, environment, receiver, arguments);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type.equals(this)) return value;
		
		if (canCastExpansion(environment, type)) {
			return castExpansion(position, environment, value, type);
		} else {
			return new ExpressionAs(position, value, type);
		}
	}

	@Override
	public String getName() {
		return "any";
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
}
