package stanhebben.zenscript.util;

import java.lang.reflect.Array;
import java.util.*;

public class ArrayUtil {
    
    public static boolean[] add(boolean[] array, boolean item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static byte[] add(byte[] array, byte item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static char[] add(char[] array, char item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static double[] add(double[] array, double item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static float[] add(float[] array, float item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static int[] add(int[] array, int item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static long[] add(long[] array, long item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static short[] add(short[] array, short item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static <T> T[] add(T[] array, T item) {
        int size = array.length;
        array = Arrays.copyOfRange(array, 0, size + 1);
        array[size] = item;
        return array;
    }
    
    public static <T> boolean contains(T[] array, T toCheck) {
        for(T item : array) {
            if(item.equals(toCheck))
                return true;
        }
        return false;
    }
    
    public static boolean contains(boolean[] array, boolean toCheck) {
        for(boolean item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    
    public static boolean contains(byte[] array, byte toCheck) {
        for(byte item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    
    public static boolean contains(char[] array, char toCheck) {
        for(char item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    
    public static boolean contains(double[] array, double toCheck) {
        for(double item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    
    public static boolean contains(float[] array, float toCheck) {
        for(float item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    
    public static boolean contains(int[] array, int toCheck) {
        for(int item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    
    public static boolean contains(long[] array, long toCheck) {
        for(long item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    
    public static boolean contains(short[] array, short toCheck) {
        for(short item : array) {
            if(item == toCheck)
                return true;
        }
        return false;
    }
    
    public static <T> T[] inverse(T[] array) {
        return inverse(array, array.length);
    }
    
    public static <T> T[] inverse(T[] array, int length) {
        Class<T[]> clazz = (Class<T[]>) array.getClass();
        T[] out = clazz.cast(Array.newInstance(clazz.getComponentType(), length));
        for(int index = 0; index < array.length; index++) {
            out[length - index - 1] = array[index];
        }
        return out;
    }
    
}
