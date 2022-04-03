package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.type.TypeID;

import java.lang.reflect.Parameter;

record DefaultedLazyValue(Parameter parameter, TypeID typeId, FunctionParameter functionParameter) {}
