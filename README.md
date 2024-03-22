## Projekttitel

Det här är en klientapplikation för att hantera företag, städer, anställda och användare. Den är designad för att
interagera med en specifik backend-tjänst, som måste vara igång för att klientapplikationen ska fungera korrekt.

## Projektbeskrivning

Denna klientapplikation är en omfattande lösning utformad för att effektivisera hanteringen av företag, städer,
anställda och användare. Applikationen erbjuder en användarvänlig gränssnitt som möjliggör enkel navigering och
hantering av kritiska data på ett säkert och effektivt sätt.

### Företagshantering

Med applikationen kan användare lägga till nya företag, uppdatera befintliga företagsinformation, ta bort företag från
systemet och hämta detaljerad information om varje företag. Detta möjliggör en centraliserad databas över företag som
kan användas för olika ändamål, inklusive rapportering och analys.

### Stadshantering

Applikationen underlättar hanteringen av städer, där användare kan lägga till, uppdatera, radera och hämta information
om städer. Detta är särskilt användbart för organisationer som opererar i flera städer och behöver en konsoliderad vy
över sina operationer.

### Anställdshantering

En annan viktig funktion är hanteringen av anställda, där användare kan registrera nya anställda, uppdatera anställdas
information, avregistrera anställda och hämta information om de anställda. Detta hjälper till att underlätta
HR-processer och säkerställer att anställdas data är organiserad och lättillgänglig.

### Användarhantering

Applikationen inkluderar också robust användarhantering, där användare kan registrera sig, logga in, hantera sina
profiler, samt lägga till, uppdatera och radera användarinformation. Detta bidrar till en säker användarupplevelse och
möjliggör skräddarsydd åtkomst och funktionalitet för olika användartyper.

### Tekniskt Ramverk

Projektet är byggt med Java, en av de mest etablerade och pålitliga programmeringsspråken, vilket säkerställer
applikationens stabilitet och skalbarhet. Maven används för projektets bygghantering, vilket underlättar hantering av
beroenden och byggprocessen. Applikationen använder också JUnit för omfattande testning, vilket garanterar kodkvalitet
och robusthet.

Sammanfattningsvis är denna klientapplikation en mångsidig och kraftfull plattform som erbjuder omfattande
funktionalitet för hantering av företag, städer, anställda och användare, vilket gör det till ett oumbärligt verktyg för
organisationer som söker effektivitet och strukturerad datahantering.

## Backend-tjänst

Denna klientapplikation är designad för att arbeta tillsammans med en backend-tjänst. Backend-tjänsten är utvecklad av
en annan grupp och är en kritisk komponent för att klientapplikationen ska fungera korrekt. Du kan hitta mer information
om backend-tjänsten och dess konfiguration på följande GitHub-repositorium:

- [AwsGroup Backend](https://github.com/Jafar-Hussein/AwsGroup).

## Komma igång

Dessa instruktioner hjälper dig att få en kopia av projektet upp och köra på din lokala maskin för utvecklings- och
teständamål.

### Förutsättningar

- Java 17
- Maven
- En IDE såsom IntelliJ IDEA

### Installation

1. Klona repositoryt till din lokala maskin.
2. Öppna projektet i din IDE.
3. Kör `mvn clean install` för att bygga projektet och installera beroenden.

## Köra applikationen

1. Se till att backend-tjänsten är igång.
2. Kör `Main`-klassen i din IDE.

## Funktioner

Denna klientapplikation tillhandahåller följande funktioner:

- Användarautentisering: Registrera och logga in som användare.
- Hantering av företag: Lägg till, uppdatera, ta bort och hämta företag.
- Hantering av städer: Lägg till, uppdatera, ta bort och hämta städer.
- Hantering av anställda: Lägg till, uppdatera, ta bort och hämta anställda.
- Hantering av användare: Lägg till, uppdatera, ta bort och hämta användare.

## Köra tester

Detta projekt använder JUnit Jupiter och Mockito för testning. För att köra testerna, använd kommandot `mvn test`.

## Beroenden

Här är några av de viktigaste beroendena som specificeras i din `pom.xml`-fil:

- Apache HttpClient5 för HTTP-anrop.
- Jackson Databind för JSON-hantering.
- Lombok för att minska boilerplate-koden.
- Gson för JSON-hantering.
- JUnit Jupiter och Mockito för enhetstester.

## Bidragande

- Grupparbete

- (https://github.com/KoffaRn)
- (https://github.com/Samer-Ismael)
- (https://github.com/EllenHalv)
- (https://github.com/AM1897)

## Licens

Detta projekt är licensierat under MIT-licensen - se filen `LICENSE.md` för detaljer
