# Google Dialogflow + Spring Boot + Angular + MQTT + ESP8266 + anything

This project is a simple Spring Boot Application designed to communicate with IoT devices over MQTT Broker. User can send commands to an ESP8266 using Google Assistant or a web interface built with Angular 4.

### Install prerequisites
  - Install MySQL 5.5 or MariaDB 10.1.23 on your server/local environment.
  - Install latest MQTT on your server/local environment.
   On MacOS you can install using brew.sh:
    ```sh
    $ brew install mosquitto
    ```
    On Debian/Raspberry Pi you can install using following commands:
    ```sh
    $ sudo apt-get update
    $ sudo apt-get install mosquitto mosquitto-clients
    ```
  - For more information on installing MQTT on different operating systems, refer to :
    https://mosquitto.org/download/

### Clone & Run
```sh
$ git clone git@github.com:adnirjhar/orchid-backend.git
$ cd orchid-backend
$ mvn install
$ mvn spring-boot:run
```

### Configure application
##### Set your own MQTT Broker host
By default, the application is configured to connect to the publicly open MQTT Broker host `iot.eclipse.org`. But eventually you would want it to connect to your own MQTT Broker on your server/local environment.

  - In the `application_config` table, find the row where `mqttServer` is set to `iot.eclipse.org`.
    [![MQTT](https://i.ibb.co/Z6VpRhr/application-config.png")](https://nodesource.com/products/nsolid)
  - Replace the `iot.eclipse.org` with your own MQTT Broker's host name or just `localhost` if local environment.
  - Also replace the `mqttPort` value with your own MQTT Broker's port in case its not running on the default port `1883`.

##### Load the initial data
For a quick start and to help out the users to understand the project better, there is an `initialData.sql` file located at `src/main/resources/`. Running this sql file will add some ADMIN users and fill up some more data to get you started.
  - Run the `initialData.sql` file located at `src/main/resources/`.
    [![MQTT](https://i.imgur.com/UPTkwIl.gif[/img])](https://nodesource.com/products/nsolid)

Congratulations! Our Spring Boot Application itself is ready to go!
It's time to plug-in the super awesome Frontend Application!

### Install and run frontend
Go to [Orchid Frontend](https://github.com/adnirjhar/orchid-frontend), follow the instructions to install and run the frontend project.

### Usage
I wrote an article on the entire system. You can learn more in this [Medium article.](https://medium.com/@nitam/google-dialogflow-spring-boot-angular-mqtt-esp8266-anything-42d8e19dedec)
