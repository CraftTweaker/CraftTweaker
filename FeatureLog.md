# Feature log

A uniform place to keep track of new features of the 1.17 port.

1) Fabric support
2) IData visitors
3) Log4J logger (well this was timed badly😬)
4) default script priority is now 0
5) ItemStack tag is now a MapData and is nullable.
6) New `IItemStack#modify` to get rid of partial code duplication
7) Tags can be iterated directly instead of having to call `.elements`

```zenscript
for type in <tag:entity_types:minecraft:axolotl_always_hostiles> {
    println(type.descriptionId + " can summon: " + type.canSummon);
}
```
