package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.mc1120.game.MCGame;
import crafttweaker.mc1120.util.CraftTweakerPlatformUtils;

public class ActionSetTranslation implements IAction {
        
        private final String lang;
        private final String key;
        private final String text;
        
        public ActionSetTranslation(String lang, String key, String text) {
            this.lang = lang;
            this.key = key;
            this.text = text;
        }
        
        @Override
        public void apply() {
            if(lang == null || CraftTweakerPlatformUtils.isLanguageActive(lang)) {
                MCGame.getTRANSLATIONS().put(key, text);
            }
        }
        
        @Override
        public String describe() {
            return "Setting localization for the key: " + key + " to " + text;
        }
        
    }