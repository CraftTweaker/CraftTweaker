package com.blamejared.crafttweaker_annotation_processors.processors.util;

import org.openzen.zencode.java.ZenCodeType;

public class OperatorTypeParameterCountProvider {
    
    public static int getParameterCountFor(ZenCodeType.OperatorType operatorType) {
        
        switch(operatorType) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case CAT:
            case OR:
            case AND:
            case XOR:
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
            case INDEXGET:
            case CONTAINS:
            case COMPARE:
            case MEMBERGETTER:
            case EQUALS:
            case NOTEQUALS:
            case SHL:
            case SHR:
                return 1;
            case NEG:
            case INVERT:
            case NOT:
                return 0;
            case MEMBERSETTER:
                return 2;
            case INDEXSET:
                return 3;
            default:
                throw new IllegalArgumentException("Invalid operatorType: " + operatorType);
        }
    }
    
}
