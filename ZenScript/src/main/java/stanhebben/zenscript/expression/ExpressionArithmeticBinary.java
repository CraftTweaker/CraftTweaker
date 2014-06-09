/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeBool;
import stanhebben.zenscript.type.ZenTypeByte;
import stanhebben.zenscript.type.ZenTypeDouble;
import stanhebben.zenscript.type.ZenTypeFloat;
import stanhebben.zenscript.type.ZenTypeInt;
import stanhebben.zenscript.type.ZenTypeLong;
import stanhebben.zenscript.type.ZenTypeShort;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionArithmeticBinary extends Expression {
	private final OperatorType operator;
	private final Expression a;
	private final Expression b;
	
	public ExpressionArithmeticBinary(ZenPosition position, OperatorType operator, Expression a, Expression b) {
		super(position);
		
		this.operator = operator;
		this.a = a;
		this.b = b;
	}

	@Override
	public ZenType getType() {
		return a.getType();
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			a.compile(result, environment);
			b.compile(result, environment);
			
			ZenType type = a.getType();
			MethodOutput output = environment.getOutput();
			
			if (type == ZenTypeBool.INSTANCE) {
				switch (operator) {
					case AND:
						output.iAnd();
						break;
					case OR:
						output.iOr();
						break;
					case XOR:
						output.iXor();
						break;
					default:
						throw new RuntimeException("Unsupported operator on " + type + ": " + operator);
				}
			} else if (type == ZenTypeByte.INSTANCE || type == ZenTypeShort.INSTANCE || type == ZenTypeInt.INSTANCE) {
				switch (operator) {
					case ADD:
						output.iAdd();
						break;
					case SUB:
						output.iSub();
						break;
					case MUL:
						output.iMul();
						break;
					case DIV:
						output.iDiv();
						break;
					case MOD:
						output.iRem();
						break;
					case AND:
						output.iAnd();
						break;
					case OR:
						output.iOr();
						break;
					case XOR:
						output.iXor();
						break;
					default:
						throw new RuntimeException("Unsupported operator on " + type + ": " + operator);
				}
			} else if (type == ZenTypeLong.INSTANCE) {
				switch (operator) {
					case ADD:
						output.lAdd();
						break;
					case SUB:
						output.lSub();
						break;
					case MUL:
						output.lMul();
						break;
					case DIV:
						output.lDiv();
						break;
					case MOD:
						output.lRem();
						break;
					case AND:
						output.lAnd();
						break;
					case OR:
						output.lOr();
						break;
					case XOR:
						output.lXor();
						break;
					default:
						throw new RuntimeException("Unsupported operator on " + type + ": " + operator);
				}
			} else if (type == ZenTypeFloat.INSTANCE) {
				switch (operator) {
					case ADD:
						output.fAdd();
						break;
					case SUB:
						output.fSub();
						break;
					case MUL:
						output.fMul();
						break;
					case DIV:
						output.fDiv();
						break;
					case MOD:
						output.fRem();
						break;
					default:
						throw new RuntimeException("Unsupported operator on " + type + ": " + operator);
				}
			} else if (type == ZenTypeDouble.INSTANCE) {
				switch (operator) {
					case ADD:
						output.dAdd();
						break;
					case SUB:
						output.dSub();
						break;
					case MUL:
						output.dMul();
						break;
					case DIV:
						output.dDiv();
						break;
					case MOD:
						output.dRem();
						break;
					default:
						throw new RuntimeException("Unsupported operator on " + type + ": " + operator);
				}
			} else {
				throw new RuntimeException("Internal compilation error: " + type + " is not a supported arithmetic type");
			}
		} else {
			a.compile(result, environment);
			b.compile(result, environment);
		}
	}
}
