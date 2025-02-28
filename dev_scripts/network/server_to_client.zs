import crafttweaker.api.entity.type.player.ServerPlayer;
import crafttweaker.api.world.ItemInteractionResult;

cauldron.addEmptyInteraction(<item:minecraft:diamond_axe>, (blockState, level, pos, player, hand, stack) => {
    if player is ServerPlayer {
        println("sending to the client");
        network.sendTo(player as ServerPlayer, "axed_cauldron", {pos: {x: pos.x, y: pos.y, z: pos.z}});
    }
    return ItemInteractionResult.sidedSuccess(level.isClientSide);
});

network.onData("axed_cauldron", (data, context) => {
    println(data.getAsString());
});