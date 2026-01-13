/**
 * @author SAIFIDINE Dayar
 * @version 1.1
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/** 
 * Classe chargee de lire et traiter les fichiers de configuration pour generer un graphe.
 * Analyse un fichier texte contenant les parametres d'une grille, les types de terrains,
 * et les positions de depart et d'arrivee pour creer un graphe correspondant.
 */
public final class TraitementFichier {
    /**
     * Fichier de configuration a traiter.
     */
    private File fichier;
    
    /**
     * Chemin absolu vers le repertoire contenant le fichier.
     */
    private String cheminAbsolu;
    
    /**
     * Coordonnees du sommet de depart dans le graphe.
     */
    private Coordonnee depart;
    
    /**
     * Coordonnees du sommet d'arrivee dans le graphe.
     */
    private Coordonnee arrivee;
    
    /**
     * Graphe genere a partir du fichier de configuration.
     */
    private Graph graph;
    
    /**
     * Constructeur de la classe FileTreatment.
     * Initialise les chemins et prepare le fichier de configuration a traiter.
     * @param nomFichier Nom du fichier de configuration a traiter
     */
    public TraitementFichier(File fichier) {
        try {
         
            this.fichier = fichier;
            graph = new Graph();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Erreur d'initialisation: " + e.getMessage());
        }
    }
    
    /**
     * Methode principale de traitement du fichier.
     * Lit le fichier ligne par ligne, extrait les parametres et genere le graphe.
     * 
     * @return true si le traitement a reussi, false sinon
     */
    public boolean launch() {
        try {
            Scanner scanner = new Scanner(fichier);
            String donnee = "";
            int nbLigne, nbColonne;
            
            for (int i = 1; i <= 3; i++) {
                donnee = scanner.nextLine();
            }
            nbLigne = Integer.parseInt(donnee.split("=")[1]);
            donnee = scanner.nextLine();
            nbColonne = Integer.parseInt(donnee.split("=")[1]);
            graph.setLigne(nbLigne);
            graph.setColonne(nbColonne);
            
            Map<String, Integer> typesTemps = new HashMap<>();
            donnee = scanner.nextLine();
            donnee = scanner.nextLine();
            
            while (donnee.compareTo("==Graph==") != 0) {
                String type = donnee.split("=")[0];
                int temps = Integer.parseInt(donnee.split("=")[1]);
                donnee = scanner.nextLine();
                typesTemps.putIfAbsent(type, temps);
                donnee = scanner.nextLine();
            }
            
            for (int l = 0; l < nbLigne; l++) {
                donnee = scanner.nextLine();
                for (int c = 0; c < nbColonne; c++) {
                    String type = String.valueOf(donnee.charAt(c));
                    graph.ajouterSommet(typesTemps.get(type), new Coordonnee(l, c), type);
                }
            }
            
            donnee = scanner.nextLine();
            donnee = scanner.nextLine();
            
            int l = Integer.parseInt((donnee.split("=")[1]).split(",")[0]);
            int c = Integer.parseInt((donnee.split("=")[1]).split(",")[1]);
            depart = new Coordonnee(l, c);
            donnee = scanner.nextLine();
            l = Integer.parseInt((donnee.split("=")[1]).split(",")[0]);
            c = Integer.parseInt((donnee.split("=")[1]).split(",")[1]);
            arrivee = new Coordonnee(l, c);
            scanner.close();
            
            int sommet1, sommet2;
            double poids;
            for (int ligne = 0; ligne < nbLigne; ligne++) {
                for (int colonne = 0; colonne < nbColonne; colonne++) {
                    sommet1 = ligne * nbColonne + colonne;
                    int[] d1 = {-1, 1, 0, 0, -1, -1, 1, 1};
                    int[] d2 = {0, 0, -1, 1, -1, 1, -1, 1};
                    
                    for (int k = 0; k < 8; k++) {
                        int iLigne = ligne + d1[k];
                        int iColonne = colonne + d2[k];
                        
                        if (iLigne < 0 || iLigne >= nbLigne || iColonne < 0 || iColonne >= nbColonne) 
                            continue;
                        sommet2 = iLigne * nbColonne + iColonne;
                        double cout1 = graph.getSommet(sommet1).getCout();
                        double cout2 = graph.getSommet(sommet2).getCout();
                        
                        boolean estDiagonal = (k >= 4);
                        if (!estDiagonal) {
                            poids = (cout1 + cout2) / 2;
                        } else {
                            double diag1 = Math.sqrt(Math.pow(cout1 / 2, 2) + Math.pow(cout1 / 2, 2));
                            double diag2 = Math.sqrt(Math.pow(cout2 / 2, 2) + Math.pow(cout2 / 2, 2));
                            poids = diag1 + diag2;
                        }
                        graph.ajouterArrete(sommet1, sommet2, poids);
                    }
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Fichier introuvable: " + e.getMessage());
        }
    }
    
    /**
     * Lance le traitement et retourne le graphe genere.
     * @return Le graphe cree a partir du fichier
     */
    public Graph getGraph() {
        return graph;
    }
    
    /**
     * Retourne les coordonnees du sommet de depart.
     * @return Coordonnees du depart
     */
    public Coordonnee getDepart() {
        return depart;
    }
    
    /**
     * Retourne les coordonnees du sommet d'arrivee.
     * @return Coordonnees de l'arrivee
     */
    public Coordonnee getArrivee() {
        return arrivee;
    }
}