# 判断project是不是android工程


```groovy
class BigSourceFinderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        if (project.plugins.hasPlugin(AppPlugin)) {

        }

```
> 找不到AppPlugin可以如下配置



```gradle
apply plugin: 'groovy'
apply plugin: 'maven'
configurations {//必需要，不然provided识别不了
    provided
    compile.extendsFrom provided
}

dependencies {
    compile gradleApi()
    compile localGroovy()
    provided 'com.android.tools.build:gradle:2.2.1'//只是把它拿出来
    testCompile group: 'junit', name: 'junit', version: '4.12'
}

```

