# [Orb](http://orb-load-balancer-2024449532.us-east-2.elb.amazonaws.com)
Orb is a web application for managing tasks and scheduling time. 

It is a kind of todo application and has a number of advantages:
* **Simplicity:** It has a simple and user-friendly interface. All functions of the app are intuitive
* **Time control:** allows you to set a deadline for completing a task, which helps users use their time more productively
* **Subtasks:** the main task can be divided (decomposed) into smaller subtasks, which makes it much easier to complete the goal


## API Documentation
See the [Swagger UI Documentation](http://ec2-3-14-148-198.us-east-2.compute.amazonaws.com:8080/api/swagger-ui.html#/)

## Contributing
I will be happy to accept your pool requests to fix bugs or add new features. 
If you decide to add a new feature, first look in issues - it may already be under development. 
Also, before creating a pool request, don't forget to write tests. 
All the best!

## Getting Started

### Setup backend (orb-server)
1. **Clone the application**
	```bash
	git clone https://github.com/FirstSin/Orb.git
	cd orb-server
	```
2. **Change database credentials**

	* Open `src/main/resources/application.properties` file.

	* Change the following properties to data for your database:

	```properties
	spring.datasource.url=jdbc:postgresql://localhost:5432/{your_db}
	spring.datasource.username={your_username}
	spring.datasource.password={your_password}
	```
	```properties
	spring.flyway.url=jdbc:postgresql://localhost:5432/{your_db}
	spring.flyway.user={your_username}
	spring.flyway.password={your_password}
	```

3. **Run the app**

	You can run the spring boot app by typing the following command:

	`mvn spring-boot:run`

	The server will start on port `8080`.

	You can also package the application in the form of a jar file and then run it like so:

	```
	mvn package
	java -jar target/orb-0.0.1-SNAPSHOT.jar
	```

### Setup frontend (orb-client)
1. **Go to the orb-client folder**

	`cd orb-client`

2. **Install the necessary modules**

	`yarn install`

3. **Run the app**

	`yarn start`

	The front-end server will start on port `3000`.

	If you want to optimize development build, enter the following command instead: 

	`yarn build`

## License

Orb is [Apache 2.0 Licensed](https://github.com/FirstSin/Orb/blob/master/LICENSE)
