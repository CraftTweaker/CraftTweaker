/*
    A Sequence is a set of tasks that can be composed and ran.

    With a Sequence, you can run a task, wait a set amount of ticks, and then run another task,
    without having to worry about the boilerplate that would usually come with doing that.

    Sequences are *currently* not persisted, this means that if a server stops while a sequence is running,
    then it will **not** be restarted, so while you can make a sequence wait 5 in-game days before another task,
    if the server restarts while it is waiting for those days, it will not be resumed at that step.

    You can achieve some persistence with tasks by making use of SavedData, an example of that will be below.

    This file will have examples for Cauldron interactions, SavedData and obviously Sequences
*/

import crafttweaker.api.data.IData;
import crafttweaker.api.world.InteractionResult;
import crafttweaker.api.entity.MobSpawnType;
import crafttweaker.api.util.sequence.SequenceType;
import crafttweaker.api.world.Level;
import crafttweaker.api.world.ServerLevel;
import crafttweaker.api.data.BoolData;

// The following snippet will run a sequence when an empty cauldron is interacted with while the player is holding a dirt block
// The sequence that is ran will check if the block has redstone power, if it does not, it will tell the player that it needs
// redstone, then it will wait until it has redstone power and then wait 20 ticks before spawning fireworks.
cauldron.addEmptyInteraction(<item:minecraft:dirt>, (blockState, cLevel, bPos, playerIn, hand, stack) => {

    // We only want to run our sequence on the server *thread* (note, a single player world does have a server thread).
    if !cLevel.isClientSide {
            // We need to store the parameters that we want to use into local variables before we can use them (this is a bug in the ZenCode engine that will be fixed).
            var pos = bPos;
            var block = blockState;
            var player = playerIn;
            // Starts a SequenceBuilder
            cLevel.sequence()
                // Run a task
                .run(level => {
                    // Check if the redstone signal at the position of the cauldron is 0
                    if level.getBestNeighborSignal(pos) == 0 {
                        // Tell the player that it needs redstone
                        player.sendMessage("Please provide redstone power!");
                    }
                })
                // Sleep until the condition is met
                .sleepUntil(level => {
                    // Check if the block has a redstone signal greater than 0
                    return level.getBestNeighborSignal(pos) > 0;
                })
                // Sleep for 20 ticks
                .sleep(20)
                // Run immediately after the sleep is done ('then' is an alias for 'run', you can use whichever sounds better and is easier to read)
                .then(level => {
                    // Stores the data for the entity
                    val data = {EntityTag: {LifeTime:10,FireworksItem:{id:"firework_rocket",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Trail:1,Colors:[2651799],FadeColors:[14602026, 6719955, 12801229]}],Flight:1}}}}} as IData;
                    // Spawn a firework rocket above the cauldron.
                    <entitytype:minecraft:firework_rocket>.spawn(level, data, null, null, pos.above(), MobSpawnType.TRIGGERED, false, true);
                })
                // Start the sequence
                .start();
    }
    // return InteractionResult.SUCCESS on the server, but CONSUME on the client.
    return InteractionResult.sidedSuccess(cLevel.isClientSide);
});


// The following snippet will run a sequence when an empty cauldron is interacted with while the player is holding a diamond
// The sequence that is ran will wait until it is raining, then wait until it is not raining, then wait until it is raining again before
// running a task.
// This sequence uses SavedData to store state, so the state of the sequence will survive restarts, but the sequence itself will not.
// To test this, interact with the cauldron while holding a diamond, set the weather to rain, set the weather to clear, leave the world
// rejoin the world, and then interact with the cauldron while holding a diamond, you should see "Wait for rain one more time!" in chat.
// So the state is saved, but the sequence still needs to be triggered again.
cauldron.addEmptyInteraction(<item:minecraft:diamond>, (blockState, cLevel, bPos, playerIn, hand, stack) => {

    // We only want to run our sequence on the server *thread* (note, a single player world does have a server thread).
    // We also want to ensure that the given level is a ServerLevel, so we can access the server.
    if !cLevel.isClientSide && cLevel is ServerLevel {
            // We need to store the parameters that we want to use into local variables before we can use them (this is a bug in the ZenCode engine that will be fixed).
            var pos = bPos;
            var block = blockState;
            var player = playerIn;

            // Gets the overworld saved data.
            // The overworld saved data is always available, making it ideal to store data that needs to be accessed whenever.
            var savedData = (cLevel as ServerLevel).server.overworldData.data;
            // Starts a SequenceBuilder
            cLevel.sequence()
                // Run a task, checking if it is raining or not
                .run(level => {
                    // Check if the saved data contains a key with the name "first-<cauldron position>" and if the value for that key is true.
                    // The cauldron position is used to ensure that multiple cauldrons in the world can run this sequence at the same time.
                    if(("first-" + pos.asLong()) in savedData && savedData["first-" + pos.asLong()] as bool) {
                        // if this sleep condition has ran before, lets return without running any other code in this function.
                        return;
                    }
                    // check if it is raining
                    var raining = level.raining;
                    // If it is not raining, lets tell the player to wait for rain.
                    if !raining {
                        player.sendMessage("Wait for rain!");
                    } else {
                        // If it is raining and this condition has passed, set that in the saved data
                        savedData.put("first-" + pos.asLong(), true);
                    }
                })
                // Sleep until the condition is met
                .sleepUntil(level => {
                    // Check if the saved data contains a key with the name "first-<cauldron position>" and if the value for that key is true.
                    // The cauldron position is used to ensure that multiple cauldrons in the world can run this sequence at the same time.
                    if(("first-" + pos.asLong()) in savedData && savedData["first-" + pos.asLong()] as bool) {
                        // if this sleep condition has ran before, lets return true without having to wait for it again.
                        return true;
                    }
                    // return if it was raining or not.
                    return level.raining;
                })
                // run immediately after the sleep is done, checking if it is raining or not
                .run(level => {
                    // Check if the saved data contains a key with the name "second-<cauldron position>" and if the value for that key is true.
                    // The cauldron position is used to ensure that multiple cauldrons in the world can run this sequence at the same time.
                    if(("second-" + pos.asLong()) in savedData && savedData["second-" + pos.asLong()] as bool) {
                        // if this sleep condition has ran before, lets return without running any other code in this function.
                        return;
                    }
                    // check if it is raining
                    var raining = level.raining;
                    // If it is not raining, lets tell the player to wait for rain.
                    if raining {
                       player.sendMessage("Wait for rain to go away!");
                    } else {
                        // If it is raining and this condition has passed, set that in the saved data
                        savedData.put("second-" + pos.asLong(), true);
                    }
                })
                .sleepUntil(level => {

                    // Check if the saved data contains a key with the name "second-<cauldron position>" and if the value for that key is true.
                    // The cauldron position is used to ensure that multiple cauldrons in the world can run this sequence at the same time.
                    if(("second-" + pos.asLong()) in savedData && savedData["second-" + pos.asLong()] as bool) {
                        // if this sleep condition has ran before, lets return true without having to wait for it again.
                        return true;
                    }

                    // return if it was raining or not.
                    return !level.raining;
                })
                // run immediately after the sleep is done, checking if it is raining or not
                .run(level => {
                    // Check if the saved data contains a key with the name "third-<cauldron position>" and if the value for that key is true.
                    // The cauldron position is used to ensure that multiple cauldrons in the world can run this sequence at the same time.
                    if(("third-" + pos.asLong()) in savedData && savedData["third-" + pos.asLong()] as bool) {
                        // if this sleep condition has ran before, lets return without running any other code in this function.
                        return;
                    }
                    // check if it is raining
                    var raining = level.raining;
                    // If it is not raining, lets tell the player to wait for rain.
                    if !raining {
                        player.sendMessage("Wait for rain one more time!");
                    } else {
                        // If it is raining and this condition has passed, set that in the saved data
                        savedData.put("third-" + pos.asLong(), true);
                    }
                })
                .sleepUntil(level => {

                    // Check if the saved data contains a key with the name "third-<cauldron position>" and if the value for that key is true.
                    // The cauldron position is used to ensure that multiple cauldrons in the world can run this sequence at the same time.
                    if(("third-" + pos.asLong()) in savedData && savedData["third-" + pos.asLong()] as bool) {
                        // if this sleep condition has ran before, lets return true without having to wait for it again.
                        return true;
                    }
                    // return if it was raining or not.
                    return level.raining;
                })
                .then(level => {
                    // Send a message to the player
                    player.sendMessage("It's a terrible day for rain");

                    // Reset the saved data so that this cauldron can be used for this sequence again.
                    savedData.put("first-" + pos.asLong(), false);
                    savedData.put("second-" + pos.asLong(), false);
                    savedData.put("third-" + pos.asLong(), false);
                })
                // Start the sequence
                .start();
    }
    // return InteractionResult.SUCCESS on the server, but CONSUME on the client.
    return InteractionResult.sidedSuccess(cLevel.isClientSide);
});


// Sequence tasks can also use a SequenceContext, which can provide custom data to the sequence as well as allowing a sequence to be stopped.
// The custom data provided to a sequence can be any type, but for the sake of this example, lets create a custom type.

// Create a public class called 'MySequenceData'
public class MySequenceData {

    // A public string version that has a getter (we can call mySequenceDataInstance.version)
    public var version as string : get;

    // A constructor so we can make our object.
    public this(version as string) {
        // Set the variable version above to the version passed into the constructor.
        this.version = version;
    }
}


// The following snippet will run a sequence when an empty cauldron is interacted with while the player is holding a stick
// The sequence that is ran will check if the block has redstone power, if it does not have redstone power, the sequence will be stopped.
// If the block does have redstone power, then the sequence will run as normal and spawn fireworks.
// This snippet will also make use of the type that we defined above.
cauldron.addEmptyInteraction(<item:minecraft:stick>, (blockState, cLevel, bPos, playerIn, hand, stack) => {

    // We only want to run our sequence on the server *thread* (note, a single player world does have a server thread).
    if !cLevel.isClientSide {
            // We need to store the parameters that we want to use into local variables before we can use them (this is a bug in the ZenCode engine that will be fixed).
            var pos = bPos;
            var block = blockState;
            var player = playerIn;
            // Starts a SequenceBuilder, providing it with custom data. The type inside the <> is the type of the data to be passed in.
            cLevel.sequence<MySequenceData>(new MySequenceData("1.0.0"))
                // Run a task with context
                .run((level, context) => {
                    // print the version from the data passed into the sequence.
                    println(context.data.version);

                    // Check if the redstone signal at the position of the cauldron is 0
                    if level.getBestNeighborSignal(pos) == 0 {
                        // Stops the sequence, no other tasks will be ran after this task finishes.
                        context.stop();
                    }
                })
                // Run immediately after the previous task is done ('then' is an alias for 'run', you can use whichever sounds better and is easier to read)
                .then(level => {
                    // Stores the data for the entity
                    val data = {EntityTag: {LifeTime:10,FireworksItem:{id:"firework_rocket",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Trail:1,Colors:[2651799],FadeColors:[14602026, 6719955, 12801229]}],Flight:1}}}}} as IData;
                    // Spawn a firework rocket above the cauldron.
                    <entitytype:minecraft:firework_rocket>.spawn(level, data, null, null, pos.above(), MobSpawnType.TRIGGERED, false, true);
                })
                // Start the sequence
                .start();
    }
    // return InteractionResult.SUCCESS on the server, but CONSUME on the client.
    return InteractionResult.sidedSuccess(cLevel.isClientSide);
});
