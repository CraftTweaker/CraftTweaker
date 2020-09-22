package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util;

import org.openzen.zencode.java.ZenCodeType;

public class FormattingUtils {


    public static String getOperatorFormat(ZenCodeType.OperatorType operator) {
        switch (operator) {
            case ADD:
                return "%s + %s";
            case SUB:
                return "%s - %s";
            case MUL:
                return "%s * %s";
            case DIV:
                return "%s / %s";
            case MOD:
                return "%s %% %s";
            case CAT:
                return "%s ~ %s";
            case OR:
                return "%s | %s";
            case AND:
                return "%s & %s";
            case XOR:
                return "%s ^ %s";
            case NEG:
                return "-%s";
            case INVERT:
                return "~%s";
            case NOT:
                return "!%s";
            case INDEXSET:
                return "[%s] = %s";
            case INDEXGET:
                return "[%s]";
            case CONTAINS:
                return "%s in %s";
            case COMPARE:
                return "%s compare %s";
            case MEMBERGETTER:
                return "%s.%s";
            case MEMBERSETTER:
                return "%s.%s = %s";
            case EQUALS:
                return "%s == %s";
            case NOTEQUALS:
                return "%s != %s";
            case SHL:
                return "%s << %s";
            case SHR:
                return "%s >> %s";
            case ADDASSIGN:
                return "%s += %s";
            case SUBASSIGN:
                return "%s -= %s";
            case MULASSIGN:
                return "%s *= %s";
            case DIVASSIGN:
                return "%s /= %s";
            case MODASSIGN:
                return "%s %= %s";
            case CATASSIGN:
                return "%s ~= %s";
            case ORASSIGN:
                return "%s |= %s";
            case ANDASSIGN:
                return "%s &= %s";
            case XORASSIGN:
                return "%s ^= %s";
            case SHLASSIGN:
                return "%s <<= %s";
            case SHRASSIGN:
                return "%s >>= %s";

            default:
                return "";
        }
    }

    public static int getOperandCountFor(ZenCodeType.OperatorType operator) {
        switch (operator) {
            //Unary Operators
            case NEG:
            case INVERT:
            case NOT:
                return 1;

            //Binary Operators
            case INDEXGET:
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case CAT:
            case OR:
            case AND:
            case XOR:
            case CONTAINS:
            case COMPARE:
            case MEMBERGETTER:
            case EQUALS:
            case NOTEQUALS:
            case SHL:
            case SHR:
            case ADDASSIGN:
            case SUBASSIGN:
            case MULASSIGN:
            case DIVASSIGN:
            case MODASSIGN:
            case CATASSIGN:
            case ORASSIGN:
            case ANDASSIGN:
            case XORASSIGN:
            case SHLASSIGN:
            case SHRASSIGN:
                return 2;

            //Ternary Operators
            case INDEXSET:
            case MEMBERSETTER:
                return 3;

            default:
                return 0;
        }
    }

}
