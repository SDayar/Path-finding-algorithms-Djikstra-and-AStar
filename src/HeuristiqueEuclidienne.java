/**
 * @author SAIFIDINE Dayar
 * @version 1.0
 */


/**
 * Implémente l'heuristique euclidiennne.
 */
public class HeuristiqueEuclidienne implements InterfaceHeuristique{
    /**
     * Le graphe qui contient les sommets sur lequels l'heuristique sera appliquée.
     */
    private Graph graph;
    /**
     * Le sommet d'arrivée.
     */
    private Sommet arrivee;
    /**
     * Le constructeur.
     * @param graph Le graphe qui contient les sommets à définir l'heuristique.
     * @param arrivee Le sommet d'arrivée.
     */
    public HeuristiqueEuclidienne(Graph graph, Sommet arrivee){
        this.graph = graph;
        this.arrivee = arrivee;
    } 

    /**
     * Implémente la fonction h(n) et affecte le cout (la distance euclidienne) des sommets du graphe jusqu'au sommet d'arrivée. Pour en savoir plus sur la formule appliquée il est conseillé de lire le compte rendu.
     */
    @Override
    public void appliquer(){
        if(graph.sommetExiste(arrivee.getEtiquette())){
            for(Sommet s : graph.getSommets()){
                //Pour chaque sommet du graphe on lui affecte une valeur heuristique
                double xCarree = Math.pow((s.getCoordonnee().getLigne()-arrivee.getCoordonnee().getLigne()),2);
                double yCarree = Math.pow((s.getCoordonnee().getColonne()-arrivee.getCoordonnee().getColonne()),2);
                double distEuclidienne = Math.sqrt(xCarree+yCarree);
                s.setHeuristique(distEuclidienne);
            }

        }
    }

}

