package org.jlab.cat;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.RegularFileProperty;

import java.io.File;

public class CatPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        CatTask task = project.getTasks().create("cat", CatTask.class);

        File projectDir = project.getProjectDir();
        File srcDir = new File(projectDir, "src");

        File defaultOutput = new File(projectDir, "build/cat-output");
        ConfigurableFileTree defaultInput = project.fileTree(srcDir);
        defaultInput.include("**/*.txt");

        task.getOutput().fileValue(defaultOutput);
        task.getInput().from(defaultInput);
    }
}
