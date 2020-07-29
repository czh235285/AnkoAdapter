## AnkoAdapter
[![](https://jitpack.io/v/com.gitee.czh235285/AnkoAdapter.svg)](https://jitpack.io/#com.gitee.czh235285/AnkoAdapter)

支持anko DSL布局的adapter

* 支持类似商城的多type布局
* 支持空布局、添加头部、尾部
* 普通adapter和多type都是一个基类
* 有一个viewBinding的adapter。个人不太喜欢xml绑定数据，没扩展dataBinding。

use Gradle:

```
repositories {
  maven { url "https://jitpack.io" }
  mavenCentral()
  google()
}
dependencies {
    implementation 'com.gitee.czh235285:AnkoAdapter:2.1.0'
}
```

简单示例[AnKoSampleAdapter](https://gitee.com/czh235285/AnkoAdapter/blob/master/app/src/main/java/c/core/sample/AnKoSampleAdapter.kt)

