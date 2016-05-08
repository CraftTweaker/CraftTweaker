/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.RedNet")
@ModOnly("MineFactoryReloaded")
public class RedNet {
	@ZenMethod
	public static void addCircuit(String name, String[] inputs, String[] outputs, IRedNetFunction function) {
		MineTweakerAPI.apply(new AddCircuitAction(new TweakerRedNetLogicCircuit(name, inputs, outputs, function)));
	}
	
	@ZenMethod
	public static void removeCircuit(String name) {
		List<IRedNetLogicCircuit> toRemove = new ArrayList<IRedNetLogicCircuit>();
		for (IRedNetLogicCircuit circuit : MFRRegistry.getRedNetLogicCircuits()) {
			if (circuit.getUnlocalizedName().equals(name)) {
				toRemove.add(circuit);
			}
		}
		
		if (toRemove.isEmpty()) {
			MineTweakerAPI.logWarning("No such rednet circuit: " + name);
		} else {
			for (IRedNetLogicCircuit toRemoveEntry : toRemove) {
				MineTweakerAPI.apply(new RemoveCircuitAction(toRemoveEntry));
			}
		}
	}
	
	// #############################
	// ### Private inner classes ###
	// #############################
	
	private static class TweakerRedNetLogicCircuit implements IRedNetLogicCircuit {
		private final String name;
		private final String[] inputs;
		private final String[] outputs;
		private final IRedNetFunction function;
		
		public TweakerRedNetLogicCircuit(String name, String[] inputs, String[] outputs, IRedNetFunction function) {
			this.name = name;
			this.inputs = inputs;
			this.outputs = outputs;
			this.function = function;
		}
		
		@Override
		public byte getInputCount() {
			return (byte) inputs.length;
		}

		@Override
		public byte getOutputCount() {
			return (byte) outputs.length;
		}

		@Override
		public int[] recalculateOutputValues(long l, int[] ints) {
			return function.calculate(l, ints);
		}

		@Override
		public String getUnlocalizedName() {
			return name;
		}

		@Override
		public String getInputPinLabel(int i) {
			return inputs[i];
		}

		@Override
		public String getOutputPinLabel(int i) {
			return outputs[i];
		}

		@Override
		public void readFromNBT(NBTTagCompound nbttc) {
		}

		@Override
		public void writeToNBT(NBTTagCompound nbttc) {
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddCircuitAction implements IUndoableAction {
		private final TweakerRedNetLogicCircuit circuit;
		
		public AddCircuitAction(TweakerRedNetLogicCircuit circuit) {
			this.circuit = circuit;
		}

		@Override
		public void apply() {
			MFRRegistry.registerRedNetLogicCircuit(circuit);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getRedNetLogicCircuits().remove(circuit);
		}

		@Override
		public String describe() {
			return "Adding rednet circuit " + circuit.name;
		}

		@Override
		public String describeUndo() {
			return "Removing rednet circuit " + circuit.name;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveCircuitAction implements IUndoableAction {
		private final IRedNetLogicCircuit circuit;
		
		public RemoveCircuitAction(IRedNetLogicCircuit circuit) {
			this.circuit = circuit;
		}

		@Override
		public void apply() {
			MFRRegistry.getRedNetLogicCircuits().remove(circuit);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getRedNetLogicCircuits().add(circuit);
		}

		@Override
		public String describe() {
			return "Removing RedNet circuit " + circuit.getUnlocalizedName();
		}

		@Override
		public String describeUndo() {
			return "Restoring RedNet circuit " + circuit.getUnlocalizedName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
