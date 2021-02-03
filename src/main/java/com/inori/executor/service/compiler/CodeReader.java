/*
    Copyright @ 2020
 */
package com.inori.executor.service.compiler;

import org.thymeleaf.util.StringUtils;

/**
 * CodeReader
 *
 * @author inori
 * @date 2021/2/3
 */

public class CodeReader {
    private String code;
    private int index = 0;
    private int line = 0;

    public CodeReader(String code) {
        this.code = code;
    }

    public String getClassName(boolean simple) throws CompileException {
        String lineStr;
        String packageName = "";
        while ((lineStr = readLine()) != null) {
            final String trim = lineStr.trim();
            if (trim.startsWith("package")) {
                packageName = getPackageName(trim);
                continue;
            }
            if (trim.startsWith("import")) continue;
            if (trim.startsWith("public")) { //main class
                String className = getClassName(trim);
                if (!simple) className = packageName + className;
                return className;
            }
        }
        throw new CompileException("No public class defined");
    }

    private String getClassName(String trimLine) throws CompileException {
        String className = "";
        final String[] s = trimLine.split(" ");
        int i = 0;
        for (; i < s.length; i++) {
            if ("class".equals(s[i])) {
                break;
            }
        }
        if (i >= s.length - 1) throw new CompileException("class name parse failed");
        className = s[i + 1];
        final char c = className.charAt(0);
        if (c > 'a' && c < 'z' || c > 'A' && c < 'Z' || c == '$' || c == '_') {
            return className;
        }
        throw new CompileException("class name un support: " + className);
    }

    private String getPackageName(String trimLine) {
        String packageName = "";
        final String[] s = trimLine.split(" ");
        if (s.length >= 2) {
            packageName = s[1];
        }
        if (packageName.endsWith(";")) {
            packageName = packageName.replaceAll(";", "");
        }
        if (!StringUtils.isEmptyOrWhitespace(packageName)) {
            packageName = packageName + ".";
        }
        return packageName;
    }

    public String readLine() {
        String lineStr = "";
        boolean find = false;
        for (int end = index; end < code.length(); end++) {
            if (code.charAt(end) == '\n') {
                lineStr = code.substring(index, end);
                index = end + 1;
                this.line += 1;
                find = true;
                break;
            }
        }
        if (!find) return null;
        final String trim = lineStr.trim();
        if (trim.startsWith("//") || (trim.startsWith("/*") && trim.endsWith("*/")) || StringUtils.isEmpty(trim)) {
            lineStr = readLine();
        } else if (trim.startsWith("/*")) {
            continueComment();
            lineStr = readLine();
        }
        return lineStr;
    }

    private void continueComment() {
        for (int end = index; end < code.length() - 1; end++) {
            if (code.charAt(end) == '\n') line += 1;
            if (code.charAt(end) == '*' && code.charAt(end + 1) == '/') {
                index = end + 2;
                this.line += 1;
                break;
            }
        }
    }

    public void reset() {
        index = 0;
        line = 0;
    }

    public int getLine() {
        return line;
    }


    public static void main(String[] args) throws CompileException {
        String code = "/*\n" +
                "    Copyright @ 2020\n" +
                " */\n" +
                "package com.inori.executor.service.compiler;\n" +
                "\n" +
                "import org.thymeleaf.util.StringUtils;\n" +
                "\n" +
                "/**\n" +
                " * CodeReader\n" +
                " *\n" +
                " * @author inori\n" +
                " * @date 2021/2/3\n" +
                " */\n" +
                "public class CodeReader {\n" +
                "    private String code;\n" +
                "    private int index = 0;\n" +
                "    private int line = 0;\n" +
                "\n" +
                "    public CodeReader(String code) {\n" +
                "        this.code = code;\n" +
                "    }\n" +
                "\n" +
                "    public String readLine() {\n" +
                "        String lineStr = \"\";\n" +
                "        boolean find = false;\n" +
                "        for (int end = index; end < code.length(); end++) {\n" +
                "            if (code.charAt(end) == '\\n') {\n" +
                "                lineStr = code.substring(index, end);\n" +
                "                index = end + 1;\n" +
                "                this.line += 1;\n" +
                "                find = true;\n" +
                "                break;\n" +
                "            }\n" +
                "        }\n" +
                "        if (!find) return null;\n" +
                "        final String trim = lineStr.trim();\n" +
                "        if (trim.startsWith(\"//\") || (trim.startsWith(\"/*\") && trim.endsWith(\"*/\")) || StringUtils.isEmpty(trim)) {\n" +
                "            lineStr = readLine();\n" +
                "        } else if (trim.startsWith(\"/*\")) {\n" +
                "            continueComment();\n" +
                "            lineStr = readLine();\n" +
                "        }\n" +
                "        return lineStr;\n" +
                "    }\n" +
                "\n" +
                "    private void continueComment() {\n" +
                "        for (int end = index; end < code.length() - 1; end++) {\n" +
                "            if (code.charAt(end) == '\\n') line += 1;\n" +
                "            if (code.charAt(end) == '*' && code.charAt(end + 1) == '/') {\n" +
                "                index = end + 1;\n" +
                "                this.line += 1;\n" +
                "                break;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public void reset() {\n" +
                "        index = 0;\n" +
                "        line = 0;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "    }\n" +
                "}\n";
        final CodeReader codeReader = new CodeReader(code);
        final String className = codeReader.getClassName(false);
        System.out.println(className);
    }
}
