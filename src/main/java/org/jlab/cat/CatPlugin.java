package org.jlab.cat;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

/**
 * Concatenation Plugin
 */
public class CatPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        TaskProvider<CatTask> task = project.getTasks().register("cat", CatTask.class);
        CatExtension extension = project.getExtensions().create("cat", CatExtension.class);

        task.configure(new Action<CatTask>() {
            @Override
            public void execute(CatTask task) {
                task.getInput().set(extension.getInput());
                task.getOutput().set(extension.getOutput());
            }
        });
    }
}
