package org.jlab.cat;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.*;
import java.util.Iterator;

/**
 * Concatenation Task
 */
public abstract class CatTask extends DefaultTask {

    /**
     * The input files.
     *
     * @return The input files
     */
    @InputFiles
    public abstract ConfigurableFileTree getInput();

    /**
     * The output file.
     *
     * @return The output file
     */
    @OutputFile
    public abstract RegularFileProperty getOutput();

    /**
     * The concatenation task action.
     *
     * @throws IOException If unable to concatenate files
     */
    @TaskAction
    public void run() throws IOException {
        File outFile = getOutput().getAsFile().get();

        try(PrintWriter writer = new PrintWriter(outFile)) {
            for (Iterator<File> it = getInput().iterator(); it.hasNext(); ) {
                File f = it.next();
                try(BufferedReader br = new BufferedReader(new FileReader(f))) {

                    String line = br.readLine();
                    while (line != null) {
                        writer.println(line);
                        line = br.readLine();
                    }
                }
            }
        }
    }
}
