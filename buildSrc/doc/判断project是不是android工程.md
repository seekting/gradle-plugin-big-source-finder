# �ж�project�ǲ���android����


```groovy
class BigSourceFinderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        if (project.plugins.hasPlugin(AppPlugin)) {

        }

```
> �Ҳ���AppPlugin������������



```gradle
apply plugin: 'groovy'
apply plugin: 'maven'
configurations {//����Ҫ����Ȼprovidedʶ����
    provided
    compile.extendsFrom provided
}

dependencies {
    compile gradleApi()
    compile localGroovy()
    provided 'com.android.tools.build:gradle:2.2.1'//ֻ�ǰ����ó���
    testCompile group: 'junit', name: 'junit', version: '4.12'
}

```

