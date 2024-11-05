# Configuration SQL pour la base de données CoffeeClasses

Ce document décrit le processus de création d'une base de données PostgreSQL pour gérer des informations relatives aux étudiants, enseignants, cours et évaluations. Suivez les étapes ci-dessous pour configurer votre base de données.

*(Note : Il est possible d'utiliser Beekeeper Studio (ou similaire) pour visualiser et gérer la base de données graphiquement, de manière similaire à MySQL Workbench.)*

## Étape 1 : Connexion à PostgreSQL

Avant de commencer, assurez-vous d'avoir PostgreSQL installé sur votre machine. Ouvrez un terminal et connectez-vous à PostgreSQL en tant qu'utilisateur ayant les droits d'administrateur (souvent `postgres`).

```bash
psql -U postgres
```

## Étape 2 : Création de l'utilisateur et de la base de données

Une fois connecté, exécutez les commandes suivantes pour créer un utilisateur et une base de données :

### 2.1 Créer l'utilisateur coffeeclasses

```sql
CREATE USER coffeeclasses WITH PASSWORD 'coffeeclasses';
```

### 2.2 Créer la base de données coffeeclassesdb

```sql
DROP DATABASE IF EXISTS coffeeclassesdb;
CREATE DATABASE coffeeclassesdb OWNER coffeeclasses;
```

### 2.3 Connexion à la nouvelle base de données

Connectez-vous à la base de données `coffeeclassesdb` en utilisant l'utilisateur `coffeeclasses` :

```bash
\c coffeeclassesdb coffeeclasses
```

## Étape 3 : Création des tables

Exécutez le script SQL ci-dessous pour créer les tables nécessaires dans la base de données :

```sql
CREATE TABLE Etudiant(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(30),
    prenom VARCHAR(30),
    dateNaissance DATE,
    contact VARCHAR(100)
);

CREATE TABLE Enseignant(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(30),
    prenom VARCHAR(30),
    dateNaissance DATE,
    contact VARCHAR(100)
);

CREATE TABLE Cours(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    coefficient REAL
);

CREATE TABLE Evaluation(
    evaluationID SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    coefficient INT,
    etudiantID INT,
    coursID INT,
    FOREIGN KEY (etudiantID) REFERENCES Etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (coursID) REFERENCES Cours(id) ON DELETE CASCADE
);

CREATE TABLE Inscription(
    id SERIAL PRIMARY KEY,
    etudiantID INT,
    coursID INT,
    FOREIGN KEY (etudiantID) REFERENCES Etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (coursID) REFERENCES Cours(id) ON DELETE CASCADE
);

CREATE TABLE Assignation(
    id SERIAL PRIMARY KEY,
    enseignantID INT,
    coursID INT,
    FOREIGN KEY (enseignantID) REFERENCES Enseignant(id) ON DELETE CASCADE,
    FOREIGN KEY (coursID) REFERENCES Cours(id) ON DELETE CASCADE
);
```