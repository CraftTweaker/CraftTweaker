/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type;

import java.util.List;
import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ZenTypeFunction extends ZenType {
	private final ZenType returnType;
	private final ZenType[] argumentTypes;
	private final String name;
	
	public ZenTypeFunction(ZenType returnType, List<ParsedFunctionArgument> arguments) {
		this.returnType = returnType;
		argumentTypes = new ZenType[arguments.size()];
		for (int i = 0; i < argumentTypes.length; i++) {
			argumentTypes[i] = arguments.get(i).getType();
		}
		
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("function(");
		for (ZenType type : argumentTypes) {
			nameBuilder.append(type.getName());
		}
		nameBuilder.append(returnType.getName());
		name = nameBuilder.toString();
	}
	
	public ZenTypeFunction(ZenType returnType, ZenType[] argumentTypes) {
		this.returnType = returnType;
		this.argumentTypes = argumentTypes;
		
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("function(");
		for (ZenType type : argumentTypes) {
			nameBuilder.append(type.getName());
		}
		nameBuilder.append(returnType.getName());
		name = nameBuilder.toString();
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		environment.error(position, "Functions have no members");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		environment.error(position, "Functions have no static members");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return equals(type) || canCastToNative(type) || canCastExpansion(environment, type); // TODO: LATER: expand
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return equals(type) || canCastToNative(type) || canCastExpansion(environment, type); // TODO: LATER: expand
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		throw new UnsupportedOperationException("not supported yet");
	}

	@Override
	public Type toASMType() {
		return null; // TODO: NEXT: expand
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public String getSignature() {
		return null; // TODO: NEXT: expand
	}

	@Override
	public boolean isPointer() {
		return true;
	}
	
	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		environment.error(position, "cannot apply operators on a function");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		environment.error(position, "cannot apply operators on a function");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}
	
	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		environment.error(position, "cannot apply operators on a function");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}
	
	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		environment.error(position, "cannot apply operators on a function");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		return null; // TODO: complete
	}

	@Override
	public Class toJavaClass() {
		// TODO: complete
		return null;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		if (!compileCastExpansion(position, environment, type)) {
			environment.error(position, "cannot cast " + this + " to " + type);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
	
	private boolean canCastToNative(ZenType type) {
		if (!(type instanceof ZenTypeNative)) return false;
		
		ZenTypeNative ntype = (ZenTypeNative) type;
		if (ntype.getNativeClass().isInterface() && ntype.getNativeClass().getMethods().length == 1) {
			// ta-da! we got a functional interface
			// does it match?
			// TODO: expand
			return true; // assume for now it is
		}
		
		return false;
	}
}
