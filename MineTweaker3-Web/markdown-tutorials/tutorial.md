# MineTweaker tutorial: Getting started

Welcome to MineTweaker! MineTweaker allows you to change MineCraft recipes and
make tweaks to many kinds of mod recipes and properties.

In order to allow a high degree of customization, a specific language has been
developed that's concise very easy to get started with. No prior coding knowledge
is assumed - simply follow the tutorials and you'll be fine.

For the non-coder, tutorials are available for all kinds of different tasks. For
the more seasoned programmer (or the curious), full documentation is available
about the scripting language and all the functions and classes it provides.

# Our first script

In order to get started with MineTweaker, you'll have to store your script file
at the right place. Two directories are recognized by MineTweaker and will
automatically have all recipes loaded from them:

- The scripts directory inside your minecraft folder
- The scripts directory inside your savegame

__Scripts stored in the global scripts directory will be applied to all games hosted by
that minecraft instance.__ That is, all singleplayer games will inherit those
scripts, as well as all multiplayer games hosted by it.

__Scripts stored in the savegame directory will be applied to that specific game
only.__ Scripts in the global directory can also be overridden by placing a file
with identical name in the savegame scripts directory.

In multiplayer worlds, scripts stored on the connecting clients are ignored.
The server will send its scripts to all connected clients and execute them
there. A modpack could thus be customed on a per-server basis and be adjusted
without having to redistribute a new modpack. Even a reconnect is unnecessary
as scripts can be reloaded on-the-fly with the reload command.

Now, let's get started. Go to the script directory in your .minecraft folder
(create it, if it doesn't exist yet) and create a new empty text file. Rename it
to have the extension '.zs' (this is the extension recognized by MineTweaker and
only files with that extension will be recognized).

Now enter the following line of code in that text file:

```
print "Hello World!"
```

Save it and run minecraft. As soon as you enter a game the console should print
"Hello World!". Your first script has executed!

Now in the console enter the command \minetweaker reload (or its shorthand, \mt reload).
The message will now appear again. MineTweaker has automatically reloaded the script
for you. This will become our most useful feature during the tutorial, as you will
be reloading the script using that command each time we have a new file.

We can now get into changing recipes. It is recommended to have a mod such as
NEI installed so you can monitor changed recipes. Add the following text to
your script:

```
recipes.remove(<minecraft:stick>);
```

Reload the script and try to craft the stick from two planks. It won't work!
MineTweaker has removed the recipe for you. 

Now remove that line and reload your script. Try to craft it again - this time
crafting should work again.

What happened? When executing the script with the remove command, MineTweaker
has remembered the removal. When you said "reload", MineTweaker will first undo
that action before running the new script. As a consequence, if you make
changes to a script, the recipes will be refreshed properly rather than duplicated.

