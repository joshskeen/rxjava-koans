##RxJava Koans
___

The Koans walk you along the [path to Rx enlightenment](https://pbs.twimg.com/media/B5oIZCXCMAI_vTn.jpg:large) in order to learn RxJava. The goal is to learn the functional reactive programming approach and how to work with RxJava to solve common problems.

The koans are broken out into subjects by file. Each koan file builds up your knowledge of rx-java and builds upon itself. It will stop at the first place you need to correct.

Some koans simply need to have the correct answer substituted for an incorrect one. Some, however, require you to supply your own answer. If you see the method __ (a double underscore) listed, it is a hint to you to supply your own code in order to make it work correctly. Your task is to make each test pass!


### How to run the project

1. Java 8 is needed for the exercise. [Download from here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) if you don't have it already. 
2. [download IntelliJ Community Edition](https://www.jetbrains.com/idea/download/)
3. `git clone git@github.com:mutexkid/rxjava-koans.git`
4. In Intellij, select File > Import Project... and select the cloned directory
5. In the Import Project dialog, select Import Project from External Model, choose Gradle and click next.
6. On the next screen, make sure "use default gradle wrapper" is selected and click Finish
7. Last, under File > Project Structure, set Project SDK: to Java 1.8 and click ok!

Run the test suite by right clicking on `src/test/java` and selecting `Run 'All Tests'`.
5. The test suite will fail - make each test pass!

For more information about Functional Reactive Programming with RxJava, [check out my article on the topic](http://www.bignerdranch.com/blog/what-is-functional-reactive-programming/).


This project began its life as a direct port of https://github.com/mattpodwysocki/RxJSKoans. It aspires to add more rx-java specific challenges. Pull requests and feedback accepted!
