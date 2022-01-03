# ProjectStructure

ProjectStructure is a library used to define a set or rules against the code to be checked at startup and to better control how a project evolves and to enforce a structure over to it.

Each class or method can be tagged and then you can build the rules against a tag, a particular package or a specific class or any combination of them.

Some examples of possible rules:
* Each method on a class tagged as "Util" should be static and that it should not have any fields
* Each field of a class tagged as "Entity" should be private
* Classes in the package "org.example.controller" should never directly call classes inside the package "org.example.dao.impl"
* Each parameter in a method tagged as "immutable" should be final and the method never does an assignment or calls another method not tagged as "immutable"

You can decide if a rule completely breaks the compilation of the code, or if it just throws some warnings.

Often happens that with the time, maybe for a team change or a rushing developer, some code that is not consistent with the project gets created, a functionality gets created again from scratch just because no one knew it was present in the code, or some variables get changed from places that should not change them etc..., this can create bugs, unclear code or just waste time.

This library aims to fix some of those issues and aims to mimic some functionalities of other programming languages when needed.

