package minetweaker.mc1120.data;

import minetweaker.api.data.*;
import net.minecraft.nbt.*;

import java.util.Map;

/**
 * @author Stan
 */
public class NBTUpdater extends NBTConverter implements IDataConverter<NBTBase> {

    private final NBTTagCompound nbt;

    public NBTUpdater(NBTTagCompound compound) {
        this.nbt = compound;
    }

    @Override
    public NBTBase fromMap(Map<String, IData> map) {
        for(Map.Entry<String, IData> entry : map.entrySet()) {
            if(nbt.hasKey(entry.getKey())) {
                if(entry.getValue() == null) {
                    nbt.removeTag(entry.getKey());
                } else {
                    if(nbt.getTag(entry.getKey()).getId() == 10) {
                        nbt.setTag(entry.getKey(), entry.getValue().convert(new NBTUpdater(nbt)));
                    } else {
                        nbt.setTag(entry.getKey(), from(entry.getValue()));
                    }
                }
            } else if(entry.getValue() != null) {
                nbt.setTag(entry.getKey(), from(entry.getValue()));
            }
        }

        return nbt;
    }
}
