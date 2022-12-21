package org.jlab.cat;

import org.gradle.api.Project;
import org.gradle.api.file.Directory;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
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
        ProjectLayout layout = project.getLayout();
        Directory projDir = layout.getProjectDirectory();
        Directory resDir = projDir.dir("src/test/resources");
        File defaultOutput = new File(buildDir, "testing-output");

        task.getOutput().fileValue(defaultOutput);

        RegularFile file1 = resDir.file("test1.txt");
        RegularFile file2 = resDir.file("test2.txt");

        task.getInput().add(file1);
        task.getInput().add(file2);

        task.run();

        String expected = "ABC" + System.lineSeparator() + "DEF" + System.lineSeparator();

        String actual = Files.readString(defaultOutput.toPath());

        assertEquals(expected, actual);
    }
}
