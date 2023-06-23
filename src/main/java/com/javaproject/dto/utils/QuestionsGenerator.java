package com.javaproject.dto.utils;

import com.javaproject.dto.FlagCodesResponse;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class QuestionsGenerator {
    public static Dictionary<String, String> getRandomDictionaryElements(int count) {
        var dict = FlagCodesResponse.codes;
        List<String> keys = Collections.list(dict.keys());
        Collections.shuffle(keys);

        Dictionary<String, String> randomElements = new Hashtable<>();
        int selectedCount = Math.min(count, keys.size());
        for (int i = 0; i < selectedCount; i++) {
            String key = keys.get(i);
            String value = dict.get(key);
            randomElements.put(key, value);
        }

        return randomElements;
    }

    public static List<String> getRandomDictionaryValues(int count, String excludeValue) {
        var dict = FlagCodesResponse.codes;
        List<String> values = Collections.list(dict.elements());
        values.remove(excludeValue); // Remove the specified excludeValue from the list
        Collections.shuffle(values);

        int selectedCount = Math.min(count, values.size());
        return values.subList(0, selectedCount);
    }
}
