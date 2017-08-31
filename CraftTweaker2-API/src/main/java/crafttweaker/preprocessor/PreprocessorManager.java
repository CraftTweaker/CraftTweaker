package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;
import crafttweaker.util.*;

import java.io.*;
import java.util.*;

/**
 * @author BloodWorkXGaming
 */
public class PreprocessorManager {
    
    /** List of all event subscribers*/
    private final EventList<CrTScriptLoadEvent> SCRIPT_LOAD_EVENT_EVENT_LIST = new EventList<>();
    
    /** This registry is filled with dummy events that are callable */
    private HashMap<String, PreprocessorFactory> registeredPreprocessorActions = new HashMap<>();
    
    /** List of files that should Ignore the execution */
    public Set<String> fileIgnoreExecuteList = new HashSet<>();
    public Set<String> classIgnoreExecuteList = new HashSet<>();
    
    // file > action event
    public HashMap<String, List<IPreprocessor>> preprocessorActionsPerFile = new HashMap<>();
    
    public void registerPreprocessorAction(String name, PreprocessorFactory preprocessorFactory){
        registeredPreprocessorActions.put(name, preprocessorFactory);
    }
    
    /**
     * Checks the given line for preprocessors
     * @param scriptFile file which is being checked
     * @param line Line to check
     * @param lineIndex index of the file in the current line
     * @return returns whether it found a preprocessor or not
     */
    private IPreprocessor checkLine(ScriptFile scriptFile, String line, int lineIndex){
        String s = line.trim();
        
        if (s.toCharArray().length > 0 && s.toCharArray()[0] == '#'){
            s = s.substring(1);
            String[] splits = s.split(" ");
            if (splits.length > 0){
                PreprocessorFactory preprocessorFactory = registeredPreprocessorActions.get(splits[0]);
                
                if (preprocessorFactory != null){
    
                    IPreprocessor preprocessor = preprocessorFactory.createPreprocessor(scriptFile.getName(), line, lineIndex);
    
                    preprocessor.executeActionOnFind(scriptFile);
                    addPreprocessorToFileMap(scriptFile.getName(), preprocessor);
                    return preprocessor;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Adds the preprocessor Command to the map
     * @param filename Key for map
     * @param preprocessor Value to add to map
     */
    private void addPreprocessorToFileMap(String filename, IPreprocessor preprocessor){
        if (preprocessorActionsPerFile.containsKey(filename)){
            preprocessorActionsPerFile.get(filename).add(preprocessor);
        }else {
            List<IPreprocessor> l = new ArrayList<>();
            l.add(preprocessor);
            preprocessorActionsPerFile.put(filename, l);
        }
    }
    
    /**
     * Checks the given inputstream for preprocessors
     * @param scriptFile ScriptFile object of file to check,
     *                   contains all important information about streams and names
     */
    public List<IPreprocessor> checkFileForPreprocessors(ScriptFile scriptFile) {
        List<IPreprocessor> preprocessorList = new ArrayList<>();
        
        String filename = scriptFile.getName();
        
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(scriptFile.open(), "UTF-8"));

            String line;
            int lineIndex = -1;
            
            while ((line = reader.readLine()) != null) {
                lineIndex++;
                IPreprocessor preprocessor = checkLine(scriptFile, line, lineIndex);
                if (preprocessor != null){
                    preprocessorList.add(preprocessor);
                }
            }
            
            reader.close();
        } catch(IOException e) {
            CraftTweakerAPI.logError("Could not read preprocessor functions in " + filename);
            e.printStackTrace();
        }
        
        executePostActions(scriptFile);
        return preprocessorList;
    }
    
    /**
     * Actions which are getting called after all preprocessor lines have been collected
     * @param scriptFile scriptFile which is being affected.
     *                   Changing this will affect the way script are getting loaded
     */
    private void executePostActions(ScriptFile scriptFile){
        for(Map.Entry<String, List<IPreprocessor>> stringListEntry : preprocessorActionsPerFile.entrySet()) {
            for(IPreprocessor preprocessor : stringListEntry.getValue()) {
                preprocessor.executeActionOnFinish(scriptFile);
            }
        }
    }
    
    public static void registerOwnPreprocessors(PreprocessorManager manager){
        manager.registerLoadEventHandler(event -> {
            if (event.affectingPreprocessorsList != null){
                for(IPreprocessor preprocessorActionBase : event.affectingPreprocessorsList) {
                    System.out.println(event.fileName + ": " + preprocessorActionBase);
                    if (preprocessorActionBase instanceof NoRunPreprocessor){
                        event.isExecuteCanceled = true;
                        event.isParsingCanceled = true;
                    }
                }
            }
        });
        
        manager.registerPreprocessorAction("debug", DebugPreprocessor::new);
        manager.registerPreprocessorAction("ignoreBracketErrors", IgnoreBracketErrorPreprocessor::new);
        manager.registerPreprocessorAction("norun", NoRunPreprocessor::new);
    
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
