# FindBigSource


## add in classpah
```java
buildscript {

    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.0'
        classpath 'com.seekting.gradle:big-source-finder:1.0.2'
    }
}

```
## add plugin in build.grade
```gradle
apply plugin: 'com.seekting.gradle.bigsourcefinder'

```
## add code in app/build.gradle below
```gradle
findbigsource {
    maxSize = 200 * 1024  //if some source's file size is bigger than 200*1024,it will build fail if ifHasAboveMaxSizeCrash=true
    ifHasAboveMaxSizeCrash = true //is true ,and a file's size is bigger than maxSize,it will build fail
    checkDuplicate = true// if res dir has duplicate files ,it will build fail(only if ifHasDuplicateCrash=true)
    ifHasDuplicateCrash = false
}

```

## add task dependsOn
```gradle

tasks.whenTaskAdded { task ->
    if (task.name == "assembleDebug") {
//        println("added:${task.name}")
    } else if (task.name == "findAppDebug") {
//        println("added:${task.name}")
        assembleDebug.dependsOn task

    }
}




```
## command line findAppDebug

if you didn't add findAppDebug dependsOn,you can run findAppDebug instead
```Terminal

$ gradle findAppDebug
```
```Terminal
FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:findAppDebug'.
> 12 source files is too big!



```

```Terminal
find task begin!
warning:find same file:[mipmap-xxxhdpi\ic_launcher1.png,mipmap-xxxhdpi\ic_launcher.png]
find task end!

```
