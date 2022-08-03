#Serenity Project
Serenity BDD is an open source library that aims to 
make the idea of living documentation a reality.

Here is the [link](https://serenity-bdd.github.io/theserenitybook/latest/index.html) for simple documentation

###Steps to create a project
1. Create a maven project called `SerenityProject`
   1. Under `pom.xml`
      1. add below property section
       ```xml
       <properties>
           <maven.compiler.source>11</maven.compiler.source>
           <maven.compiler.target>11</maven.compiler.target>
       </properties>
       ```
      2. add dependencies
       ```xml
           <dependencies>
           <!--        This is for base support for anything we do with serenity-->
           <dependency>
               <groupId>net.serenity-bdd</groupId>
               <artifactId>serenity-core</artifactId>
               <version>2.4.4</version>
           </dependency>
           <!--        this is the dependency that wrap up rest assured with additional serenity support-->
           <dependency>
               <groupId>net.serenity-bdd</groupId>
               <artifactId>serenity-rest-assured</artifactId>
               <version>2.4.4</version>
           </dependency>
           <!-- Junit 5 dependency -->
           <dependency>
               <groupId>org.junit.jupiter</groupId>
               <artifactId>junit-jupiter</artifactId>
               <version>5.7.1</version>
               <scope>test</scope>
           </dependency>
           <!--Junit 5 support for serenity -->
           <dependency>
               <groupId>io.github.fabianlinz</groupId>
               <artifactId>serenity-junit5</artifactId>
               <version>1.6.0</version>
           </dependency>

           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-simple</artifactId>
               <version>1.7.30</version>
           </dependency>

           <dependency>
               <groupId>com.github.javafaker</groupId>
               <artifactId>javafaker</artifactId>
               <version>1.0.2</version>
           </dependency>
       </dependencies>
       ```
      3. add build plugins 
      ```xml
       <build>
           <plugins>
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-compiler-plugin</artifactId>
                   <version>3.8.1</version>
                   <configuration>
                       <source>8</source>
                       <target>8</target>
                   </configuration>
               </plugin>
               <!--            This is where the report is being generated after the test run -->
               <plugin>
                   <groupId>net.serenity-bdd.maven.plugins</groupId>
                   <artifactId>serenity-maven-plugin</artifactId>
                   <version>2.4.4</version>
                   <executions>
                       <execution>
                           <id>serenity-reports</id>
                           <phase>post-integration-test</phase>
                           <goals>
                               <goal>aggregate</goal>
                           </goals>
                       </execution>
                   </executions>
               </plugin>
               <!--         We want to run all the tests then generate one report -->
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-surefire-plugin</artifactId>
                   <version>3.0.0-M5</version>
                   <configuration>
                       <testFailureIgnore>true</testFailureIgnore>
                   </configuration>
               </plugin>
           </plugins>
       </build> 
      ```
      4. Create a package called `eu8` under `src/test/java`
         1. Under `eu8` create `spartan` and under spartan 
         create `admin` packages
         2. Create a class called `SpartanAdminGetTest`

      5. Create regular @Test rest api class `getAllSpartan`
         and send a request
      6. This is just regular test, in order to make it 
         recognized by serenity reports
         * add annotation class level : `@SerenityTest`
         * it is coming from `import net.serenitybdd.junit5.SerenityTest;`

      7. Add a properties file with exact name 
         `serenity.properties` right under project level
         * add following lines to properties file
         ```properties
         serenity.project.name=EU8 API Report
         serenity.test.root=eu8
         ```
      8. To generate serenity report, we need to use 
         maven goal
         * if you are using command line `mvn clean verify`
           +enter
         * if you are using intelliJ buttons
            * first click on clean and then click on verify
         * your report will be created under target as HTML report
      
      9. This is for serenity to pick up log and eliminate
         the warning
         ```xml
         <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-simple</artifactId>
               <version>1.7.30</version>
           </dependency>
         ```
      10. We were able to generate report, however there are
          no details about the request and response
             In order to you see the details then we 
             need to use the `given(), when(), then()`
             methods coming from Serenity
          * Instead of importing rest assured `given()` 
            import, use below
            * `Serenity.given()`
          * From this point on, all details will be picked
            up by serenity report you will see **Rest Query**
            button on the individual tests
      11. The way that assert the response and show it 
          as a steps in serenity report is using `Ensure`
          class (from `import net.serenitybdd.rest.Ensure;`)
         ```java 
          Ensure.that("Status code is 200", validatableResponse -> validatableResponse.statusCode(200));
          Ensure.that("Content type is JSON", each -> each.contentType(ContentType.JSON)); 
          Ensure.that("id is 10", each -> each.body("id",Matchers.is(10)));
         ```
      
   

