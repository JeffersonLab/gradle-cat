package org.jlab.cat;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.*;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.*;

import java.io.*;
import java.util.Iterator;

/**
 * Concatenation Task that concatenates files in the order provided.
 */
public abstract class CatTask extends DefaultTask {


    @InputFiles
    abstract Property<CatInput> getInput();

    @OutputFile
    abstract RegularFileProperty getOutput();

    /**
     * The concatenation task action.
     *
     * @throws IOException If unable to concatenate files
     */
    @TaskAction
    public void run() throws IOException {
        File outFile = getOutput().getAsFile().get();

        try(PrintWriter writer = new PrintWriter(outFile)) {
            for (Iterator<RegularFile> it = getInput().get().getList().get().iterator(); it.hasNext(); ) {
                File f = it.next().getAsFile();
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
