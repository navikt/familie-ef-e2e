FROM navikt/java:17

COPY ./target/*with-dependencies.jar "app.jar"