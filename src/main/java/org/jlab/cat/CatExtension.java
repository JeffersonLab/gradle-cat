package org.jlab.cat;

import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;

public abstract class CatExtension {
    @InputFiles
    public abstract ListProperty<RegularFile> getInput();

    @OutputFile
    public abstract RegularFileProperty getOutput();
}
