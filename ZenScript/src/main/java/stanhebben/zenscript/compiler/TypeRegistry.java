package stanhebben.zenscript.compiler;

import stanhebben.zenscript.type.*;
import stanhebben.zenscript.value.IAny;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author Stanneke
 */
public class TypeRegistry implements ITypeRegistry {
    
    private final Map<Class, ZenType> types;
    
    public TypeRegistry() {
        types = new HashMap<>();
        
        types.put(boolean.class, ZenType.BOOL);
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
        
        types.put(IAny.class, ZenTypeAny.INSTANCE);
        
        types.put(String.class, ZenTypeString.INSTANCE);
        types.put(List.class, new ZenTypeArrayBasic(ZenTypeAny.INSTANCE));
    }
    
    public ZenType getClassType(Class cls) {
        if(types.containsKey(cls)) {
            return types.get(cls);
        } else if(cls.isArray()) {
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
        if(type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type raw = pType.getRawType();
            if(raw instanceof Class) {
                Class rawClass = (Class) raw;
                if(List.class.isAssignableFrom(rawClass)) {
                    return getListType(pType);
                } else if(Map.class.isAssignableFrom(rawClass)) {
                    return getMapType(pType);
                } else if(Map.Entry.class.isAssignableFrom(rawClass)) {
                    return getMapEntryType(pType);
                } else {
                    return getClassType(rawClass);
                }
            } else {
                return getType(raw);
            }
        } else if(type instanceof Class) {
            return getClassType((Class) type);
        } else {
            // TODO: cannot retrieve type
            return null;
        }
    }
    
    private ZenType getListType(ParameterizedType type) {
        if(type.getRawType() == List.class) {
            return new ZenTypeArrayList(getType(type.getActualTypeArguments()[0]));
        }
        
        return null;
    }
    
    private ZenType getMapType(ParameterizedType type) {
        if(type.getRawType() == Map.class) {
            return new ZenTypeAssociative(getType(type.getActualTypeArguments()[1]), getType(type.getActualTypeArguments()[0]));
        }
        
        return null;
    }
    
    private ZenTypeEntry getMapEntryType(ParameterizedType type) {
        if(type.getRawType() == Map.Entry.class) {
            return new ZenTypeEntry(getType(type.getActualTypeArguments()[0]), getType(type.getActualTypeArguments()[1]));
        }
        
        return null;
    }
    
    public Map<Class, ZenType> getTypeMap() {
        return types;
    }
}
