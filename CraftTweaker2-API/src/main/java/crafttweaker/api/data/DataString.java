package crafttweaker.api.data;

import crafttweaker.api.data.cast.CastResult;
import crafttweaker.api.data.cast.DataConverterString;

/**
 * Contains a string value.
 *
 * @author Stan Hebben
 */
public class DataString implements IData {
    
    private final String value;
    
    public DataString(String value) {
        this.value = value;
    }
    
    @Override
    public IData getAt(int i) {
        return new DataString(value.substring(i, i + 1));
    }
    
    @Override
    public void setAt(int i, IData value) {
        throw new UnsupportedOperationException("Strings are immutable");
    }
    
    @Override
    public IData memberGet(String name) {
        if(name.equals("length")) {
            return new DataInt(value.length());
        } else {
            throw new UnsupportedOperationException("no such member: " + name);
        }
    }
    
    @Override
    public void memberSet(String name, IData data) {
        throw new UnsupportedOperationException("Strings are immutable");
    }
    
    @Override
    public int length() {
        return value.length();
    }
    
    @Override
    public boolean contains(IData data) {
        return equals(data);
    }
    
    @Override
    public boolean equals(IData data) {
        if (data instanceof DataString) {
            return value.equals(((DataString) data).value);
        }
        CastResult<String> result = data.convert(DataConverterString.INSTANCE);
        return result.isOk() && value.equals(result.get());
    }
    
    @Override
    public int compareTo(IData data) {
        return value.compareTo(data.asString());
    }
    
    @Override
    public IData immutable() {
        return this;
    }
    
    @Override
    public IData update(IData data) {
        return data;
    }
    
    @Override
    public <T> T convert(IDataConverter<T> converter) {
        return converter.fromString(value);
    }
    
    @Override
    public IData add(IData other) {
        return new DataString(value + other.asString());
    }
    
    @Override
    public IData sub(IData other) {
        throw new UnsupportedOperationException("Cannot subtract from a string");
    }
    
    @Override
    public IData mul(IData other) {
        throw new UnsupportedOperationException("Cannot multiply a string");
    }
    
    @Override
    public IData div(IData other) {
        throw new UnsupportedOperationException("Cannot divide a string");
    }
    
    @Override
    public IData mod(IData other) {
        throw new UnsupportedOperationException("Cannot perform modulo on a string");
    }
    
    @Override
    public IData and(IData other) {
        throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
    }
    
    @Override
    public IData or(IData other) {
        throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
    }
    
    @Override
    public IData xor(IData other) {
        throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
    }
    
    @Override
    public IData neg() {
        throw new UnsupportedOperationException("Cannot negate a string");
    }
    
    @Override
    public IData not() {
        throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
    }
    
    @Override
    public String toString() {
        return quoteAndEscape(value);
//        return '\"' + value.replace("\"", "\\\"") + "\"";
    }
    
    public static String quoteAndEscape(String p_193588_0_)
    {
        StringBuilder stringbuilder = new StringBuilder("\"");
        
        for (int i = 0; i < p_193588_0_.length(); ++i)
        {
            char c0 = p_193588_0_.charAt(i);
            
            if (c0 == '\\' || c0 == '"')
            {
                stringbuilder.append('\\');
            }
            
            stringbuilder.append(c0);
        }
        
        return stringbuilder.append('"').toString();
    }
}
