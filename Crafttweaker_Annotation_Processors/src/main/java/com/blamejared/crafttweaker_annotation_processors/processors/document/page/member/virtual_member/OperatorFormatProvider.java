package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import org.openzen.zencode.java.ZenCodeType;

public class OperatorFormatProvider {
    
    public static String getOperatorFormat(ZenCodeType.OperatorType operator) {
        switch(operator) {
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
                return "%2$s in %1$s";
            case COMPARE:
                return "%s < %s";
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
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
    
    
}
