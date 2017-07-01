### Extended TestNG DataProvider

This repository contains TestNG **DataProvider** wrapper which helps to supply test data in a more flexible way.

Common **DataProvider** forces using quite old and ugly syntax which expects one of the following types to be returned from DP method's body:

 - Object[][]
 - Iterator<Object[]>

That's weird, as developers tend to use Stream and Collection API for data manipulation in the modern Java world.

Just imaging if you could use the following syntax to supply some filtered and sorted data into test method's signature:

```java
@DataSupplier
public Stream<User> getData() {
    return Stream.of(
        new User("Petya", "password2"),
        new User("Virus Petya", "password3"),
        new User("Mark", "password1"))
            .filter(u -> !u.getName().contains("Virus"))
            .sorted(comparing(User::getPassword));
}
    
@Test(dataProvider = "getData")
public void shouldSupplyStreamData(final User user) {
    // ...
}
```

Much better and flexible than two-dimensional arrays or iterators, isn't it?

And what if we don't want to iterate the same test N times depending on collection size? What if we want to extract its values and inject into test's signature like the following?

```java
@DataSupplier(extractValues = true)
public List<User> getExtractedData() {
    return StreamEx.of(
        new User("username1", "password1"),
        new User("username2", "password2"))
            .toList();
}
        
@Test(dataProvider = "getExtractedData")
public void shouldSupplyExtractedListData(final User user1, final User user2) {
    // ...
}
```

#### Supported return types

 - Collection
 - Object[]
 - double[]
 - int[]
 - long[]
 - Stream
 - StreamEx
 - A single Object of any common or custom type

#### Usage

Add the following configuration into **build.gradle** as soon as it's published to bintray.

```groovy
repositories {
    jcenter()
}
    
dependencies {
    compile('org.testng:testng:6.10',
            'io.github.sskorol:test-data-supplier:0.5.0'
    )
}
    
test {
    useTestNG() {
        listeners << 'io.github.sskorol.dataprovider.DataProviderTransformer'
    }
}
```

Instead of a common **DataProvider** annotation use one of the following:
 
```java
@DataSupplier
public T getData() {
    //...
}
    
// or
    
@DataSupplier(extractValues = true)
public T getData() {
    //...
}
```

Now you can refer **DataSupplier** the same way as with TestNG **DataProvider**:

```java
@Test(dataProvider = "getData")
public void supplyData(final T data) {
    // ...
}
    
// or
    
@Test(dataProviderClass = ExternalDataProvider.class, dataProvider = "getData")
public void supplyExternalData(final T data) {
    // ...
}
```

You can find more examples in a **io.github.sskorol.testcases** package.

#### Limitations

 - no custom names support (method name is used by default);
 - no parallel feature