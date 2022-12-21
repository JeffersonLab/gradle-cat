package org.jlab.cat;

import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.tasks.*;

import java.io.*;
import java.util.Iterator;

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
