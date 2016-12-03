package com.blamejared.ctgui.client.gui;

import com.blamejared.ctgui.api.CheckButtonRecipe;
import com.blamejared.ctgui.api.GuiBase;
import com.blamejared.ctgui.api.Slider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@SideOnly(Side.CLIENT)
public class GuiMenu {
    private GuiBase parent;
    private int x;
    private int y;
    private List<Pair<CheckButtonRecipe, Slider[]>> buttons = new ArrayList<>();
    //    public Pair<CheckButtonRecipe, Slider[]> matchNotEmpty;
    public Pair<CheckButtonRecipe, Slider[]> useOreDict;
    public Pair<CheckButtonRecipe, Slider[]> matchAnyMetadata;
    public Pair<CheckButtonRecipe, Slider[]> anyDamage;
    public Pair<CheckButtonRecipe, Slider[]> onlyDamage;

    //    public Pair<CheckButtonRecipe, Slider[]> damage;
    public Pair<CheckButtonRecipe, Slider[]> greaterThanEqualDamage;
    public Pair<CheckButtonRecipe, Slider[]> lessThanDamage;
    public Pair<CheckButtonRecipe, Slider[]> betweenDamage;

    public Pair<CheckButtonRecipe, Slider[]> nbt;

    //    public Pair<CheckButtonRecipe, Slider[]> transformDamage;
//    public Pair<CheckButtonRecipe, Slider[]> transformReplace;
    public Pair<CheckButtonRecipe, Slider[]> reuse;
    public Pair<CheckButtonRecipe, Slider[]> noReturn;
//    public Pair<CheckButtonRecipe, Slider[]> giveBack;


    public GuiMenu() {
    }

    public void open(final GuiBase parent, int x, int y) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        int buttonCount = 0;
//        matchNotEmpty = new MutablePair<>(new CheckButtonRecipe(parent, 0, x, y + (15 * buttonCount++), "Match Not Empty", false, "matchNotEmpty"), new Slider[]{});
        useOreDict = new MutablePair<>(new CheckButtonRecipe(parent, 1, x, y + (15 * buttonCount++), "Ore Dictionary", false, "oreDictionary"), new Slider[]{});
        matchAnyMetadata = new MutablePair<>(new CheckButtonRecipe(parent, 2, x, y + (15 * buttonCount++), "Any Metadata", false, "anyMetadata"), new Slider[]{});
        anyDamage = new MutablePair<>(new CheckButtonRecipe(parent, 3, x, y + (15 * buttonCount++), "Any Damage", false, "anyDamage"), new Slider[]{});
        onlyDamage = new MutablePair<>(new CheckButtonRecipe(parent, 4, x, y + (15 * buttonCount++), "Only Damage", false, "onlyDamage"), new Slider[]{});
        greaterThanEqualDamage = new MutablePair<>(new CheckButtonRecipe(parent, 6, x, y + (15 * buttonCount++), "X>=Damage", false, "gted"), new Slider[]{new Slider("gted", 10, x - 2, parent.getGuiTop() + parent.getYSize() + 20, parent.getXSize() + 102, 20, "X= ", "", 0, 0, 0, false, true, slider -> {
            if(parent.selectedSlot != null && parent.selectedSlot.getStack() != null) {
                parent.selectedSlot.getProperties().put(((Slider) slider).getPropertyKey(), slider.getValueInt());
            }
        })});
        lessThanDamage = new MutablePair<>(new CheckButtonRecipe(parent, 7, x, y + (15 * buttonCount++), "Damage<X", false, "ltd"), new Slider[]{new Slider("ltd", 11, x - 2, parent.getGuiTop() + parent.getYSize() + 20, parent.getXSize() + 102, 20, "X= ", "", 0, 0, 0, false, true, slider -> {
            if(parent.selectedSlot != null && parent.selectedSlot.getStack() != null) {
                parent.selectedSlot.getProperties().put(((Slider) slider).getPropertyKey(), slider.getValueInt());
            }
        })});
        betweenDamage = new MutablePair<>(new CheckButtonRecipe(parent, 8, x, y + (15 * buttonCount++), "X<Damage<Y", false, "betweenDamage"), new Slider[]{new Slider("betweenDamageX", 12, x - 2, parent.getGuiTop() + parent.getYSize() + 20, parent.getXSize() + 102, 20, "X= ", "", 0, 0, 0, false, true, slider -> {
            if(parent.selectedSlot != null && parent.selectedSlot.getStack() != null) {
                parent.selectedSlot.getProperties().put(((Slider) slider).getPropertyKey(), slider.getValueInt());
            }
        }), new Slider("betweenDamageY", 13, x - 2, parent.getGuiTop() + parent.getYSize() + 44, parent.getXSize() + 102, 20, "Y= ", "", 0, 0, 0, false, true, slider -> {
            if(parent.selectedSlot != null && parent.selectedSlot.getStack() != null) {
                parent.selectedSlot.getProperties().put(((Slider) slider).getPropertyKey(), slider.getValueInt());
            }
        })});

        nbt = new MutablePair<>(new CheckButtonRecipe(parent, 8, x, y + (15 * buttonCount++), "NBT", false, "nbt"), new Slider[]{});

        reuse = new MutablePair<>(new CheckButtonRecipe(parent, 0, x, y + (15 * buttonCount++), "Reuse", false, "reuse"), new Slider[]{});
        noReturn = new MutablePair<>(new CheckButtonRecipe(parent, 0, x, y + (15 * buttonCount), "No Return", false, "noreturn"), new Slider[]{});


        this.buttons.add(useOreDict);
        this.buttons.add(matchAnyMetadata);
        this.buttons.add(anyDamage);
        this.buttons.add(onlyDamage);

        this.buttons.add(greaterThanEqualDamage);
        this.buttons.add(lessThanDamage);
        this.buttons.add(betweenDamage);
        this.buttons.add(nbt);


        this.buttons.add(reuse);
        this.buttons.add(noReturn);

        for(Pair<CheckButtonRecipe, Slider[]> but : this.buttons) {
            parent.getButtonList().add(but.getLeft());
        }

        useOreDict.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{matchAnyMetadata.getLeft(), anyDamage.getLeft(), onlyDamage.getLeft(), greaterThanEqualDamage.getLeft(), lessThanDamage.getLeft(), betweenDamage.getLeft(), nbt.getLeft()}));
        matchAnyMetadata.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{useOreDict.getLeft(), anyDamage.getLeft(), onlyDamage.getLeft(), greaterThanEqualDamage.getLeft(), lessThanDamage.getLeft(), betweenDamage.getLeft()}));
        anyDamage.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{useOreDict.getLeft(), matchAnyMetadata.getLeft(), onlyDamage.getLeft(), greaterThanEqualDamage.getLeft(), lessThanDamage.getLeft(), betweenDamage.getLeft()}));
        onlyDamage.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{useOreDict.getLeft(), matchAnyMetadata.getLeft(), anyDamage.getLeft(), greaterThanEqualDamage.getLeft(), lessThanDamage.getLeft(), betweenDamage.getLeft()}));

        greaterThanEqualDamage.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{useOreDict.getLeft(), matchAnyMetadata.getLeft(), anyDamage.getLeft(), onlyDamage.getLeft(), lessThanDamage.getLeft(), betweenDamage.getLeft()}));
        lessThanDamage.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{useOreDict.getLeft(), matchAnyMetadata.getLeft(), anyDamage.getLeft(), onlyDamage.getLeft(), greaterThanEqualDamage.getLeft(), betweenDamage.getLeft()}));
        betweenDamage.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{useOreDict.getLeft(), matchAnyMetadata.getLeft(), anyDamage.getLeft(), onlyDamage.getLeft(), greaterThanEqualDamage.getLeft(), lessThanDamage.getLeft()}));

        nbt.getLeft().setIncompatible(Arrays.asList(new CheckButtonRecipe[]{useOreDict.getLeft()}));
        noReturn.getLeft().setIncompatible(Collections.singletonList(reuse.getLeft()));
        reuse.getLeft().setIncompatible(Collections.singletonList(noReturn.getLeft()));


    }

    public void close(GuiBase parent) {
        this.parent = parent;
        if(!parent.getButtonList().isEmpty()) {
            this.parent.getButtonList().remove(useOreDict.getLeft());
            this.parent.getButtonList().remove(matchAnyMetadata.getLeft());
            this.parent.getButtonList().remove(anyDamage.getLeft());
            this.parent.getButtonList().remove(onlyDamage.getLeft());
            this.parent.getButtonList().remove(greaterThanEqualDamage.getLeft());
            this.parent.getButtonList().remove(lessThanDamage.getLeft());
            this.parent.getButtonList().remove(betweenDamage.getLeft());

            this.parent.getButtonList().remove(reuse.getLeft());
            this.parent.getButtonList().remove(noReturn.getLeft());

        }
    }


    public void setState(boolean active) {
        useOreDict.getLeft().setIsChecked(active);
        matchAnyMetadata.getLeft().setIsChecked(active);
        anyDamage.getLeft().setIsChecked(active);
        onlyDamage.getLeft().setIsChecked(active);

        greaterThanEqualDamage.getLeft().setIsChecked(active);
        lessThanDamage.getLeft().setIsChecked(active);
        betweenDamage.getLeft().setIsChecked(active);
        reuse.getLeft().setIsChecked(active);
        noReturn.getLeft().setIsChecked(active);
        nbt.getLeft().setIsChecked(active);

    }

    public void toggle(boolean active) {
        useOreDict.getLeft().enabled = active;
        matchAnyMetadata.getLeft().enabled = active;
        anyDamage.getLeft().enabled = active;
        onlyDamage.getLeft().enabled = active;
        greaterThanEqualDamage.getLeft().enabled = active;
        lessThanDamage.getLeft().enabled = active;
        betweenDamage.getLeft().enabled = active;
        reuse.getLeft().enabled = active;
        noReturn.getLeft().enabled = active;
        nbt.getLeft().enabled = active;


    }

    public void getFromMap(Map<String, Boolean> propertyMap, Map<String, Integer> properties) {
        useOreDict.getLeft().setIsChecked(getPropertyFromMap(propertyMap, useOreDict.getLeft().getPropertyKey()));
        matchAnyMetadata.getLeft().setIsChecked(getPropertyFromMap(propertyMap, matchAnyMetadata.getLeft().getPropertyKey()));
        anyDamage.getLeft().setIsChecked(getPropertyFromMap(propertyMap, anyDamage.getLeft().getPropertyKey()));
        onlyDamage.getLeft().setIsChecked(getPropertyFromMap(propertyMap, onlyDamage.getLeft().getPropertyKey()));
        greaterThanEqualDamage.getLeft().setIsChecked(getPropertyFromMap(propertyMap, greaterThanEqualDamage.getLeft().getPropertyKey()));
        lessThanDamage.getLeft().setIsChecked(getPropertyFromMap(propertyMap, lessThanDamage.getLeft().getPropertyKey()));
        betweenDamage.getLeft().setIsChecked(getPropertyFromMap(propertyMap, betweenDamage.getLeft().getPropertyKey()));
        reuse.getLeft().setIsChecked(getPropertyFromMap(propertyMap, reuse.getLeft().getPropertyKey()));
        noReturn.getLeft().setIsChecked(getPropertyFromMap(propertyMap, noReturn.getLeft().getPropertyKey()));
        nbt.getLeft().setIsChecked(getPropertyFromMap(propertyMap, nbt.getLeft().getPropertyKey()));
    }

    boolean getPropertyFromMap(Map<String, Boolean> propertyMap, String key) {
        if(propertyMap.containsKey(key)) {
            return propertyMap.get(key);
        }
        return false;
    }

    Object getProperty(Map<String, Integer> propertyMap, String key) {
        if(propertyMap.containsKey(key)) {
            return propertyMap.get(key);
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return 11;
    }

    public int getHeight() {
        return 55;
    }


    public Slider[] getSliders(CheckButtonRecipe check) {
        for(Pair<CheckButtonRecipe, Slider[]> pair : this.buttons) {
            if(pair.getLeft().getPropertyKey().equals(check.getPropertyKey())) {
                return pair.getValue();
            }
        }
        return new Slider[0];
    }

    public List<Pair<CheckButtonRecipe, Slider[]>> getButtons() {
        return buttons;
    }
}
