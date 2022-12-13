# gradle-cat
A [Gradle](https://gradle.org/) file concatenation plugin.  This plugin is useful to combine / merge files such as web application JavaScript or CSS resources.

## Install

Add to the top of your build.gradle file (Groovy syntax shown):
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
    output = file("${buildDir}/combined-js/combined.js")
}
```
