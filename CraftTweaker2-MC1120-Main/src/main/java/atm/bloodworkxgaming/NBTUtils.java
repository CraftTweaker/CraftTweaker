package atm.bloodworkxgaming;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Jonas on 28.05.2017.
 */
public class NBTUtils {


    public static String getSerilaizedNBTString(ItemStack stack){
        if (stack.serializeNBT().hasKey("tag")){
            return stack.serializeNBT().getTag("tag").toString();
        }else {
            return "";
        }
    }

    public static String getAppealingString(String string){

        CraftTweakerAPI.logInfo("Compact NBT:");
        CraftTweakerAPI.logInfo(string);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderNonColor = new StringBuilder();
        int currentIndent = 0;

        boolean inQuotes = false;
        boolean isInValue = false;

        stringBuilder.append("§e├");
        stringBuilderNonColor.append("\n├");


        for (int i = 0; i < string.length(); i++) {
            char[] cArray = new char[1];
            string.getChars(i, i+1, cArray, 0);
            char c = cArray[0];

            switch (c){
                case '"':
                    inQuotes = !inQuotes;
                    stringBuilder.append("§3" + '"');
                    stringBuilderNonColor.append(c);

                    break;
                case '{':
                case '[':
                    if (!inQuotes){
                        currentIndent++;
                        isInValue = false;

                        stringBuilder.append("§2").append(c);
                        addNewLine(stringBuilder, currentIndent);

                        stringBuilderNonColor.append(c);
                        addNewLineNoColor(stringBuilderNonColor, currentIndent);
                    }else {
                        stringBuilder.append("§b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes){
                        currentIndent--;
                        isInValue = false;
                        addNewLine(stringBuilder, currentIndent);
                        stringBuilder.append("§2").append(c);

                        addNewLineNoColor(stringBuilderNonColor, currentIndent);
                        stringBuilderNonColor.append(c);
                    }else {
                        stringBuilder.append("§b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case ',':
                    if (!inQuotes){
                        isInValue = false;
                        stringBuilder.append("§2,");
                        addNewLine(stringBuilder, currentIndent);

                        stringBuilderNonColor.append(c);
                        addNewLineNoColor(stringBuilderNonColor, currentIndent);

                    }else {
                        stringBuilder.append("§b,");
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case ':':
                    if (!inQuotes){
                        isInValue = true;
                        stringBuilder.append("§2").append(c);
                        stringBuilderNonColor.append(c);

                    }else {
                        stringBuilder.append("§b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                default:
                    if (inQuotes){
                        stringBuilder.append("§b").append(c);
                        stringBuilderNonColor.append(c);

                    }else if (isInValue){
                        stringBuilder.append("§b").append(c);
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
        s.append("\n§e├");
        for (int j = 0; j <indent; j++) {
            s.append("§e    ");
        }
    }

    static private void addNewLineNoColor(StringBuilder s, int indent){
        s.append("\n├");
        for (int j = 0; j <indent; j++) {
            s.append("    ");
        }
    }

    public static NBTTagCompound StringToNBTCompound(String string){
        NBTTagCompound nbt = new NBTTagCompound();
        try {
             nbt = JsonToNBT.getTagFromJson(string);
        } catch (NBTException e) {
            System.out.println("The NBT-Tag " + string + " is not correct!");
            e.printStackTrace();
        }

        return nbt;
    }


}