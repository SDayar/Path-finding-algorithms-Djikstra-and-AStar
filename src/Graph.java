/**
 * @author SAIFIDINE Dayar
 * @version 1.1 
 */


import java.util.ArrayList;
import java.util.List;

/**
 * Implémente la structure d'un graphe à partir d'un fichier.
*/
public class Graph{

    /**
     * Le nombre de colonnes qu'est constitué le graphe.
     */
    private int nColonne;
    /**
     * Le nombre de lignes du graphe qu'est constitué le graphe.
     */
    private int  nLigne;
    /**
     * Le nombre de sommets à un instant t présents dans le graphe.
     */
    private int nombreSommets;
    /**
     * La liste de sommets présents dans le graphe à un instant t.
     */
    private List<Sommet> sommets;
    /**
     * La liste des arrêtes dans le graphes à un instant t.
     */
    private List<Arrete> arretes;


  
    /**
     * Constructeur du graphe. Au début le graphe est vide.
     */
    public Graph(){
        this.arretes = new ArrayList<>();
        this.sommets = new ArrayList<>();
        this.nColonne = this.nLigne = 0;
        this.nombreSommets=0;

    }
    /**
     * Permet de mettre à jour le nombre de colonnes qu'est composé le graphe.
     * @param n Le nouveau nombre de colonnes du graphe.
     */
    public void setColonne(int n){
        this.nColonne = n;
    }
    /**
     * Permet de mettre à jour le nombre de lignes qu'est composé le graphe.
     * @param n Le nouveau nombre de lignes du graphe.
     */
    public void setLigne(int n){
        this.nLigne = n;
    }
    /**
     * Permet de récupérer le nombre de lignes qu'est formé le graphe.
     * @return le nombre de lignes qu'est formé le graphe : la
     */
    public int getLigne(){
        return nLigne;
    }
    /**
     * Permet de récupérer le nombre de colonnes qu'est formé le graphe.
     * @return le nombre de colonnes qu'est constitué le graphe.
     */
    public int getColonne(){
        return nColonne;
    }
    /**
     * Permet d'ajouter un sommet dans le graphe avec son cout.
     * @param cout cout du sommet indiqué dans le fichier .txt
     * @param coordonnees la position du sommet sur le fichier de configuration.
     */
    public void ajouterSommet(double cout, Coordonnee coordonnees, String typeTerrain){
        if(nombreSommets<=nLigne*nColonne){
            Sommet s = new Sommet(nombreSommets, cout,coordonnees);
            s.setTypeTerrain(typeTerrain);
            this.sommets.add(s);
            nombreSommets+=1;
        }
    }
    /**
     * Permet d'ajouter une arrête entre deux sommets.
     * @param etiquette1 L'étiquette du sommet source.
     * @param etiquette2 L'étiquette du sommet destinataire.
     * @param poids ELle est calculée selon des modalités précises (voir le compte rendu).
     */
    public void ajouterArrete(int etiquette1, int etiquette2, double poids){
        if(sommetExiste(etiquette1)==true && sommetExiste(etiquette2)==true){
            Sommet sommet1 = sommets.get(etiquette1);
            Sommet sommet2 = sommets.get(etiquette2);
            Arrete nouvelleArrete = new Arrete(etiquette1, etiquette2, poids);
            if((sommet1.ajouterVoisin(sommet2, nouvelleArrete))==null){
                arretes.add(nouvelleArrete);
            }
        }else{
            if(sommetExiste(etiquette1)==false){
                System.out.println("Le sommet "+etiquette1+" n'existe pas.");
            }else{
                System.out.println("Le sommet "+etiquette2+" n'existe pas.");
            }
            
        }

    }
    /**
     * Permet de vérifier si un sommet existe dans le graphe ou non.
     * @param etiquette L'étiquette du sommet à vérifier.
     * @return{@code true} si le sommet existe.{@code false} sinon.
     */
    public boolean sommetExiste(int etiquette){
        if(etiquette < sommets.size() && etiquette>=0){
            return true;
        }
        return false;
    }
    /**
     * Permet de récupérer un sommet du graphe à condition qu'il existe.
     * @param etiquette Etiquette du sommet à recupérer.
     * @return un sommet s'il existe sinon null.
     */
    public Sommet getSommet(int etiquette){
        if(etiquette<sommets.size() && etiquette>=0){
            return sommets.get(etiquette);
        }
        return null;
    }
    /**
     * Permet de récupérer la liste des sommets à un instant t.
     * @return la liste des sommets du graphe.
     */
    public List<Sommet> getSommets(){
        return sommets;
    }
    /**
     * Permet de récupérer le nombre de sommets dans le graphe à un instant t.
     */
    public int getNombreSommets(){
        return nombreSommets;
    }
    /**
     * Permet de récupérer une arrête dans le graphe à condition qu'il existe.
     * @param etiquette1 Etiquette du sommet source de l'arrête.
     * @param etiquette2 Etiquette du sommet destinataire de l'arrête.
     * @return L'arrête représentée par les deux étiquettes des sommets indiquées en argument.
     */
    public Arrete getArrete(int etiquette1, int etiquette2){
        for(Arrete e : arretes){
            if(e.getSommet1()==etiquette1 && e.getSommet2()==etiquette2){
                return e;
            }
        }
        return null;
    }
     /**
     * Permet de récupérer un clone identique au graphe qui occupera une autre zone mémoire.
     * @return un clone du graphe résultant d'une deep copy.
     */
    @Override 
    public Graph clone(){
        Graph clone_graph = new Graph();
        clone_graph.setColonne(this.nColonne);
        clone_graph.setLigne(this.nLigne);
        
        for(Sommet s : sommets){
            clone_graph.ajouterSommet(s.getCout(), s.getCoordonnee().clone(), s.getTypeTerrain());
        }
        for(Arrete a : arretes){
            clone_graph.ajouterArrete(a.getSommet1(), a.getSommet2(), a.getPoids());   
        }
        return clone_graph;
    }
    
    /**
     * Permet d'attribuer un nouveau format pour représenter le graphe.
     * @return un nouveau formatage pour representé un graphe.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Sommet s : sommets){
        
            sb.append(s.getEtiquette());
            sb.append(" : ");
            for(Arrete e : s.getVoisins().values()){
                    sb.append(e);
                    sb.append(" | ");
            }
            sb.append("\n");//Saut de ligne
        }
        return sb.toString();
    }
}
