package org.jlab.cat;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CatPluginTest {

    private Task task;
    private Project project;

    @Before
    public void setup() {
        File projectRoot = new File(".").getAbsoluteFile();
        project = ProjectBuilder.builder().withProjectDir(projectRoot).build();
        project.getPluginManager().apply("org.jlab.cat");
        task = project.getTasks().getByName("cat");
    }

    @Test
    public void checkTaskType() {
        assertTrue(task instanceof CatTask);
    }

    @Test
    public void checkExecution() throws IOException {
        ((CatTask) task).run();

        File output = new File (project.getBuildDir(), "cat-output");

        String expected = "ABC" + System.lineSeparator() + "DEF" + System.lineSeparator();

        assertEquals(expected, Files.readString(output.toPath()));
    }
}
