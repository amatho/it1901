# Project architecture
The project structure is split in three layers: **domain**, **user interface** and **persistence**.
Each layer is documented in javadoc and has been tested. <br/>
We have decided to split the files into three modules. One module consists of **domain**
and **persistence** (core). The other consists of the **user interface** (gui), and the **rest api** 
(rest).

## Diagram
Our diagram was generated using the IntelliJ IDE.

![PlantUML](./img/plantUML.png "PlantUML")