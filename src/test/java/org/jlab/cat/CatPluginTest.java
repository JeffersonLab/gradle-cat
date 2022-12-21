package org.jlab.cat;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class CatPluginTest {

    private CatTask task;
    private Project project;

    @Before
    public void setup() {
        File projectRoot = new File(".").getAbsoluteFile();
        project = ProjectBuilder.builder().withProjectDir(projectRoot).withGradleUserHomeDir(new File(System.getProperty("java.io.tmpdir"))).build();
        project.getPluginManager().apply("org.jlab.cat");
        task = (CatTask) project.getTasks().getByName("cat");
    }

    @Test
    public void checkExecution() throws IOException {
        File buildDir = project.getBuildDir();
        File defaultOutput = new File(buildDir, "testing-output");

        task.getOutput().fileValue(defaultOutput);
        task.getInput().from("src/test/resources").include("**/*.txt");

        task.run();

        String expected1 = "ABC" + System.lineSeparator() + "DEF" + System.lineSeparator();
        String expected2 = "DEF" + System.lineSeparator() + "ABC" + System.lineSeparator();


        String actual = Files.readString(defaultOutput.toPath());

        assertTrue(expected1.equals(actual) || expected2.equals(actual));
    }
}
