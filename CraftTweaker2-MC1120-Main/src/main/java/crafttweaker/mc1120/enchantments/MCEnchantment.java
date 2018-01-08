package crafttweaker.mc1120.enchantments;

import crafttweaker.api.data.*;
import crafttweaker.api.enchantments.*;
import net.minecraft.enchantment.Enchantment;

import java.util.*;

public class MCEnchantment implements IEnchantment {
    
    private final Enchantment definition;
    private int level;
    
    public MCEnchantment(int id, int lvl) {
        this(Enchantment.getEnchantmentByID(id), lvl);
    }
    
    public MCEnchantment(Enchantment enchantment, int level) {
        this.definition = enchantment;
        this.level = level;
    }
    
    @Override
    public IEnchantmentDefinition getDefinition() {
        return new MCEnchantmentDefinition(definition);
    }
    
    @Override
    public IData makeTag() {
        Map<String, IData> map = new HashMap<>();
        map.put("id", new DataShort((short) getDefinition().getID()));
        map.put("lvl", new DataShort((short) level));
        return new DataMap(Collections.singletonMap("ench", new DataList(Collections.singletonList(new DataMap(map, false)), false)), false);
    }
    
    @Override
    public int getLevel() {
        return level;
    }
    
    @Override
    public void setLevel(int level) {
        this.level = level;
    }
    
    @Override
    public String displayName() {
        return definition.getTranslatedName(level);
    }
}
