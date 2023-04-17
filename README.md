# Shepherd

This project is built with a Scala backend and a Next.js (React) + Tailwind CSS frontend.

The Scala backend is run on a Jetty server with Scalatra Servlet mounted on top to serve the API endpoint.

This is incredibly powerful and scalable even if our data scope increases massively. Although for now, the data is stored in memory and structured using a `TreeBasedTable` from Guava.

The API supports basic Cookie authentication.

## Build

The project was built with Sbt 1.8.2 and JDK 1.8. You can download those from [here](https://docs.scala-lang.org/getting-started/index.html).

Once you've downloaded Sbt, to build and compile the project, you can run
```
sbt clean compile
```
Next, navigate to the `shepherd-client` and run the following commands to build the client code
```
npm install && npm run build && npm run export
```

I haven't packaged the program in one big JAR, but I would recommend running the project from an IDE like IntelliJ so that it can find all the relevant dependencies neatly.

The build command (via CLI) is massive because of all the compile time dependencies. However, if you open the project in the IDE, all you have to do is navigate to `shepherd-server/src/main/scala/org/shepherd/server/Server.scala` and hit the `Run` button next to the `main()` method.

This will launch the API and the UI and print out the location in the logs. Once done, you should see this in the browser window:

![image](https://user-images.githubusercontent.com/16950123/232413750-d068aea9-185a-4041-94ef-bcc3b84b5ac2.png)

Loom video to follow!
