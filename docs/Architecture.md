# Project architecture
The project structure is split in three layers: **domain**, **user interface** and **persistence**.
Each layer is documented in javadoc and has been tested. <br/>
We have decided to split the files into three modules. One module consists of **domain**
and **persistence** (core). The other consists of the **user interface** (gui), and the **rest api** 
(rest).

## Class diagram
Our diagram was generated using the IntelliJ IDE. <br>
A **dotted** line represents a class dependency. <br>
A **solid** line represents a class aggregation. 

![ClassDiagram](./img/class_diagram.png "Class Diagram")

## Sequence diagram
Our sequence diagram shows the act of a user booking a course.

![SequenceDiagram](./img/sequence_diagram.png "Sequence Diagram")

## Package diagram
Our package diagram shows the package structure and dependencies between packages.

![PackageDiagram](./img/package_diagram.png "Package Diagram")