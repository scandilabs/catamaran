package com.scandilabs.catamaran.util;


/**
 * Utilities related to arrays 
 */
public class ArrayUtils {
    
    private ArrayUtils() {}

    public static boolean contains(String[] array, String valueToFind, boolean ignoreCase) {
        for (int i = 0; i < array.length; i++) {
            if (ignoreCase) {
                if (array[i].equalsIgnoreCase(valueToFind)) {
                    return true;
                }
            } else {
                if (array[i].equals(valueToFind)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Converts an array into a character-delimited string
     * @param arr the array to convert
     * @param delim the delimiter to use in the resultant string
     * @return the delimited string, or <code>null</code> if
     * the given array is null.
     */
    public static String toString(Object[] arr, String delim) {
        if (arr == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            Object obj = arr[i];
            sb.append(String.valueOf(obj));
            if (i < (arr.length - 1)) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }
    
}
