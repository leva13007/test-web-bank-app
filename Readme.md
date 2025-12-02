# Web bank app automation tests

This is the repo for project which is developing in the YT streams

[Youtube #1](https://youtube.com/live/CIPcTZyJqFc)

to run tests use command:

```
mvn test
```

to run specific test use command:

```
mvn -Dtest=RegistrationPageTest test
```

to run specific test method use command:

```
mvn -Dtest=RegistrationPageTest#testRegistrationWithExistingUser test
```