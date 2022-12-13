package org.jlab.cat;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

public class CatPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        CatTask task = project.getTasks().create("cat", CatTask.class);

        File buildDir = project.getBuildDir();
        File defaultOutput = new File(buildDir, "cat-output");

        task.getOutput().fileValue(defaultOutput);
        task.getInput().from("src/test/resources").include("**/*.txt");
    }
}
