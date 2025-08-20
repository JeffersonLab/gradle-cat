package org.jlab.cat;

import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;

/** Gradle Extension for the concatenation plugin. */
public abstract class CatExtension {
  /**
   * Input files (ordered lazy list).
   *
   * @return The input files
   */
  @InputFiles
  public abstract ListProperty<RegularFile> getInput();

  /**
   * Output file (lazy non-directory/non-device File).
   *
   * @return The output file
   */
  @OutputFile
  public abstract RegularFileProperty getOutput();
}
