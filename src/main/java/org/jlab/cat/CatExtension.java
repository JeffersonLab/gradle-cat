package org.jlab.cat;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;

public abstract class CatExtension {
    @InputFiles
    public abstract Property<CatInput> getInput();

    @OutputFile
    public abstract RegularFileProperty getOutput();
}
