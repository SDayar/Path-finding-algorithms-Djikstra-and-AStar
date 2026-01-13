/**
 * @author SAIFIDINE Dayar
 * @version 1.0
 */


/**
 * Implémente la postion(coordonnées) d'un sommet dans le graphe
 */
public class Coordonnee {
    /**
     * La colonne où se situe le sommet.
     */
    private int colonne;
    /**
     * La ligne où se situe le sommet.
     */
    private int ligne;
    /**
     * Constructeur de la classe.
     * @param ligne La ligne où se situe le sommet sur le graphe.
     * @param colonne La colonne où se situe le sommet sur le graphe.
     */
    public Coordonnee(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
    }
    /**
     * Permet de récupérer la ligne où se situe le sommet.
     * @return la ligne où se situe le sommet.
     */
    public int getLigne(){
        return ligne;
    }
    /**
     * Permet de mettre à jour la ligne où se situe le sommet dans le graphe.
     * @param ligne La nouvelle ligne où se situe le sommet.
     */
    public void setLigne(int ligne){
        this.ligne = ligne;
    }
    /**
     * Permet de récupérer la colonne où se situe le sommet.
     * @return la colonne om se situe le sommet.
     */
    public int getColonne(){
        return colonne;
    }
    /**
     * Permet de mettre à jour la colonne où se situe le sommet.
     * @param colonne La nouvelle colonne où se situe le sommet dans le graphe.
     */
    public void setColonne(int colonne){
        this.colonne = colonne;
    }
     /**
     * Implémente la méthode de clonage.
     */
    public Coordonnee clone(){
        return new Coordonnee(this.getLigne(), this.getColonne());
    }
}
