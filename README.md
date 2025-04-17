API d'un portail de mise en relation entre locataires et propriétaires
Cette application est l'API d'un portail de mise en relation entre les futurs locataires et les propriétaires pour de la location saisonnière sur la côte basque.

Installation
Pré-requis :

il est nécessaire d'avoir une base mysql intitulée "p3_back_end", et d'un user avec les droits sur la base.
pour la gestion des images, il faut avoir un dossier C:\Users\wakka\eclipse-workspace\p3backEnd\uploads créé en local et un Simple Web Server pointant sur ce dossier, lancé sur le port 8080
Clonez le dépôt git avec la commande suivante dans un terminal : git clone https://github.com/MorganFerreira/FerreiraLopes_Morgan_3_BackEndJava_032625/tree/master
Installation des dépendances et compilation : placez vous dans le répertoire racine du projet et tapez mvn clean install
Appliquez le script sql suivant, à la racine du projet, à l'aide de la commande suivante : mysql -u root -p rentals < src/main/resources/script.sql
Modifier le fichier application.properties pour ajouter les variables d'environnement aux propriétés spring.datasource.username et spring.datasource.password (cela concerne le couplet user/mot de passe de la base mysql)
Lancer l'application
Ce projet a été développé sous IntelliJ IDEA. Pour lancer l'application, cliquez sur la classe RentalsApplication.java et cliquez sur Run.

Une autre façon de lancer l'application est de taper la commande suivante dans un terminal, à la racine du projet : mvn spring-boot:run

L'API est accessible à l'url suivante : http://localhost:8080/

Documentation - Swagger
La documentation de l'application, sous forme de swagger, est disponible à l'url suivante depuis un navigateur : http://localhost:8080/swagger-ui/index.html#/

Technologies
Spring Boot
Java 17
Maven
MySql
Git
Licence
Développé dans le cadre du projet 3 de la formation Développeur Full-Stack - Java et Angular (OpenClassrooms) : https://openclassrooms.com/fr/paths/533/projects/1303/assignment