# RedflagAPI

## Innehållsförteckning

- [Beskrivning](#beskrivning)
- [Installation](#installation)
- [Klient](#klient)
- [API-dokumentation](#api-dokumentation)
- [Databas](#databas)
- [Deployment och CI/CD](#deployment-och-cicd)
- [Testning och Kodkvalitet](#testning-och-kodkvalitet)
- [Miljövariabler](#miljövariabler)
- [Teknologier](#teknologier)
- [Kontakt](#kontakt)

## Beskrivning

RedflagAPI är ett REST API som fungerar som ett register över så kallade "red flags". En "red flag" i en relation är en varningssignal eller ett tecken på att något inte står rätt till. Det kan indikera problematiskt beteende, attityder eller dynamik som kan leda till ohälsosamma eller destruktiva mönster i relationen. 
API:et erbjuder CRUD-operationer för att hantera information om dessa redflags. Applikationen är byggd med Spring Boot och använder en MySQL-databas som är hostad i AWS RDS. Applikationen körs på en AWS Elastic Beanstalk-server och är integrerad med AWS CodeBuild och CodePipeline för CI/CD.

## Installation

För att köra applikationen, följ dessa steg:

1. Klona detta repo:
   ```bash
   git clone https://github.com/CarolinaMCorreia/RedflagAPI.git
   ```

2. Bygg och kör applikationen med Maven:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

Applikationen kommer nu att köras mot databasen på "http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com".

### Deployment till AWS Elastic Beanstalk

Den här applikationen är konfigurerad för att automatiskt deployas till AWS Elastic Beanstalk vid händelse av en push via en CI/CD-pipeline som använder GitHub Actions, AWS CodeBuild och AWS CodePipeline.

### Klient

Till API:et medföljer även en enkel Java-baserad konsollapplikation som utvecklats för att användaren ska kunna interagera med API
direkt från kommandoraden. Klienten är en klass vid namn "ApiClient" som finns under package "client".  Klassen är uppdelad i två huvudsakliga sektioner: hantering av användare och hantering av "Redflags", båda med full CRUD-funktionalitet (Create, Read, Update, Delete).

För att köra klienten måste applikationen vara igång och därefter kan klienten startas via en main-metod längst ner i klassen.

## API-dokumentation

API:et är dokumenterat med Swagger. När applikationen körs kan du besöka Swagger-UI för att utforska och testa API:et.

### Åtkomst till Swagger-UI

- **Lokal körning**: `http://localhost:5000/swagger-ui/index.html`
- **AWS Elastic Beanstalk-domänen**: `http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html`

Det finns även en generated-requests.http-fil i roten av projektet som ger en tydligare syntax till swagger json-bodys.

### API Endpoints

#### Redflag Endpoints

- **GET /redflags**: Hämtar alla redflags.
- **POST /redflags**: Skapar en ny redflag.
    - Request body:
      ```json
      {
        "id": 1,
        "description": "Detta är en beskrivning",
        "category": "BEHAVIOR",
        "examples": "Exempel på beteende",
        "advice": "Undvik detta beteende",
        "createdAt": "2023-01-01T12:00:00Z",
        "user": {
          "id": 1,
          "username": "användare1"
        }
      }
      ```

#### User Endpoints

- **GET /users**: Hämtar alla users.
- **POST /users**: Skapar en ny user.
    - Request body:
      ```json
      {
  "username": "john_doe",
  "password": "password123"
}
- **PUT /users/{id}**: Uppdaterar en user baserat på ID.
- **DELETE /users/{id}**: Tar bort en user baserat på ID.

### Javadocs

Koden är dokumenterad med Javadocs. För att generera och läsa Javadocs, kör följande kommando i projektets rotmapp:

```bash
mvn javadoc:javadoc
```

## Åtkomst till Javadocs
Detta kommer att skapa Javadocs-dokumentationen under target/site/apidocs/. Du kan öppna index.html-filen i en webbläsare för att bläddra igenom dokumentationen.

## Databas

Applikationen är ansluten till en MySQL-databas som är hostad på AWS RDS.

**Databasens uppsättning:**
- För att köra applikationen första gången behöver databasen `redflagdb` skapas manuellt i MySQL. Detta görs genom att ansluta till din MySQL-instans och köra kommandot:

  ```sql
  CREATE DATABASE redflagdb;
  USE redflagdb;

Efter att databasen har skapats kommer Hibernate automatiskt att generera de nödvändiga tabellerna när applikationen startas.

### Databasstruktur

Databasens schema består av två huvudsakliga tabeller:

- **Users**: Lagrar information om användare.
- **Redflags**: Lagrar information om redflags kopplade till användare.

## Deployment och CI/CD

Applikationen byggs och deployas automatiskt genom en CI/CD-pipeline som inkluderar följande steg:

- **GitHub Actions**: Används för att köra linter och säkerställa kodkvalitet. Actions är konfigurerade för att automatiskt bygga projektet och köra tester på varje push eller pull request.
- **AWS CodeBuild**: När en commit pushas till huvudgrenen, triggas en CodeBuild-process som bygger projektet och kör eventuella tester.
- **AWS CodePipeline**: Hanterar deployprocessen. Efter en lyckad build deployas applikationen automatiskt till AWS Elastic Beanstalk.

## Testning och Kodkvalitet

Projektet inkluderar tester och verktyg för att upprätthålla hög kodkvalitet:

- **Serviceklass-tester med JUnit**: JUnit används för att testa alla serviceklasser, vilket säkerställer att affärslogiken i applikationen fungerar som förväntat. Dessa tester körs som en del av CI-processen, vilket minskar risken för buggar i produktionsmiljön.
    - För att köra testerna:
      ```bash
      mvn test
      ```
- **Javadocs**: Koden är dokumenterad med Javadocs, och det finns kontroller för att säkerställa att dokumentationen är komplett och korrekt.
- **Kodkvalitet**: Linting-kontroller körs som en del av byggprocessen för att upprätthålla en hög kodstandard. Detta inkluderar både kodstil och korrekt implementering av Javadocs.

## Miljövariabler

Applikationens miljövariabler hittar du i `application.properties`.

## Teknologier

Applikationen använder följande teknologier och verktyg:

- **Spring Boot**: Backend-ramverk
- **MySQL**: Relationsdatabas (hostad på AWS RDS)
- **Swagger**: API-dokumentation
- **AWS Elastic Beanstalk**: Hosting och deployment
- **AWS CodeBuild & CodePipeline**: CI/CD-hantering
- **GitHub Actions**: För CI och kodkvalitetskontroller
- **JUnit**: För enhetstester av serviceklasser
- **Javadocs**: För kod-dokumentation

## Kontakt

Om du har frågor eller vill bidra till projektet kontakta mig via mail 94carcon@gafe.molndal.se eller skapa en issue på GitHub.

---
