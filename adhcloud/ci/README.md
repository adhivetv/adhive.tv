# AdHive CI #

This repository for scripts and configuration:

### This repository for ###

* Dockerfiles for **build-tool**
    * **teamcity-agent**
    * teamcity-server
    * nexus
* docker-compose configurations for configure build tools
    * docker-compose.yml
        * teamcity-agent
    
* Version 1.0
* [Learn about docker](https://www.docker.com/)

### Requirements ###
* Setup
   * docker for [ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/#set-up-the-repository),
   [centos](https://docs.docker.com/install/linux/docker-ce/centos/) 
   * docker compose
   * create local file .env with variables. Example:
	SERVER_URL=http://teamcity-server:8111/
        NEXUS_URL=http://yournexusserver:XXXX/
   * configure private registry in Dockerfile
    * echo "{ "insecure-registries":["$NEXUS_URL"] }">/etc/docker/daemon.json
    * restart docker service
    
### Build/push/pull/run###

* Build containers **./gradlew docker**
* Push container to registry **./gradlew dockerPush**
* Pull container from private registry **docker pull "${nexusUrl}/${project.group}/${jar.baseName}"**
* Run container over docker compose *docker-compose up teamcity-agent -d*

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

