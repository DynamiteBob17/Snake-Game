# About
- a classic snake game with audio feedback for events such as collecting an apple, hitting a wall/losing and winning the game 
- the apples spawn in the available spaces and inputs for snake directions are queued and consumed on each movement of the snake 
- [live demo](https://drive.google.com/file/d/1rSL7jooabvl-GhF_3Mvj9AFce1u7kVJf/view)

# How to use	
If you have [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and up installed you can:
- download the .jar executable from the release  
**OR**
- compile and run the repository as a Maven project with your method of choice.
  
`mvn clean compile exec:java` to run from a command.  

> :warning: **If you run the program with no sound devices existing or connected to your system (if your sound is just muted this doesn't apply)**:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The MIDI sounds will not be loaded, so if you connect any sound device  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;while in the program, it won't be able to play any sounds. Therefore,  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;you need to restart the program, so it can load the sounds it needs.
