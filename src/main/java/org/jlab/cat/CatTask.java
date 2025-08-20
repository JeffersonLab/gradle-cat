package org.jlab.cat;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Transformer;
import org.gradle.api.file.*;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.*;

/** Concatenation Task that concatenates files in the order provided. */
public abstract class CatTask extends DefaultTask {

  @InputFiles
  abstract ListProperty<RegularFile> getInput();

  @OutputFile
  abstract RegularFileProperty getOutput();

  /**
   * The concatenation task action.
   *
   * @throws IOException If unable to concatenate files
   */
  @TaskAction
  public void run() throws IOException {
    File outFile = getOutput().getAsFile().get();

    try (PrintWriter writer = new PrintWriter(outFile)) {
      for (Iterator<RegularFile> it = getInput().get().iterator(); it.hasNext(); ) {
        File f = it.next().getAsFile();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

          String line = br.readLine();
          while (line != null) {
            writer.println(line);
            line = br.readLine();
          }
        }
      }
    }
  }

  /**
   * Set the provided list of input files as paths relative to the project root.
   *
   * @param paths The list of paths
   */
  public void from(String... paths) {
    Project project = getProject();
    ProjectLayout layout = project.getLayout();
    Directory dir = layout.getProjectDirectory();

    ArrayList<RegularFile> list = new ArrayList<>();

    for (String path : paths) {
      RegularFile f = dir.file(path);

      list.add(f);
    }

    getInput().set(list);
  }

  /**
   * Lazily sets the value of the input to an unordered ConfigurableFileTree rooted at the given
   * path.
   *
   * @param path Root path in the project
   * @return A ConfigurableFileTree that can be chained further
   */
  public ConfigurableFileTree from(String path) {
    Project project = getProject();
    ConfigurableFileTree tree = project.fileTree(path);
    ProjectLayout layout = project.getLayout();
    Directory projectDir = layout.getProjectDirectory();

    Provider<Set<FileSystemLocation>> locationProvider = tree.getElements();

    Transformer<Set<RegularFile>, Set<FileSystemLocation>> transformer =
        new Transformer<>() {
          @Override
          public Set<RegularFile> transform(Set<FileSystemLocation> locations) {
            HashSet<RegularFile> set = new HashSet<>();

            for (FileSystemLocation l : locations) {
              File f = l.getAsFile();
              if (f.isFile()) {
                set.add(projectDir.file(f.getPath()));
              }
            }

            return set;
          }
        };

    Provider<Set<RegularFile>> regularFileProvider = locationProvider.map(transformer);

    getInput().set(regularFileProvider);

    return tree;
  }
}
