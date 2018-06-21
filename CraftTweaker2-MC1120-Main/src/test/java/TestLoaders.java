import crafttweaker.CraftTweakerAPI;

public class TestLoaders {
    
    public static void main(String[] args) {
        CraftTweakerAPI.tweaker.addLoader("test1");
        CraftTweakerAPI.tweaker.addLoader("test2");
        CraftTweakerAPI.tweaker.addLoader("test1", "test2");
        CraftTweakerAPI.tweaker.addLoader("test3", "test4");
        CraftTweakerAPI.tweaker.addLoader("test1", "test3");
        
    
        CraftTweakerAPI.tweaker.loadScript(false, "test1");
        CraftTweakerAPI.tweaker.loadScript(false, "test4");
    }
}
