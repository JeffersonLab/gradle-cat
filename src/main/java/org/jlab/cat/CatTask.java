package org.jlab.cat;

import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

import java.io.*;
import java.util.Iterator;

/**
 * Concatenation Task that concatenates files in random order.  In situations where
 * order doesn't matter the ConfigurableFileTree input property is much easier to work with
 * compared to the ordered ListProperty alternative.
 */
public abstract class CatTask extends AbstractCatTask {

    /**
     * The unordered input files.
     *
     * @return The ConfigurableFileTree
     */
    @InputFiles
    public abstract ConfigurableFileTree getInput();

    public Iterator<File> getInputIterator() {
        return getInput().iterator();
    }
}
