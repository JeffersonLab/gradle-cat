package org.jlab.cat;

import org.gradle.api.Project;
import org.gradle.api.file.*;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.*;

public class CatPluginTest {

    private CatTask task;
    private OrderedCatTask orderedTask;
    private Project project;

    @Before
    public void setup() {
        File projectRoot = new File(".").getAbsoluteFile();
        project = ProjectBuilder.builder().withProjectDir(projectRoot).withGradleUserHomeDir(new File(System.getProperty("java.io.tmpdir"))).build();
        project.getPluginManager().apply("org.jlab.cat");
        task = (CatTask) project.getTasks().getByName("cat");
        orderedTask = (OrderedCatTask) project.getTasks().getByName("ordered-cat");
    }

    @Test
    public void orderedTest() throws IOException {
        File buildDir = project.getBuildDir();
        ProjectLayout layout = project.getLayout();
        Directory projDir = layout.getProjectDirectory();
        Directory resDir = projDir.dir("src/test/resources");
        File output = new File(buildDir, "testing-output");

        orderedTask.getOutput().fileValue(output);

        RegularFile file1 = resDir.file("test1.txt");
        RegularFile file2 = resDir.file("test2.txt");

        orderedTask.getInput().add(file1);
        orderedTask.getInput().add(file2);

        orderedTask.run();

        String expected = "ABC" + System.lineSeparator() + "DEF" + System.lineSeparator();

        String actual = Files.readString(output.toPath());

        assertEquals(expected, actual);
    }


    @Test
    public void reverseOrderWithFileTreeTest() throws IOException {
        File buildDir = project.getBuildDir();
        ProjectLayout layout = project.getLayout();
        Directory projDir = layout.getProjectDirectory();
        File output = new File(buildDir, "testing-reverse-output");

        orderedTask.getOutput().fileValue(output);

        ConfigurableFileTree tree = project.fileTree("src/test/resources");
        tree.include("**/*.txt");
        Set<File> fileSet = tree.getFiles();
        List<File> fileList = new ArrayList<>(fileSet);
        Collections.sort(fileList);
        Collections.reverse(fileList);

        fileList.forEach(f -> orderedTask.getInput().add(projDir.file(f.getPath())));

        orderedTask.run();

        String expected = "DEF" + System.lineSeparator() + "ABC" + System.lineSeparator();

        String actual = Files.readString(output.toPath());

        assertEquals(expected, actual);
    }

    @Test
    public void unorderedTest() throws IOException {
        File buildDir = project.getBuildDir();
        File output = new File(buildDir, "testing-unordered-output");

        task.getOutput().fileValue(output);

        task.getInput().from("src/test/resources").include("**/*.txt");

        task.run();

        String possibility1 = "ABC" + System.lineSeparator() + "DEF" + System.lineSeparator();
        String possibility2 = "DEF" + System.lineSeparator() + "ABC" + System.lineSeparator();


        String actual = Files.readString(output.toPath());

        assertTrue(possibility1.equals(actual) || possibility2.equals(actual));
    }
}
