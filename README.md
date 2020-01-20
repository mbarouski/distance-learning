# Distance learning

Client-server application which allows to transfer audio, drawing, messages from server node to client ones and messages from client to all other nodes.

## Installation

- Install JavaFX as described here https://openjfx.io/openjfx-docs/#install-javafx
- Define `JAVAFX_PATH` env var to `JAVAFX_DIR/lib`
- Compile with `javac -Xlint:deprecation -Xlint:unchecked --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp .$(find . -name *.java)`
- Run with `java -jar ...`
