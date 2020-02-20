# Demo Project: Serenity, Cucumber, Selenium and REST Assured

This demo project illustrates Test Automation using Serenity, Cucumber, Selenium and REST Assured. 
The project tests the [Trello](https://trello.com) online team collaboration application. There are two sample scenarios:
- create trello boards from templates - this scenario is implemented via the UI with Selenium
- perform various board actions and verify the activity log - this scenario is implemented via the publicly available [trello api](https://developers.trello.com/reference#introduction) with REST Assured.

## Prerequisites
This project is targeted to Windows O.S. (but can probably be adapted to linux or mac with little effort). It requires [maven](http://maven.apache.org/download.cgi) and [chrome browser](https://www.google.com/chrome/) to be installed.


## Get the code

Git:

    git clone https://github.com/pisqa/serenity-cucumber-selenium-rest-demo.git
    cd serenity-cucumber-selenium-rest-demo


Or simply [download a zip](https://github.com/pisqa/serenity-cucumber-selenium-rest-demo/archive/master.zip) file.

## Configure trello credentials

1. **Do NOT use a live trello account - the test tear-down step deletes all boards from the configured account!**
1. create a new trello [account](https://trello.com/signup)
1. get the API Key and Token for your account - log into trello and go to [https://trello.com/app-key](https://trello.com/app-key):


<img src="https://user-images.githubusercontent.com/57501449/74966117-c4716180-5416-11ea-8d6e-6877642a6a09.png" width="450" >

<img src="https://user-images.githubusercontent.com/57501449/74966679-cdaefe00-5417-11ea-9027-1c8bece83e75.png" width="450" >

1.   Edit  `serenity-cucumber-selenium-rest-demo/src/test/resources/serenity.conf`
2. Set the trello credential properties:

<img src="https://user-images.githubusercontent.com/57501449/74740696-b1fef880-525b-11ea-83a0-1f4b42b91bb7.png" width="450">


## Executing the tests
To run the project run `mvn clean verify` from the command line.

By default, the tests will run in headless mode. You can run them in non-headless mode by overriding the `headless.mode` system property, e.g.
```json
$ mvn clean verify -Dheadless.mode=false
```

## Test Results
The test results will be recorded in the `target/site/serenity` directory.
If all goes well, you should see something like this:



<img src="https://user-images.githubusercontent.com/57501449/74771301-2fdbf780-528e-11ea-8f54-dca76b58d9ff.png" width="800">


