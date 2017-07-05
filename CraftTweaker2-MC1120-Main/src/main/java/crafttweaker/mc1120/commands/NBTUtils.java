package crafttweaker.mc1120.commands;

import crafttweaker.CraftTweakerAPI;

/**
 * @author BloodWorkXGaming
 */
public class NBTUtils {

    public static String getAppealingString(String string){

        CraftTweakerAPI.logInfo("Compact NBT:");
        CraftTweakerAPI.logInfo(string);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderNonColor = new StringBuilder();
        int currentIndent = 0;

        boolean inQuotes = false;
        boolean isInValue = false;

        stringBuilder.append("\u00A7e├");
        stringBuilderNonColor.append("\n├");


        for (int i = 0; i < string.length(); i++) {
            char[] cArray = new char[1];
            string.getChars(i, i+1, cArray, 0);
            char c = cArray[0];

            switch (c){
                case '"':
                    inQuotes = !inQuotes;
                    stringBuilder.append("\u00A73" + '"');
                    stringBuilderNonColor.append(c);

                    break;
                case '{':
                case '[':
                    if (!inQuotes){
                        currentIndent++;
                        isInValue = false;

                        stringBuilder.append("\u00A72").append(c);
                        addNewLine(stringBuilder, currentIndent);

                        stringBuilderNonColor.append(c);
                        addNewLineNoColor(stringBuilderNonColor, currentIndent);
                    }else {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes){
                        currentIndent--;
                        isInValue = false;
                        addNewLine(stringBuilder, currentIndent);
                        stringBuilder.append("\u00A72").append(c);

                        addNewLineNoColor(stringBuilderNonColor, currentIndent);
                        stringBuilderNonColor.append(c);
                    }else {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case ',':
                    if (!inQuotes){
                        isInValue = false;
                        stringBuilder.append("\u00A72,");
                        addNewLine(stringBuilder, currentIndent);

                        stringBuilderNonColor.append(c);
                        addNewLineNoColor(stringBuilderNonColor, currentIndent);

                    }else {
                        stringBuilder.append("\u00A7b,");
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case ':':
                    if (!inQuotes){
                        isInValue = true;
                        stringBuilder.append("\u00A72").append(c);
                        stringBuilderNonColor.append(c);

                    }else {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                default:
                    if (inQuotes){
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }else if (isInValue){
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }else {
                        stringBuilder.append(c);
                        stringBuilderNonColor.append(c);
                    }
            }

        }

        CraftTweakerAPI.logInfo("Fancy NBT:");
        CraftTweakerAPI.logInfo(stringBuilderNonColor.toString());

        return stringBuilder.toString();
    }

    static private void addNewLine(StringBuilder s, int indent){
        s.append("\n\u00A7e├");
        for (int j = 0; j <indent; j++) {
            s.append("\u00A7e    ");
        }
    }

    static private void addNewLineNoColor(StringBuilder s, int indent){
        s.append("\n├");
        for (int j = 0; j <indent; j++) {
            s.append("    ");
        }
    }
}