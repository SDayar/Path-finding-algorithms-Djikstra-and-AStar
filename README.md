🧭 TP7 - Algorithmes de Recherche de Chemin (Dijkstra & A*)
📋 Description du Projet
TP7 - Recherche de Chemin Optimal est une application Java complète implémentant les algorithmes de pathfinding Dijkstra et A* avec différentes heuristiques. Le projet permet de visualiser et comparer les performances de ces algorithmes sur différentes cartes de terrain à travers une interface graphique intuitive.

✨ Fonctionnalités Principales
🎯 Algorithmes Implémentés
Algorithme de Dijkstra (recherche exhaustive)

Algorithme A* avec 3 heuristiques :

Heuristique de Manhattan

Heuristique Euclidienne

Heuristique Octile (optimisée pour déplacements diagonaux)

🗺️ Système de Cartes
Support de différents types de terrain :

🟩 G (Herbe) : Coût = 1

🟨 S (Sable) : Coût = 30

🟦 B (Buisson) : Coût = 20

⬜ W (Eau) : Coût = 1000

Génération automatique de graphe à partir de fichiers texte

Visualisation graphique avec code couleur

🖥️ Interface Graphique
Interface utilisateur complète avec AWT

Sélection de fichiers de configuration

Visualisation du chemin optimal en temps réel

Affichage des statistiques (coût, sommets visités, temps)

Comparaison visuelle des algorithmes

🚀 Installation et Exécution
Prérequis
Java 8 ou supérieur

Aucune dépendance externe nécessaire

Installation
bash
# Cloner le repository
git clone https://github.com/votre-username/TP7-Pathfinding.git
cd TP7-Pathfinding

# Compiler le projet
javac -d bin src/**/*.java
Exécution
bash
# Lancer l'application
java -cp bin App
📁 Structure du Projet
text
TP7-Pathfinding/
├── src/
│   ├── AEtoile.java                 # Implémentation A*
│   ├── Djikstra.java                # Implémentation Dijkstra
│   ├── Graph.java                   # Structure de graphe
│   ├── Sommet.java                  # Représentation d'un sommet
│   ├── Arrete.java                  # Représentation d'une arête
│   ├── Coordonnee.java              # Coordonnées 2D
│   ├── InterfaceHeuristique.java    # Interface pour heuristiques
│   ├── HeuristiqueDeManhattan.java  # Heuristique Manhattan
│   ├── HeuristiqueEuclidienne.java  # Heuristique Euclidienne
│   ├── HeuristiqueOctile.java       # Heuristique Octile
│   ├── App.java                     # Point d'entrée
│   ├── InterfaceGraphiqueFichier.java # Interface graphique
│   └── TraitementFichier.java       # Traitement fichiers config
├── ressources/
│   ├── graph_petit.txt              # Carte de test 3x3
│   └── graph.txt                    # Carte complexe 50x100
└── README.md
🎮 Utilisation
1. Lancement de l'application
bash
java -cp bin App
2. Étapes d'utilisation
Choisir un fichier : Sélectionnez un fichier de configuration dans ressources/

Sélectionner l'algorithme : Choisissez entre Dijkstra ou A*

Choisir l'heuristique : Si A* est sélectionné, choisissez une heuristique

Exécuter : Cliquez sur "EXECUTER L'ALGORITHME"

Analyser les résultats : Visualisez le chemin et les statistiques

3. Exemple de Fichier de Configuration
txt
==Metadata==
=Size=
nlines=3
ncol=3
=Types=
G=1
W=1000
B=20
S=30
==Graph==
GGG
GSG
GBG
==Path==
Start=0,0
Finish=2,2
🏗️ Architecture Technique
Modèle de Données
Graph : Représente l'ensemble du graphe avec sommets et arêtes

Sommet : Noeud avec coordonnées, type de terrain et coût

Arrete : Connexion pondérée entre deux sommets

Coordonnee : Position (ligne, colonne) dans la grille

Algorithmes
Dijkstra : Utilise une file de priorité basée sur g(n) uniquement

A* : Utilise f(n) = g(n) + h(n) où h(n) est l'heuristique

Heuristiques : Différentes fonctions d'estimation de distance

Interface Graphique
Basée sur AWT (Abstract Window Toolkit)

Canvas pour le dessin de la grille

Composants pour la sélection et l'affichage

Thread séparé pour le traitement lourd

📊 Résultats et Comparaisons
Métriques Mesurées
Coût total du chemin trouvé

Nombre de sommets visités

Temps d'exécution

Pourcentage de la grille explorée

Comparaison Algorithmes
Algorithme	Meilleur pour	Performances	Exploration
Dijkstra	Garantie optimalité	Plus lent	Complète
A Manhattan*	Grilles orthogonales	Rapide	Ciblée
A Euclidienne*	Distances réelles	Moyenne	Modérée
A Octile*	Déplacements diagonaux	Optimale	Minimale
🔧 Développement
Compilation
bash
# Compilation manuelle
javac -d bin *.java

# Avec structure de packages
javac -d bin src/**/*.java
Ajout de Nouvelles Fonctionnalités
Implémenter une nouvelle heuristique en respectant InterfaceHeuristique

Ajouter un nouveau type de terrain dans TraitementFichier.java

Étendre l'interface graphique pour nouvelles options

Tests
bash
# Exécuter avec différentes cartes
java -cp bin App
# Puis tester avec graph_petit.txt et graph.txt
🧪 Exemples de Tests
Test 1 - Carte Simple
bash
# Utiliser graph_petit.txt (3x3)
# Résultats attendus : Chemin direct, tous les algorithmes similaires
Test 2 - Carte Complexe
bash
# Utiliser graph.txt (50x100)
# Résultats attendus : A* plus rapide, exploration réduite
Test 3 - Obstacles
bash
# Cartes avec obstacles d'eau (coût élevé)
# Vérifier que l'algorithme évite les zones coûteuses
📝 Format des Fichiers de Carte
Structure Obligatoire
text
==Metadata==
=Size=
nlines=<nombre_lignes>
ncol=<nombre_colonnes>
=Types=
<type1>=<coût1>
<type2>=<coût2>
...
==Graph==
<ligne1_types>
<ligne2_types>
...
==Path==
Start=<ligne_départ>,<colonne_départ>
Finish=<ligne_arrivée>,<colonne_arrivée>
Exemple Complet
txt
==Metadata==
=Size=
nlines=5
ncol=5
=Types=
G=1
W=1000
B=20
S=30
==Graph==
GGGGG
GWWGG
GSBGG
GGGGG
GGGGG
==Path==
Start=0,0
Finish=4,4
🤝 Contribution
Pour Contribuer
Fork le projet

Créer une branche (git checkout -b feature/amélioration)

Commiter les changements (git commit -m 'Ajout fonctionnalité')

Pusher (git push origin feature/amélioration)

Ouvrir une Pull Request

Standards de Code
Nommage : CamelCase pour les classes, camelCase pour les méthodes

Documentation : Javadoc pour toutes les classes publiques

Tests : Ajouter des tests pour nouvelles fonctionnalités

📚 Documentation Technique
Classes Principales
AEtoile.java
java
public final class AEtoile {
    // Implémentation complète de l'algorithme A*
    // Utilise f(n) = g(n) + h(n)
    // Supporte différentes heuristiques
}
Graph.java
java
public class Graph {
    // Représentation matricielle du graphe
    // Méthodes d'ajout de sommets et arêtes
    // Clonage pour isolation des exécutions
}
Heuristiques Implémentées
Manhattan
text
h(n) = |x1 - x2| + |y1 - y2|
Euclidienne
text
h(n) = √((x1 - x2)² + (y1 - y2)²)
Octile
text
h(n) = D × (dx + dy) + (D2 - 2×D) × min(dx, dy)
où D = coût déplacement orthogonal
D2 = √2 × D
🐛 Dépannage
Problèmes Courants
"Fichier introuvable"
bash
# Vérifier que le fichier existe dans ressources/
# Vérifier les permissions de lecture
"Out of Memory"
bash
# Augmenter la heap Java
java -Xmx1024m -cp bin App
Interface Graphique Non Responsive
bash
# Vérifier que les traitements lourds sont dans des threads séparés
# Vérifier les logs d'erreur
📈 Améliorations Futures
Planifiées
Support de cartes plus grandes (1000x1000)

Export des résultats en CSV/JSON

Comparaison côte-à-côte des algorithmes

Mode "pas à pas" pour visualiser l'exploration

Idées
Algorithmes supplémentaires (IDA*, JPS)

Génération procédurale de cartes

Interface Web avec JavaFX

Benchmarks automatisés

📝 Licence
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.

👨‍💻 Auteur
SAIFIDINE Dayar

Développement principal

Conception des algorithmes

Interface graphique

🙏 Remerciements
Université Paris Cité pour le cadre du projet

Les contributeurs de la communauté Java

Tous les testeurs ayant participé

<div align="center"> <p>⭐ Si ce projet vous a été utile, n'hésitez pas à lui donner une étoile !</p>
