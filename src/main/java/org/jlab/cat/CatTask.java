package org.jlab.cat;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.*;
import java.util.Iterator;

public abstract class CatTask extends DefaultTask {

    @InputFiles
    public abstract FileCollection getInput();

    @OutputFile
    public abstract RegularFileProperty getOutput();

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
