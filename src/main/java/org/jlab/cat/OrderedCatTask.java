package org.jlab.cat;

import org.gradle.api.Project;
import org.gradle.api.Transformer;
import org.gradle.api.file.*;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.*;

import javax.annotation.Nullable;
import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Concatenation Task that concatenates files in the order provided.
 */
public abstract class OrderedCatTask extends AbstractCatTask {

    /**
     * The ordered input files.
     *
     * @return The ListProperty
     */
    @InputFiles
    abstract ListProperty<RegularFile> getInput();

    public ConfigurableFileTree from(String path) {
        Project project = getProject();
        ConfigurableFileTree tree = project.fileTree(path);
        ProjectLayout layout = project.getLayout();
        Directory projectDir = layout.getProjectDirectory();

        Provider<Set<FileSystemLocation>> locationProvider = tree.getElements();

        Transformer<Set<RegularFile>, Set<FileSystemLocation>> transformer = new Transformer<>() {
            @Override
            public Set<RegularFile> transform(Set<FileSystemLocation> locations) {
                HashSet<RegularFile> set = new HashSet<>();

                for(FileSystemLocation l: locations) {
                    File f = l.getAsFile();
                    if(f.isFile()) {
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

    public Iterator<File> getInputIterator() {
        return new Iterator<File>() {
            Iterator<RegularFile> internal = getInput().get().iterator();

            @Override
            public boolean hasNext() {
                return internal.hasNext();
            }

            @Override
            public File next() {
                return internal.next().getAsFile();
            }
        };
    }
}
