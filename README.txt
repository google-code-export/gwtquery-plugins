

This is the archetype to create a new  GwtQuery plugin.

- Assuming you have installed maven, compile and install it just running:
$ mvn clean install

- To use the archetype run:
$ mvn archetype:generate  -DarchetypeGroupId=com.googlecode.gwtquery \
                          -DarchetypeArtifactId=gquery-plugin  \
                          -DarchetypeVersion=0.3
                          -DartifactId=myplugin \
                          -DpluginName=MyPlugin 

- Then change to the folder myplugin and run:
$ mvn clean install

- To run the example, change to the myplugin/sample folder and run:
$ mvn gwt:run
