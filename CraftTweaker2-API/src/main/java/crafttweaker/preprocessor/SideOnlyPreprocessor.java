package crafttweaker.preprocessor;

import crafttweaker.api.network.NetworkSide;
import crafttweaker.runtime.ScriptFile;

/**
 * Preprocessor can be used as follows:
 * #sideonly sidename
 * Example:
 * #sideonly client
 * This will make scripts only being loaded when the loader is the specified side
 * of the network
 *
 * just leaving this loader away will cause it to be loaded on both sides
 *
 * @author BloodWorkXGaming
 */
public class SideOnlyPreprocessor extends PreprocessorActionBase{
    public static final String PREPROCESSOR_NAME = "sideonly";
    private NetworkSide side;
    
    public SideOnlyPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        
        String s = preprocessorLine.substring(PREPROCESSOR_NAME.length() + 1);
        String sideName = s.trim();
        
        switch(sideName) {
            case "client":
                side = NetworkSide.SIDE_CLIENT;
                break;
            case "server":
                side = NetworkSide.SIDE_SERVER;
                break;
            default:
                side = NetworkSide.INVALID_SIDE;
                break;
        }
        
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        scriptFile.setNetworkSide(side);
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
