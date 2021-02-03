package com.inori.executor.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassNameTransfer
 *
 * @author inori
 * @date 2021/2/2
 */
public class ClassNameTransfer {
    private static final Map<Character, String> transferMap;

    static {
        transferMap = new HashMap<Character, String>() {{
            put('B', "byte");
            put('C', "char");
            put('D', "double");
            put('F', "float");
            put('I', "int");
            put('J', "long");
            put('S', "short");
            put('Z', "boolean");
            put('V', "void");
            put('L', "");
        }};
    }

    public static String transfer(String className) {
        if (!className.startsWith("[")) return className;

        int aCount = 0; //数组维度
        StringBuilder sb = new StringBuilder();
        char replaceSymbol = 'L';
        for (int i = 0; i < className.length(); i++) {
            if (className.charAt(i) == '[') {
                sb.append("[]");
            } else {
                sb.append(transferMap.get(className.charAt(i)));
                sb.append(className.substring(i + 1));
                break;
            }
        }
        return sb.toString();
    }

    public static String bytesTransfer(String bytes) {
        double i = 0;
        try {
            i = Integer.parseInt(bytes);
        } catch (Exception e) {
            return bytes;
        }
        String unit = " B";
        if (i > 1024) {
            unit = "KB";
            i = i / 1024;
            if (i > 1024) {
                unit = "MB";
                i = i / 1024;
            }
        }
        StringBuilder sb = new StringBuilder(String.format("%.1f", i) + unit);
        final int c = bytes.length() - sb.length();
        if (c > 0) { //补上空格
            for (int i1 = 0; i1 < c; i1++) {
                sb.insert(0, " ");
            }
        }
        return sb.toString();
    }

}
