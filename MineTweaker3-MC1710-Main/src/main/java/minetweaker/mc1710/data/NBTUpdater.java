/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.data;

import java.util.Map;
import minetweaker.api.data.IData;
import minetweaker.api.data.IDataConverter;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author Stan
 */
public class NBTUpdater extends NBTConverter implements IDataConverter<NBTBase> {
	private final NBTTagCompound nbt;

	public NBTUpdater(NBTTagCompound compound) {
		this.nbt = compound;
	}

	@Override
	public NBTBase fromMap(Map<String, IData> map) {
		for (Map.Entry<String, IData> entry : map.entrySet()) {
			if (nbt.hasKey(entry.getKey())) {
				if (entry.getValue() == null) {
					nbt.removeTag(entry.getKey());
				} else {
					if (nbt.getTag(entry.getKey()).getId() == 10) {
						NBTTagCompound tag = (NBTTagCompound) nbt.getTag(entry.getKey());
						nbt.setTag(entry.getKey(), entry.getValue().convert(new NBTUpdater(nbt)));
					} else {
						nbt.setTag(entry.getKey(), from(entry.getValue()));
					}
				}
			} else if (entry.getValue() != null) {
				nbt.setTag(entry.getKey(), from(entry.getValue()));
			}
		}

		return nbt;
	}
}
