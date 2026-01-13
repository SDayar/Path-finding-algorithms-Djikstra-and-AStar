/**
 * @author SAIFIDINE Dayar
 * @version 1.1
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Classe qui implémente l'algorithme de Djikstra
 */
public final class Djikstra {
    /**
     * Liste qui où se seront stockés les étiquettes du meilleur chemin
     */
    private List<Integer> chemin = new ArrayList<>();
    /**
     * Le plus petit cout (ou la plus petite distance) entre deux points évalué par l'algorithme.
     */
    private double cout;
    /**
     * L'étiquette du sommet de départ dans le graphe.
     */
    private int depart;
    /**
     * L'étiquette du sommet d'arrivée dans le graphe.
     */
    private int arrivee;
    /**
     * Le graphe avec lequel l'algorithme sera appliqué.
     */
    private Graph graph;
    /**
     * Le nombre de sommets visités durant l'exécution de l'algorithme.
     */
    private int nbSommetsVisites;
    /**
     * une liste de priorité des sommets du graphe qui implémente la fonction "open" qui contiendra tous les sommets à visiter et les triera en ordre croissant selon les priorités f(n)= coutDepuisSource. Le sommet le plus prioriatire est celui qui aura le f(n) le plus bas.
     */
    private PriorityQueue<Sommet> open = new PriorityQueue<>(Comparator.comparingDouble(s->s.getCoutDepuisSource()));
     /**
     * désigne la structure qui décrit les sommets qui sont déjà parcourus. Un sommet déjà parcouru aura une valeur "true" dans le tableau close à travers son étiquette.
     */
    private boolean[] close; 
    
    /**
     * Constructeur de la classe Djikstra.
     * @param depart le sommet de départ appartennant au graphe avec lequel l'algorithme va s'exécuter.
     * @param arrivee le sommet d'arrivée. 
     * @param graph le graphe avec lequel l'algorithme va s'exécuter.
     **/

    public Djikstra(int depart, int arrivee, Graph graph){
        this.depart = depart;
        this.arrivee = arrivee;
        this.graph = graph;
        this.cout = 0;
        this.nbSommetsVisites=0;
    }
    /**
     * Méthode principale. La recherche du plus petit cout(de la plus petite distance) sera exécutée dans cette méthode.
     */
    public void launch(){
        Sommet sommetCourant = graph.getSommet(depart);//Le somet courant au début est le départ
        int[] predec = new int[graph.getNombreSommets()];//Tableau qui permettra de reconstruire le chemin emprunté.
        close = new boolean[graph.getNombreSommets()];
        for(int i = 0; i<predec.length; i++){
            predec[i]=-1;
        }
        sommetCourant.setCoutDepuisSource(0.0);
        open.add(sommetCourant);//On ajoute le premier sommet dans la liste des sommets à parcourir.
        double distVoisin;
        while(!open.isEmpty()){
            //Tant que'il existe des sommets à parcourir 'dans "open").
            sommetCourant = open.poll();// On recupère le premier sommet dans "open" qui est le sommet minimum.
            nbSommetsVisites++;//On incrémente le nombre de sommets visités.
            if(close[sommetCourant.getEtiquette()]){
                //Si ce sommet est déjà parcouru (appartient à close) on passe au suivant.
                continue;
            }
            close[sommetCourant.getEtiquette()] = true;
            if(sommetCourant.getEtiquette()==arrivee){
                //Si ce sommet est le sommet d'arrivée alors on reconstitue le chemin puis on quitte la méthode.
                chemin.removeAll(chemin);//On réinitialise "chemin" d'abord.
                 Sommet target = sommetCourant;
                if (target.getCoutDepuisSource() != Double.POSITIVE_INFINITY) {
                    //On vérifie bien que l'arrivée a une distance qui a été mise à jour avant d'être tiré par le "poll".
                    java.util.LinkedList<Integer> rev = new java.util.LinkedList<>();
                    int v = arrivee;
                    while (v != -1) {
                        //On reconstitue le chemin.
                        rev.addFirst(v);
                        v = predec[v];
                    }
                    chemin.addAll(rev);
                    cout = target.getCoutDepuisSource();//On recupère le cout.
                }else{
                    cout = Double.POSITIVE_INFINITY;
                }
                return;// On sort de la méthode.
            }
            //Sinon si on n'a pas encore trouvé le sommet d'arrivée, on regarde le voisinage du sommet courant.
            for(Sommet v : sommetCourant.getVoisins().keySet()){
                if(close[v.getEtiquette()]){
                    //Si le voisin est déjà parcouru on continue
                    continue;
                }
                //Sinon on cherche le voisin ayant la plus petite distance (seulement avec g(n)).
                Arrete e = sommetCourant.getVoisins().get(v);
                distVoisin = sommetCourant.getCoutDepuisSource()+e.getPoids();
                if(distVoisin<v.getCoutDepuisSource()){
                    //Si on a trouvé un voisin candidat on l'ajoute dans la liste des sommets à parcourir.
                    v.setCoutDepuisSource(distVoisin);
                    predec[v.getEtiquette()]=sommetCourant.getEtiquette();
                    //On l'efface d'abord dans "open" afin de s'assurer que le sommet avec le nouveau cout "distVoisin" existe dans la queue de priorité.
                    open.add(v);
                }

            }
        
        }
    }
    /**
     * Retourne le chemin le plus court déterminé  après l'exécution de l'algorithme.
     * @return le chemin le plus court
     */
    public  List<Integer> getChemin(){
        return chemin;
    }
    /**
     * Retourne le cout (la distance) la plus petite après l'exécution de l'algorithme.
     * @return le cout(la distance la plus petite). 
     */
    public double getCout(){
        return cout;
    }
    /**
     * Retourne le nombre de sommets visités lors de l'exécution de l'algorithme.
     * @return nombre de sommets visités.
     */
    public int getNbSommetsVisites(){
        return nbSommetsVisites;
    }
    /**
     * Affiche la solution sur le terminal.
     */
    public void printSolution(){
        System.out.print("Chemin : ");
        for(int e : chemin){
            System.out.print(e);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println("Cout = "+cout);
        System.out.println("Nombre de sommets visités : "+nbSommetsVisites+"/"+graph.getNombreSommets());

    }
}

