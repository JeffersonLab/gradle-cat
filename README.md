# gradle-cat [![CI](https://github.com/JeffersonLab/gradle-cat/actions/workflows/ci.yml/badge.svg)](https://github.com/JeffersonLab/gradle-cat/actions/workflows/ci.yml) [![Maven Central](https://badgen.net/maven/v/maven-central/org.jlab/cat)](https://repo1.maven.org/maven2/org/jlab/cat/)
A [Gradle](https://gradle.org/) file concatenation plugin.  This plugin is useful to combine / merge files such as web application JavaScript or CSS resources.

## Install
### settings.gradle
The plugin is available in Maven Central (but not the Gradle Plugin Portal).  Since Gradle only checks the Gradle Plugin Portal by default, you'll need to update your `settings.gradle` to point to Maven Central:
```
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
```
### build.gradle
Add to the top of your `build.gradle` file (Groovy syntax shown):
```
plugins {
  id "org.jlab.cat" version "<version>"
}
```

## Configure
Create a new task of type `org.jlab.cat.CatTask` and set the input files and output file.  For example (Groovy syntax shown):
```
task catJsFiles(type: org.jlab.cat.CatTask) {
    from("src/main/webapp/resources/js").include('**/*.js')
    output = file("${buildDir}/combined.js")
}
```
Alternatively you can enumerate files explicitly to maintain an ordering:
```
task catJsFiles(type: org.jlab.cat.CatTask) {
    from("src/main/webapp/resources/js/widget1/file1.js", src/main/resources/js/widget2/file2.js)
    output = file("${buildDir}/combined.js")
}
```
Finally, you can also leverage the `cat` extension if you only need one instance of the task:
```
cat {
    input = [layout.getProjectDirectory().file("src/main/webapp/resources/js/widget1/file1.js"), layout.getProjectDirectory().file("src/main/resources/js/widget2/file2.js")]
    output = file("${buildDir}/combined.js")
}
```
**Note**: Extension doesn't wrap `RegularFile` API so, it's clumsy.

## Release
1. Bump the version number in build.gradle and commit and push to GitHub (using [Semantic Versioning](https://semver.org/)).   
1. Create a new release on the GitHub [Releases](https://github.com/JeffersonLab/gradle-cat/releases) page corresponding to same version in build.gradle (Enumerate changes and link issues)
1. Publish new artifact on maven central with:
```
gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository
```
**Note**: There is a [GitHub action](https://github.com/JeffersonLab/gradle-cat/actions/workflows/maven-pubilsh.yml) for this to happen automatically.  To run locally you'll need to configure credentials.  See: [Gradle Publish Notes](https://gist.github.com/slominskir/5fcd5cf84182bf1542c07cbca953904a)
