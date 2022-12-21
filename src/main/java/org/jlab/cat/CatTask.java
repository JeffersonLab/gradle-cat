package org.jlab.cat;

import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

import java.io.*;
import java.util.Iterator;

/**
 * Concatenation Task
 */
public abstract class CatTask extends AbstractCatTask {

    /**
     * The unordered input files, only used if getUnordered() returns true.
     *
     * @return The ConfigurableFileTree
     */
    @InputFiles
    public abstract ConfigurableFileTree getInput();

    public Iterator<File> getInputIterator() {
        return getInput().iterator();
    }
}
