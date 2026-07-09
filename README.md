# Gestion Scolaire

Application backend **Spring Boot 3** pour la gestion scolaire (cours, enseignants, étudiants, inscriptions) avec authentification **JWT**, rôles (ADMIN, ENSEIGNANT, ETUDIANT) et documentation API **Swagger/OpenAPI**.

## Technologies

| Domaine            | Stack                                                        |
|--------------------|--------------------------------------------------------------|
| Langage            | Java 17                                                      |
| Framework          | Spring Boot 3.5 (Web, Data JPA, Security, Validation)        |
| Sécurité           | Spring Security + JWT (`jjwt` 0.12.6)                        |
| Base de données    | PostgreSQL (prod) / H2 en mémoire (dev)                      |
| Documentation API  | springdoc-openapi (Swagger UI)                               |
| Build              | Maven (wrapper inclus)                                       |
| Conteneur DB       | Docker Compose (PostgreSQL 15)                               |

## Structure du projet

```
src/main/java/com/ipd/Gestion_Scolaire/
├── controller/      # Endpoints REST (Auth, Cours, Enseignant, Etudiant, Inscription)
├── service/         # Interfaces + implémentations métier
├── dto/             # Objets de transfert (Request / Response)
├── entity/          # Entités JPA (User, Role, Cours, Enseignant, Etudiant, Inscription, RefreshToken)
├── repository/      # Interfaces Spring Data JPA
├── security/        # JwtService, JwtFilter, SecurityConfig, UserDetailsServiceImpl
├── exception/       # GlobalExceptionHandler, ResourceNotFoundException
└── GestionScolaireApplication.java

src/main/resources/
├── application.yaml       # Valeurs communes à tous les profils
├── application-dev.yaml   # Profil dev (H2)
└── application-prod.yaml  # Profil prod (PostgreSQL)
```

## Prérequis

- **JDK 17**
- **Maven** (ou utiliser le wrapper `mvnw`)
- **PostgreSQL** (uniquement pour le profil `prod`) ou **Docker** pour `docker-compose`

## Installation et lancement

### 1. Cloner et compiler

```bash
git clone <repo>
cd projet-sprintboot
./mvnw clean install
```

### 2. Lancer en mode développement (H2 en mémoire)

Le profil `dev` est actif par défaut (`application.yaml` → `spring.profiles.active: dev`).
Aucune base de données externe n'est requise.

```bash
./mvnw spring-boot:run
```

- API : http://localhost:8085
- Swagger UI : http://localhost:8085/swagger-ui.html
- Console H2 : http://localhost:8085/h2-console (JDBC URL `jdbc:h2:mem:gestion_scolaire`)

### 3. Lancer en mode production (PostgreSQL)

Démarrer PostgreSQL (via Docker) puis lancer l'application avec le profil `prod` :

```bash
docker-compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Authentification (JWT)

Toutes les routes sauf `/auth/**` et Swagger nécessitent un token JWT
transmis dans l'en-tête : `Authorization: Bearer <token>`.

### Flux

1. `POST /auth/register` → crée un compte (role par défaut selon l'inscription).
2. `POST /auth/login` → retourne `accessToken` + `refreshToken`.
3. `POST /auth/refresh-token` → renouvelle l'`accessToken` avec le `refreshToken`.

Le token d'accès expire après 15 min (`access-token-expiration: 900000` ms),
le refresh token après 7 jours (`604800000` ms).

## Rôles et autorisations

| Rôle        | Droits                                                                 |
|-------------|------------------------------------------------------------------------|
| `ADMIN`     | CRUD complet sur toutes les ressources                                 |
| `ENSEIGNANT`| Lecture cours/enseignants/étudiants/inscriptions                       |
| `ETUDIANT`  | Lecture des cours uniquement                                           |

## Endpoints de l'API

Base URL : `http://localhost:8085`

### Authentification `/auth`

| Méthode | Endpoint           | Rôle requis | Description                          |
|---------|--------------------|-------------|--------------------------------------|
| POST    | `/auth/register`   | Public      | Créer un compte utilisateur          |
| POST    | `/auth/login`      | Public      | Obtenir les tokens JWT               |
| POST    | `/auth/refresh-token` | Public   | Rafraîchir le token d'accès          |

### Cours `/cours`

| Méthode | Endpoint     | Rôle requis                              | Description              |
|---------|--------------|------------------------------------------|--------------------------|
| POST    | `/cours`     | ADMIN                                    | Créer un cours           |
| GET     | `/cours`     | ADMIN, ENSEIGNANT, ETUDIANT              | Lister les cours         |
| GET     | `/cours/{id}`| ADMIN, ENSEIGNANT, ETUDIANT              | Détail d'un cours        |
| PUT     | `/cours/{id}`| ADMIN                                    | Mettre à jour un cours   |
| DELETE  | `/cours/{id}`| ADMIN                                    | Supprimer un cours       |

### Enseignants `/enseignants`

| Méthode | Endpoint         | Rôle requis                | Description                |
|---------|------------------|----------------------------|----------------------------|
| POST    | `/enseignants`   | ADMIN                      | Créer un enseignant        |
| GET     | `/enseignants`   | ADMIN, ENSEIGNANT          | Lister les enseignants     |
| GET     | `/enseignants/{id}` | ADMIN, ENSEIGNANT       | Détail d'un enseignant     |
| PUT     | `/enseignants/{id}` | ADMIN                   | Mettre à jour un enseignant|
| DELETE  | `/enseignants/{id}` | ADMIN                   | Supprimer un enseignant    |

### Étudiants `/etudiants`

| Méthode | Endpoint            | Rôle requis                | Description                 |
|---------|---------------------|----------------------------|-----------------------------|
| POST    | `/etudiants`        | ADMIN                      | Créer un étudiant           |
| GET     | `/etudiants`        | ADMIN, ENSEIGNANT          | Lister les étudiants        |
| GET     | `/etudiants/paginated` | ADMIN, ENSEIGNANT       | Lister paginé (`page`, `size`) |
| GET     | `/etudiants/{id}`   | ADMIN, ENSEIGNANT          | Détail d'un étudiant        |
| PUT     | `/etudiants/{id}`   | ADMIN                      | Mettre à jour un étudiant   |
| DELETE  | `/etudiants/{id}`   | ADMIN                      | Supprimer un étudiant       |

### Inscriptions `/inscriptions`

| Méthode | Endpoint           | Rôle requis                | Description                  |
|---------|--------------------|----------------------------|------------------------------|
| POST    | `/inscriptions`    | ADMIN                      | Créer une inscription        |
| GET     | `/inscriptions`    | ADMIN, ENSEIGNANT          | Lister les inscriptions      |
| GET     | `/inscriptions/{id}` | ADMIN, ENSEIGNANT        | Détail d'une inscription     |
| DELETE  | `/inscriptions/{id}` | ADMIN                    | Supprimer une inscription    |

## Exemples de requêtes

### Inscription

```bash
curl -X POST http://localhost:8085/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Marie",
    "email": "marie.dupont@ipd.com",
    "password": "password123",
    "role": "ADMIN"
  }'
```

### Connexion

```bash
curl -X POST http://localhost:8085/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "marie.dupont@ipd.com", "password": "password123"}'
```

### Création d'un cours (avec token)

```bash
curl -X POST http://localhost:8085/cours \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Mathématiques Avancées",
    "description": "Cours de calcul différentiel et intégral",
    "credits": 3,
    "enseignantId": 1
  }'
```

## Configuration

Les secrets et paramètres sensibles peuvent être surchargés via variables d'environnement :

| Variable        | Description                  | Défaut (dev)                                     |
|-----------------|------------------------------|--------------------------------------------------|
| `JWT_SECRET`    | Clé de signature JWT (HS256) | valeur codée en dur dans `application-*.yaml`    |
| `JWT_SECRET` (prod) | idem                    | idem                                             |

> ⚠️ Le secret JWT par défaut est partagé entre dev et prod à titre d'exemple.
> En production, définissez toujours `JWT_SECRET` avec une clé forte et unique.

Profils disponibles (`spring.profiles.active` ou `-Dspring-boot.run.profiles`) :
- `dev` (défaut) : H2 en mémoire, `ddl-auto: create-drop`.
- `prod` : PostgreSQL, `ddl-auto: update`.

## Tests

```bash
./mvnw test
```

## Génération du JAR

```bash
./mvnw clean package
java -jar target/Gestion-Scolaire-0.0.1-SNAPSHOT.jar
```
