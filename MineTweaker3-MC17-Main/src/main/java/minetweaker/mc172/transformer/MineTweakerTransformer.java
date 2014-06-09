/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.transformer;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import java.util.Map;
import minetweaker.mc172.MineTweakerMod;

/**
 *
 * @author Stanneke
 */
@MCVersion(MineTweakerMod.MCVERSION)
@IFMLLoadingPlugin.TransformerExclusions("minetweaker.mc172.transformer")
public class MineTweakerTransformer implements IFMLLoadingPlugin {
	
	@Override
	public String[] getASMTransformerClass() {
		System.out.println("MineTweakerASM transformer class loading");
		return new String[] { MineTweakerASM.class.getName() };
	}

	@Override
	public String getModContainerClass() {
		//return MineTweaker.class.getName();
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}

