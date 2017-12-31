package crafttweaker.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * @author Stan
 */
public class MergeJarsTask extends DefaultTask {

    @InputFile
    public File mainInput;

    @InputFile
    public File[] inputs;

    @OutputFile
    public File output;

    @TaskAction
    public void doTask() {
        System.out.println("Main: " + mainInput.toString());
        System.out.println("Inputs: " + inputs.length);
        System.out.println("Output: " + output.toString());
    }
}
