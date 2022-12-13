package org.jlab.cat;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CatPluginTest {
    @Test
    public void addToProject() {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("org.jlab.cat");

        assertTrue(project.getTasks().getByName("cat") instanceof CatTask);
    }
}
