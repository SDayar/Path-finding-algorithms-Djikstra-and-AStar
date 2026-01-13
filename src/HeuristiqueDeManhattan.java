/**
 * @author SAIFIDINE Dayar
 * @version 1.0
 */


/**
 * Implémente l'heuristique de Manhattah ou la distance de Manhattan.
 */
public class HeuristiqueDeManhattan implements InterfaceHeuristique{
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
    public HeuristiqueDeManhattan(Graph graph, Sommet arrivee){
        this.graph = graph;
        this.arrivee = arrivee;
    } 
     /**
     * Implémente la fonction h(n) et affecte le cout (la distance de Manhattan) des sommets du graphe jusqu'au sommet d'arrivée. Pour en savoir plus sur la formule appliquée il est conseillé de lire le compte rendu.
     */
    @Override
    public void appliquer(){
        if(graph.sommetExiste(arrivee.getEtiquette())){
            for(Sommet s : graph.getSommets()){
                //Pour chaque sommet du graphe on lui affecte une valeur heuristique
                double xCarree = Math.abs((s.getCoordonnee().getLigne()-arrivee.getCoordonnee().getLigne()));
                double yCarree = Math.abs((s.getCoordonnee().getColonne()-arrivee.getCoordonnee().getColonne()));
                double distEuclidienne =xCarree+yCarree;
                s.setHeuristique(distEuclidienne);
            }

        }
        

    }

}