package com.daviancorp.android.ui.general;

import android.content.Context;
import android.util.Log;

import androidx.annotation.ArrayRes;

/**
 * A static class that provides helper methods for accessing and managing the {@code res} directory.
 */
public class ResourceUtils {

    private ResourceUtils() { }

    /**
     * Attempts to split the specified string by a comma.
     * @param stringArray The {@code string-array} resource from which to parse.
     * @param index The index in the string array to fetch a string from.
     * @param part The 0-based index of final piece of the string to retrieve.
     * @return The {@code part} index of a group of strings created by splitting the desired string by commas.
     */
    public static String splitStringInArrayByComma(@ArrayRes int stringArray, int index, int part, Context context) {
        String fullString;

        try {
            fullString = context.getResources().getStringArray(stringArray)[index];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Log.e("App", "The string array resource does not have the specified index.");
            return "";
        }

        try {
            return fullString.split(",")[part];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Log.e("App", "The specified string in the array does not have a comma in it.");
            return "";
        }
    }
}
