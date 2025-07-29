# jfunc [![jfunc](https://img.shields.io/maven-central/v/com.github.romanqed/jfunc?strategy=releaseProperty&style=for-the-badge&label=jfunc&color=blue)](https://repo1.maven.org/maven2/com/github/romanqed/jfunc)

A generalized lightweight set of lambda interfaces and some implementations that adds the necessary functionality that
is not available in the standard sdk.

## Getting Started

To install it, you will need:

* Java 11+
* Maven/Gradle

### Features

* Common set of functional interfaces
* Double-check thread-safe lazy suppliers
* Wrappers for suppressing and silent throwing checked exceptions

## Installing

### Gradle dependency

```groovy
dependencies {
    implementation group: 'com.github.romanqed', name: 'jfunc', version: '1.2.0'
}
```

### Maven dependency

```xml
<dependencies>
    <dependency>
        <groupId>com.github.romanqed</groupId>
        <artifactId>jfunc</artifactId>
        <version>1.2.0</version>
    </dependency>
</dependencies>
```

## Built With

* [Gradle](https://gradle.org) - Dependency management

## Authors

* **[RomanQed](https://github.com/RomanQed)** - *Main work*

See also the list of [contributors](https://github.com/RomanQed/jfunc/contributors)
who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details
