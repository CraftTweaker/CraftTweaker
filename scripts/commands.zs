#loader setupCommon

import crafttweaker.api.commands.custom.CustomCommands;
import crafttweaker.api.commands.custom.MCCommand;
import crafttweaker.api.commands.custom.MCSuggestionProvider;


var cmd = CustomCommands.literal("myCommand").executes(new MCCommand(context => {
	context.getSource().sendFeedback("I was called!", true);
	return 0;
}));


CustomCommands.registerCommand(cmd);

var cmd2 = CustomCommands.literal("myOtherCommand").then(
    CustomCommands.argument("someArgument")
        .suggests(new MCSuggestionProvider((context, builder) => builder.suggest(builder.getRemaining() + builder.getRemaining()).build()))
        .executes(new MCCommand(context => {
                    context.getSource().sendFeedback("The parameter was " + context.getArgument("someArgument"), true);
                    return 0;
                }))
);

CustomCommands.registerCommand(cmd2);