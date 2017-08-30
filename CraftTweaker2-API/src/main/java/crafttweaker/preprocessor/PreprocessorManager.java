package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.util.*;

import java.io.*;
import java.util.*;

/**
 * @author BloodWorkXGaming
 */
public class PreprocessorManager {
    
    /** List of all event subscribers*/
    private final EventList<CrTScriptLoadEvent> SCRIPT_LOAD_EVENT_EVENT_LIST = new EventList<>();
    
    /**
     * This registry is filled with dummy events that are callable
     */
    private HashMap<String, PreprocessorActionBase> registeredPreprocessorActions = new HashMap<>();
    /**
     * List of files that should Ignore the execution
     */
    public Set<String> fileIgnoreExecuteList = new HashSet<>();
    public Set<String> classIgnoreExecuteList = new HashSet<>();
    
    // file > action event
    public HashMap<String, List<PreprocessorActionBase>> preprocessorActionsPerFile = new HashMap<>();
    
    public void registerPreprocessorAction(String name, PreprocessorActionBase preprocessorClass){
        registeredPreprocessorActions.put(name, preprocessorClass);
    }
    
    /**
     * Checks the given line for preprocessors
     * @param filename Name of the file
     * @param line Line to check
     * @param lineIndex index of the file in the current line
     * @return returns whether it found a preprocessor or not
     */
    private boolean checkLine(String filename, String line, int lineIndex){
        String s = line.trim();
        
        if (s.toCharArray().length > 0 && s.toCharArray()[0] == '#'){
            s = s.substring(1);
            String[] splits = s.split(" ");
            if (splits.length > 0){
                PreprocessorActionBase dummyAction = registeredPreprocessorActions.get(splits[0]);
                
                if (dummyAction != null){
                    
                    PreprocessorActionBase actionBase = dummyAction.createPreprocessor(filename, line, lineIndex);
    
                    actionBase.executeActionOnFind();
                    addPreprocessorToFileMap(filename, actionBase);
    
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Adds the preprocessor Command to the map
     * @param filename Key for map
     * @param actionBase Value to add to map
     */
    private void addPreprocessorToFileMap(String filename, PreprocessorActionBase actionBase){
        if (preprocessorActionsPerFile.containsKey(filename)){
            preprocessorActionsPerFile.get(filename).add(actionBase);
        }else {
            List<PreprocessorActionBase> l = new ArrayList<>();
            l.add(actionBase);
            preprocessorActionsPerFile.put(filename, l);
        }
    }
    
    /**
     * Checks the given inputstream for preprocessors
     * @param filename name of the file that is being checked
     * @param inputStream stream to check
     */
    public void checkFileForPreprocessors(String filename, InputStream inputStream) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            int lineIndex = -1;
            
            while ((line = reader.readLine()) != null) {
                lineIndex++;
                checkLine(filename, line, lineIndex);
            }
            
            reader.close();
        } catch(IOException e) {
            CraftTweakerAPI.logError("Could not read preprocessor functions in " + filename);
            e.printStackTrace();
        }
        
        executePostActions();
    }
    
    /**
     * Actions which are getting called after all preprocessor lines have been collected
     */
    public void executePostActions(){
        for(Map.Entry<String, List<PreprocessorActionBase>> stringListEntry : preprocessorActionsPerFile.entrySet()) {
            for(PreprocessorActionBase preprocessorActionBase : stringListEntry.getValue()) {
                preprocessorActionBase.executeActionOnFinish();
            }
        }
    }
    
    public static void registerOwnPreprocessors(PreprocessorManager manager){
        manager.registerLoadEventHandler(event -> {
            if (event.affectingPreprocessorsList != null){
                for(PreprocessorActionBase preprocessorActionBase : event.affectingPreprocessorsList) {
                    System.out.println(event.fileName + ": " + preprocessorActionBase);
                    if (preprocessorActionBase instanceof NoRunPreprocessor){
                        event.isExecuteCanceled = true;
                        event.isParsingCanceled = true;
                    }
                }
            }
        });
        
        manager.registerPreprocessorAction("debug", new DebugPreprocessor("", "", -1));
        manager.registerPreprocessorAction("ignoreBracketErrors", new IgnoreBracketErrorPreprocessor("", "", -1));
        manager.registerPreprocessorAction("norun", new NoRunPreprocessor("", "", -1));
    
    }

    public void registerLoadEventHandler(IEventHandler<CrTScriptLoadEvent> handler){
        SCRIPT_LOAD_EVENT_EVENT_LIST.add(handler);
    }
    
    public boolean postLoadEvent(CrTScriptLoadEvent event){
        SCRIPT_LOAD_EVENT_EVENT_LIST.publish(event);
        return event.isExecuteCanceled;
    }
    
    public void handleLoadEvents(CrTScriptLoadEvent event){
        if (event.isExecuteCanceled) fileIgnoreExecuteList.add(event.fileName);
        if (event.isExecuteCanceled) classIgnoreExecuteList.add(event.className);
    }
}
