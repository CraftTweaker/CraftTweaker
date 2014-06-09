/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.type.ZenTypeArrayBasic;
import stanhebben.zenscript.type.ZenTypeArrayList;
import stanhebben.zenscript.type.ZenTypeAssociative;
import stanhebben.zenscript.type.ZenTypeBool;
import stanhebben.zenscript.type.ZenTypeBoolObject;
import stanhebben.zenscript.type.ZenTypeByte;
import stanhebben.zenscript.type.ZenTypeByteObject;
import stanhebben.zenscript.type.ZenTypeDouble;
import stanhebben.zenscript.type.ZenTypeDoubleObject;
import stanhebben.zenscript.type.ZenTypeFloat;
import stanhebben.zenscript.type.ZenTypeFloatObject;
import stanhebben.zenscript.type.ZenTypeInt;
import stanhebben.zenscript.type.ZenTypeIntObject;
import stanhebben.zenscript.type.ZenTypeLong;
import stanhebben.zenscript.type.ZenTypeLongObject;
import stanhebben.zenscript.type.ZenTypeNative;
import stanhebben.zenscript.type.ZenTypeShort;
import stanhebben.zenscript.type.ZenTypeShortObject;
import stanhebben.zenscript.type.ZenTypeString;
import stanhebben.zenscript.type.ZenTypeVoid;

/**
 *
 * @author Stanneke
 */
public class TypeRegistry implements ITypeRegistry {
	private final Map<Class, ZenType> types;
	
	public TypeRegistry() {
		types = new HashMap<Class, ZenType>();
		
		types.put(boolean.class, ZenTypeBool.INSTANCE);
		types.put(byte.class, ZenTypeByte.INSTANCE);
		types.put(short.class, ZenTypeShort.INSTANCE);
		types.put(int.class, ZenTypeInt.INSTANCE);
		types.put(long.class, ZenTypeLong.INSTANCE);
		types.put(float.class, ZenTypeFloat.INSTANCE);
		types.put(double.class, ZenTypeDouble.INSTANCE);
		types.put(void.class, ZenTypeVoid.INSTANCE);
		
		types.put(Boolean.class, ZenTypeBoolObject.INSTANCE);
		types.put(Byte.class, ZenTypeByteObject.INSTANCE);
		types.put(Short.class, ZenTypeShortObject.INSTANCE);
		types.put(Integer.class, ZenTypeIntObject.INSTANCE);
		types.put(Long.class, ZenTypeLongObject.INSTANCE);
		types.put(Float.class, ZenTypeFloatObject.INSTANCE);
		types.put(Double.class, ZenTypeDoubleObject.INSTANCE);
		
		types.put(String.class, ZenTypeString.INSTANCE);
		types.put(List.class, new ZenTypeArrayBasic(ZenTypeAny.INSTANCE));
	}
	
	public ZenType getClassType(Class cls) {
		if (types.containsKey(cls)) {
			return types.get(cls);
		} else if (cls.isArray()) {
			ZenType result = new ZenTypeArrayBasic(getType(cls.getComponentType()));
			types.put(cls, result);
			return result;
		} else {
			ZenTypeNative result = new ZenTypeNative(cls);
			types.put(cls, result);
			result.complete(this);
			return result;
		}
	}
	
	@Override
	public ZenType getType(Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			Type raw = pType.getRawType();
			if (raw instanceof Class) {
				Class rawClass = (Class) raw;
				if (List.class.isAssignableFrom(rawClass)) {
					return getListType(pType);
				} else if (Map.class.isAssignableFrom(rawClass)) {
					return getMapType(pType);
				} else {
					return getClassType(rawClass);
				}
			} else {
				return getType(raw);
			}
		} else if (type instanceof Class) {
			return getClassType((Class) type);
		} else {
			// TODO: cannot retrieve type
			return null;
		}
	}
	
	private ZenType getListType(ParameterizedType type) {
		if (type.getRawType() == List.class) {
			return new ZenTypeArrayList(getType(type.getActualTypeArguments()[0]));
		}
		
		return null;
	}
	
	private ZenType getMapType(ParameterizedType type) {
		if (type.getRawType() == Map.class) {
			return new ZenTypeAssociative(
					getType(type.getActualTypeArguments()[1]),
					getType(type.getActualTypeArguments()[0]));
		}
		
		return null;
	}
}
