import crafttweaker.api.food.FoodProperties;
import crafttweaker.api.entity.effect.MobEffectInstance;

var food = FoodProperties.create(1, 2);
food = food.setIsMeat(true);
food = food.setCanAlwaysEat(true);
food = food.setIsFastFood(true);
food = food.addEffect(new MobEffectInstance(<mobeffect:minecraft:haste>, 100, 2), 1);
<item:minecraft:diamond>.food = food;