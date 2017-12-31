package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import net.minecraftforge.oredict.OreDictionary;

public class ActionOreDictMirror implements IAction {

    private final String idTarget;
    private final String idSource;


    public ActionOreDictMirror(String idTarget, String idSource) {
        this.idTarget = idTarget;
        this.idSource = idSource;
    }

    @Override
    public void apply() {
        int sourceOreId = OreDictionary.getOreID(idSource);
        int targetOreId = OreDictionary.getOreID(idTarget);
        MCOreDictEntry.getOredictContents().set(targetOreId, MCOreDictEntry.getOredictContents().get(sourceOreId));
        MCOreDictEntry.getOredictContentsUn().set(targetOreId, MCOreDictEntry.getOredictContentsUn().get(sourceOreId));
    }

    @Override
    public String describe() {
        return "Mirroring " + idSource + " to " + idTarget;
    }
}