import crafttweaker.api.util.text.MCTextComponent;
import crafttweaker.api.util.text.MCStyle;

<item:minecraft:diamond>.addTooltip("This is a test");
<item:minecraft:music_disc_blocks>.removeTooltip(".* - blocks");
<item:minecraft:apple>.modifyTooltip((stack, tooltip, advanced) => {
tooltip.add("I am in a stack of " + stack.amount + "items");
});

<item:minecraft:diamond>.addShiftTooltip(("this is shifted" as MCTextComponent).setStyle(new MCStyle().setColor(<formatting:red>)), "Press shift for more info");