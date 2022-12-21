package org.jlab.cat;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Concatenation Plugin
 */
public class CatPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        CatTask task = project.getTasks().create("cat", CatTask.class);
    }
}
