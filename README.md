# Projet de Collection de Satellites

## Description

Ce projet a pour objectif de construire une interface graphique permettant de répertorier des satellites. Il est développé en Java avec l'utilisation de FXML et suit l'architecture Modèle-Vue-Contrôleur (MVC) pour une organisation claire du code.

## Table des matières

- [Installation](#installation)
- [Utilisation](#utilisation)
- [Contribuer](#contribuer)
- [Licence](#licence)
- [Contact](#contact)

## Installation

1. Clonez le dépôt GitHub :
   ```
   git clone <lien-du-depot>
   ```

2. Assurez-vous d'avoir Java JDK installé sur votre système.

3. Importez le projet dans votre environnement de développement Java préféré.

## Utilisation

1. Ouvrez le projet dans votre IDE Java.

2. Compilez le code source.

3. Exécutez l'application.

4. L'interface graphique s'affiche avec deux types de vues disponibles : la vue globale et la vue détaillée.

5. Dans la vue globale :
    - En haut à gauche, vous trouverez un menu offrant la possibilité de quitter la fenêtre, de changer de mode d'édition ou d'ajouter un satellite. Vous pouvez également utiliser les raccourcis suivants : "Q" pour quitter et "C" pour changer de mode.
    - À côté du menu, vous pouvez gérer les données en utilisant les fonctionnalités de sauvegarde ("S") et d'importation ("N").
    - Vous pouvez également gérer les tags disponibles dans l'application, en ajoutant ou en retirant des tags, et en utilisant les options de tri pour filtrer les éléments en fonction des tags.
    - La zone de présence des satellites affiche la collection de satellites. Un compteur à droite répertorie le nombre de satellites dans la collection.
    - En mode édition, un bouton pour ajouter apparaît en bas à droite.

6. Pour passer à la deuxième vue, la vue détaillée, il suffit de cliquer sur l'un des satellites présents dans la zone de présence des satellites.

7. Dans la vue détaillée :
    - La barre de menu propose plusieurs options, dont un bouton "Menu" pour sauvegarder ou passer en mode édition, un bouton pour passer à la vue globale et un bouton pour ajouter des informations. Vous pouvez également utiliser le raccourci "N" pour ajouter une information.
    - En mode édition, vous pouvez modifier le nom du satellite et l'image associée. Assurez-vous que l'image se trouve dans le répertoire "ressources/image/utilisateur/".
    - La date affichée en bas correspond à la date de création du satellite dans la collection.

## Contribuer

Les contributions à ce projet sont les bienvenues. Pour contribuer, veuillez suivre les étapes ci-dessous :

1. Fork the repository and create your branch:
   ```
   git checkout -b nom-de-branche
   ```

2. Effectuez les modifications nécessaires et effectuez les commits :
   ```
   git commit -m "Description des modifications"
   ```

3. Poussez les modifications vers votre fork :
   ```
   git push origin nom-de-branche
   ```

4. Ouvrez une pull request vers ce dépôt principal.

## Licence

Le projet est sous licence MIT. Veuillez consul

ter le fichier [LICENSE](/chemin-vers-le-fichier/LICENSE) pour plus d'informations.

## Contact

Pour toute question ou préoccupation, veuillez me contacter à [votre-email@example.com](mailto:votre-email@example.com). Vous pouvez également visiter mon compte GitHub à [votre-nom-utilisateur](https://github.com/votre-nom-utilisateur).

---

Veuillez remplacer `<lien-du-depot>`, `votre-email@example.com` et `votre-nom-utilisateur` par les informations appropriées.