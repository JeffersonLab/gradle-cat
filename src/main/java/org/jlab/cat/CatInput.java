package org.jlab.cat;

import org.gradle.api.Project;
import org.gradle.api.Transformer;
import org.gradle.api.file.*;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.InputFiles;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class CatInput {

    @InputFiles
    abstract ListProperty<RegularFile> getList();

    /**
     * Lazily sets the value of the input to an unordered ConfigurableFileTree.
     *
     * @param path Path in the project
     * @return A ConfigurableFileTree that can be chained further
     */
    public ConfigurableFileTree from(String path, Project project) {
        ConfigurableFileTree tree = project.fileTree(path);
        ProjectLayout layout = project.getLayout();
        Directory projectDir = layout.getProjectDirectory();

        Provider<Set<FileSystemLocation>> locationProvider = tree.getElements();

        Transformer<Set<RegularFile>, Set<FileSystemLocation>> transformer = new Transformer<>() {
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

        getList().set(regularFileProvider);

        return tree;
    }
}
