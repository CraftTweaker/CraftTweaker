#modloader forge
/*
 * This file is only relevant to Forge, if you are using Fabric this will not work!
*/


/*
 * Events are fired by certain actions that occur in the game, some examples are:
 * 1) crafttweaker.api.event.entity.EntityJoinWorldEvent
 * 1.1) Fired when an Entity joins (spawns in) the world.
 * 2) crafttweaker.api.event.tick.WorldTickEvent
 * 2.1) Fired when the World ticks (Normal Ticks Per Second is 20, so this will fire 20 times a second).
 * 3) crafttweaker.api.event.block.FarmlandTrampleEvent
 * 3.1) Fired when farmland is tramped by an Entity.
 *
 * There are a ton more events that get fired, way too many to be listed here, you can find out more about them on the documentation site.
 *
 * Some Events can be cancled, for example, canceling  the FarmlandTrampleEvent, will prevent the farmland from being trampled.
 * Another example of a cancelable event is the EnderEntityTeleportEvent, which, if cancled, will prevent any forms of ender teleportation, namely Ender Pearls and Enderman teleporting away.
*/

// First things first, you should import the CTEventManager, which allows you to register Events.
import crafttweaker.api.events.CTEventManager;
// We are also going to import other classes that are needed for this example.
import crafttweaker.api.text.Component;
import crafttweaker.api.event.item.ItemTossEvent;

// Now that we have imported the CTEventManager, we can use it to register an event.
// This example will listen to the PlayerLoggedInEvent and send a message to the player when they log in.
CTEventManager.register<crafttweaker.api.event.entity.player.PlayerLoggedInEvent>((event) => {
    // You can find what members `event` has by looking on the CraftTweaker docs or by looking at the code
    // In this case, since PlayerLoggedInEvent extends PLayerEvent, we have access to the `player` object.
    event.player.sendMessage("You have the CraftTweaker example scripts enabled!");
    // Here we are using Component to make `deleting` red
    var message = Component.literal("You can disable them by ") + Component.literal("deleting").setStyle(<constant:formatting:red>) + Component.literal(" the ./scripts/examples folder!");
    event.player.sendMessage(message);
});

// You don't always need to use the full event package and name, if you import the Event, then you can reference it by it's name.
// This example will listen to the ItemTossEvent and if the player is standing on a Bookshelf, the dropped Item will turn into a Diamond.
CTEventManager.register<ItemTossEvent>((event) => {
    // Here we are just storing the values so they are easier to reference.
    val player = event.player;
    val level = player.level;
    // First we need to see what side we are running on, we only want this to run on the server side (if `remote` is true, it means it is the client)
    if level.isClientSide {
        // Since it is the client, we are just going to do nothing and return.
        return;
    }
    // Now we get the BlockState at the position below the player, check if it is a Bookshelf.
    if level.getBlockState(player.blockPosition.below()) == <blockstate:minecraft:bookshelf> {
        // If it is a Bookshelf, set the item to be a Diamond.
        event.entityItem.item = <item:minecraft:diamond>;
    }
});