# ZenScript - the MineTweaker programming language

MineTweaker uses the newly developed scripting language ZenScript. The goal of
ZenScript is to make it possible for users with no coding experience wo write
simple scripts while allowing advanced programmers to efficiently write large
projects.

Additionally, it is possible to tightly integrate ZenScript within custom java
applications and wire up existing classes with a few annotations and have them
made available automatically to the scripting system. In the context of
MineTweaker, that means that adding functions and mod support has become much
easier than before.

# Storing scripts

Scripts in MineTweaker can be stored in two locations:

- In the scripts directory in your minecraft folder
- In the scripts directory in your world folder (the savegame or multiplayer world)

__Scripts stored in the scripts directory will be applied to all games hosted by
that minecraft instance.__ That is, all singleplayer games will inherit those
scripts, as well as all multipler games hosted by it.

__Scripts stored in the savegame directory will be applied to that specific game
only.__ Scripts in the global directory can also be overridden by placing a file
with identical name in the savegame scripts directory.

In multiplayer worlds, scripts stored on the connecting clients are ignored.
The server will send its scripts to all connected clients and execute them
there. A modpack could thus be customed on a per-server basis and be adjusted
without having to redistribute a new modpack. Even a reconnect is unnecessary
as scripts can be reloaded on-the-fly with the reload command.

Scripts can be stored in these directories in three different ways:

- Single scripts can be stored as a .zs file.
- Multiple script files can be packaged into a .zip file.
- Multiple script files can be stored into a subdirectory (useful for development)

When multiple scripts are stored together into a .zip file or subdirectory, they
can access each other's functions. Independent scripts run in a completely different
namespace, that is, they won't see each other's symbols or functions.

The order of execution between scripts is undefined. They may be executed in any
order. When writing multi-file scripts, it is recommended to store functionality
in different functions and have a single main script execute those functions.

This zip file illustrates example contents of a script directory. [INSERT LINK]

# General script structure

Scripts follow this structure:

- A list of imports (if any)
- A mix between statements and function definitions

The following example script will add a new recipe:

```
recipes.addShaped(<minecraft:stick> * 8, [[<minecraft:wood>, <minecraft:wood>, <minecraft:wood>]]);
```
