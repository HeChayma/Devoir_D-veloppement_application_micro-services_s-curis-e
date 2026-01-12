# 🛒 Plateforme E-Commerce Micro-Services Sécurisée

## 🧭 Vue globale du projet
Ce projet est une plateforme **E-Commerce haut de gamme** basée sur une architecture **micro-services** moderne. Elle intègre une gestion complète des produits et des commandes, sécurisée par **Keycloak** via des tokens **JWT**.

L'application distingue deux rôles principaux :
- 🧑‍💼 **ADMIN** : Gestion du catalogue (Ajout, Modification, Suppression de produits) et validation des commandes clients.
- 🛍️ **CLIENT** : Consultation du catalogue, gestion du profil personnel et passage de commandes.

---

## 🏗️ Architecture Technique
L'architecture repose sur le découpage en micro-services pour assurer la scalabilité et la robustesse du système.

### 🗺️ Schéma d'Architecture
![Architecture Globale](file:///C:/Users/GigaLap/.gemini/antigravity/brain/501841d3-649e-4a1b-962f-1a860bdb6224/uploaded_image_1_1768180325453.png)

### 🧩 Composants Principaux
- **API Gateway (Port 8086)** : Point d'entrée unique, gère le routage et la validation centrale des tokens JWT.
- **Micro-Service Produits (Port 8083)** : Gestion du catalogue et des stocks (Stockage : MySQL).
- **Micro-Service Commandes (Port 8082)** : Gestion des ventes et historique client (Stockage : MySQL).
- **Keycloak (Port 8080)** : Serveur d'Identité (IdP) gérant OAuth2 et OpenID Connect.
- **Frontend React (Port 3000)** : Interface utilisateur moderne et réactive.

---

## 🔐 Modèle de Données et Rôles
Le système suit une hiérarchie stricte basée sur les rôles extraits du token Keycloak.

![Modèle de Classes et Rôles](file:///C:/Users/GigaLap/.gemini/antigravity/brain/501841d3-649e-4a1b-962f-1a860bdb6224/uploaded_image_2_1768180325453.png)

### 👥 Capacités par Rôle
| Fonctionnalité | CLIENT | ADMIN |
| :--- | :---: | :---: |
| Lister les produits | ✅ | ✅ |
| Voir son profil | ✅ | ✅ |
| Passer une commande | ✅ | ❌ |
| Voir ses commandes | ✅ | ❌ |
| Ajouter/Supprimer des produits | ❌ | ✅ |
| Valider les commandes globales | ❌ | ✅ |

---

## 🚀 Déploiement avec Docker
La plateforme est entièrement conteneurisée pour un déploiement simplifié.

### Prérequis
- Docker & Docker Compose installés.

### Lancement
```bash
# À la racine du projet
docker-compose up --build
```

### Accès aux services
- **Frontend** : [http://localhost:3000](http://localhost:3000)
- **Keycloak** : [http://localhost:8080](http://localhost:8080)
- **H2 Console (Debug)** : Accessibles via les ports respectifs des services.

---

## 🛠️ Technologies Utilisées
- **Frontend** : React.js, Axios, Keycloak-js, CSS3 (Design Système sur mesure).
- **Backend** : Spring Boot 3, Spring Cloud Gateway, Spring Security OAuth2.
- **Base de données** : MySQL 8.0 & H2 (In-memory).
- **Conteneurisation** : Docker & Docker Compose.
- **Sécurité** : Keycloak (JWT, RBAC).

