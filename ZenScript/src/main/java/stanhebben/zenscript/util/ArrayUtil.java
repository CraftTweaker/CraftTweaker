package stanhebben.zenscript.util;

import java.util.Arrays;

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
}
