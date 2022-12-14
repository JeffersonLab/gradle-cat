# gradle-cat
A [Gradle](https://gradle.org/) file concatenation plugin.  This plugin is useful to combine / merge files such as web application JavaScript or CSS resources.

## Install
### settings.gradle
The plugin is available in Maven Central (but not the Gradle Plugin Portal yet!).  Since Gradle only checks the Gradle Plugin Portal by default, you'll need to update your `settings.gradle` to point to Maven Central:
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
    input.from("src/main/webapp/resources/js").include('**/*.js')
    output = file("${buildDir}/combined.js")
}
```
