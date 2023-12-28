import crafttweaker.api.events.CTTestEventManager;
import test.api.TestEvent;
import crafttweaker.api.capability.IItemHandler;
import crafttweaker.api.capability.Capabilities;

CTTestEventManager.register<TestEvent>((event) => {

    var player = event.player;
    var pos = event.innerPos;
    var level = event.level;

    if level.isClientSide { return; }

    var blockEntity = level.getBlockEntity(pos);

    if blockEntity != null {

        var itemHandler = blockEntity.getCapability<IItemHandler>(Capabilities.ITEM);
        if itemHandler != null {
            event.printf(itemHandler.getStackInSlot(2).commandString);
            itemHandler.insertItem(1, <item:minecraft:dirt>, false);
            event.printf(itemHandler.getStackInSlot(1).commandString);
        }
    }

});