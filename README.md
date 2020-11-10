[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod&style=flat-square)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2009/gr2009)

# Golf App

## Build and run

All Maven commands should be ran from the **project root** since some sub-modules depend on others.

To run a standard Maven compile, run `mvn compile`.

Run `mvn javafx:run` to run the JavaFX app. Use `mvn jetty:run` to start up the Jetty server for the
REST API.

**Note:** The JavaFX app will only use the REST API if the server is running **before** the app has
started, otherwise it will fall back to file storage.

## Test and verify

Run `mvn test` to run unit tests.

Run `mvn verify` to run both unit tests and integration tests. This will also run JaCoCo (code
coverage), Checkstyle (code style), and Spotbugs (check for bugs).

## Location for file storage

The JavaFX app and REST server use a platform-specific location for storing application data. The
location is as follows:
* On Linux, `$HOME/.local/share/GolfApp`
* On Mac OS, `$HOME/Library/Application Support/GolfApp`
* On Windows, `%LOCALAPPDATA%\GolfApp`

## Further documentation
For further documentation, visit the [developer documentation](./docs/README.md).
