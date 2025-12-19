# 📚 Application E-Learning sécurisée avec Keycloak, React et Spring Boot

## 🧭 Vue globale du projet

Ce projet consiste à développer une **application E-learning sécurisée** permettant de gérer
l’authentification et l’autorisation des utilisateurs à l’aide de **Keycloak**, en respectant
les standards de sécurité modernes.

L’application propose deux types d’utilisateurs :

- 🧑‍💼 **Administrateur** : gestion des cours (création, administration)
- 🎓 **Étudiant** : consultation des cours et des informations pédagogiques

La gestion de l’authentification est **entièrement déléguée à Keycloak**, afin de libérer
l’application des responsabilités liées à la sécurité.

---

## 🎯 Objectifs du projet

Les objectifs principaux de ce projet sont :

- 🔐 Appliquer le principe **OpenID Connect**
- 🔁 Comprendre la délégation de l’authentification à une entité externe (Keycloak)
- 🪪 Mettre en œuvre une **authentification par token (JWT)**
- 👥 Gérer les **rôles des utilisateurs** via Keycloak (Admin / Student)
- 🔍 Comprendre le **processus global d’authentification et d’autorisation**
- ✅ Vérifier l’**identité de l’utilisateur à partir du token**
- 🧑‍💼 Faciliter l’accès de l’administrateur à la gestion des cours
- 🎓 Faciliter l’accès de l’étudiant à la consultation des cours

---

## 🗺️ Architecture générale du projet

Le projet repose sur une architecture **Front-End / Back-End sécurisée par Keycloak** :

1. L’utilisateur accède à l’application via le Front-End (React)
2. Le Front-End redirige l’utilisateur vers Keycloak pour l’authentification
3. Keycloak authentifie l’utilisateur et génère un **token JWT**
4. Le token est renvoyé au Front-End
5. Le Front-End envoie le token au Back-End (Spring Boot)
6. Le Back-End vérifie le token et autorise l’accès selon le rôle de l’utilisateur

📌 *Schéma global du projet* :
<img width="526" height="264" alt="image" src="https://github.com/user-attachments/assets/1a268935-27cd-4812-9ccb-28a84c4b3e7c" />

---

## 🔁 Flux d’authentification

- Redirection de l’utilisateur vers la page de login Keycloak
- Authentification de l’utilisateur
- Génération du token JWT
- Envoi du token au Back-End
- Vérification du token
- Extraction de l’identité et du rôle de l’utilisateur
- Autorisation ou refus d’accès aux ressources

---

## 🧩 Composants du projet

### 🔑 Keycloak

Keycloak est utilisé comme **serveur d’authentification et d’autorisation**.  
Il permet de :

- Gérer les utilisateurs
- Gérer les rôles (Admin / Student)
- Authentifier les utilisateurs
- Générer des tokens JWT
- Centraliser la sécurité de l’application

---

### ⚙️ Spring Boot (Back-End)

Le Back-End est développé avec **Spring Boot** et permet de :

- Vérifier la validité du token JWT
- Extraire l’identité de l’utilisateur à partir du token
- Extraire les rôles associés à l’utilisateur
- Protéger les endpoints selon les rôles
- Fournir les données nécessaires au Front-End

---

### 💻 React (Front-End)

Le Front-End est développé avec **React** et permet de :

- Afficher l’interface utilisateur
- Rediriger l’utilisateur vers Keycloak pour l’authentification
- Stocker et transmettre le token JWT
- Afficher une interface différente selon le rôle :
  - Interface Administrateur
  - Interface Étudiant

---

## 🚀 Technologies utilisées

- **React** – Front-End
- **Spring Boot** – Back-End
- **Keycloak** – Authentification et Autorisation
- **OpenID Connect**
- **JWT (JSON Web Token)**

---

## ✅ Conclusion

Ce projet permet de comprendre et d’appliquer les concepts fondamentaux
de la sécurité des applications web modernes, notamment :

- L’authentification déléguée
- La gestion des rôles
- L’authentification par token
- La séparation des responsabilités entre les composants

---
