
### Containerize SpringBoot Application using Jib

Using Jib plugin we can build and push our image to ACR which allows storing and managing container images and artifacts in a private registry for all types of container deployments.

Jib is an open-source Java tool maintained by Google for building Docker images of Java applications. It simplifies containerization since with it, we don't need to write a Dockerfile.

✅ **Add jib plugin into pom.xml**

```xml
<plugin>
    <artifactId>jib-maven-plugin</artifactId>
    <groupId>com.google.cloud.tools</groupId>
    <version>${jib-maven-plugin.version}</version>
    <configuration>
        <allowInsecureRegistries>true</allowInsecureRegistries>
        <from>
            <image>mcr.microsoft.com/java/jdk:11-zulu-ubuntu</image>
        </from>
        <to>
            <image>${docker.image.prefix}/<repository-name></image>
            <auth>
                <username>${env.username}</username>
                <password>${env.password}</password>
            </auth>
        </to>
    </configuration>
</plugin>
```

**Note that we are using a base image from the Microsoft Container Registry (MCR): mcr.microsoft.com/java/jdk:11-zulu-ubuntu`, which contains an officially supported JDK for Azure. For other MCR base images with officially supported JDKs, see Java SE JDK, Java SE JRE, Java SE Headless JRE, and Java SE JDK and Maven.** [[source Microsoft](https://docs.microsoft.com/en-us/azure/container-registry/container-registry-java-quickstart)]

✅ **Push image into Azure Container Registry**

Now we can run the following command to build the image and push the image to the registry:

- `mvn compile jib:build -Denv.username=xxxx -Denv.password=yyyy`

We can pick **env.username** and **env.password** information from azure container registry resource on the Portal.
From the resource created on the previous lab, on the `settings` panel, open the `Access keys`, then copy `Username` value and `Password` value. 

**The image will be pushed to the registry with a tag latest`**.
``
:building_construction: [Some help - Google Jib official documentation](https://cloud.google.com/java/getting-started/jib?hl=fr)


### Connecting to Azure Container Registry (ACR) using Docker !

Let's test the pushed image on our local machine, if you have docker installed :

- Connect to the ACR

  `docker login <CONTAINER_NAME>.azurecr.io -u <CONTAINER_NAME> -p <PASSWORD_ADMIN>`


- pull the deployed image

  `docker pull <CONTAINER_NAME>.azurecr.io/labo21`


- Remove the pulled image

  `docker rmi <CONTAINER_NAME>.azurecr.io/labo21 --force`


- Run the pulled image

  `docker run -it --rm -p 8080:8080 <CONTAINER_NAME>.azurecr.io/labo21`