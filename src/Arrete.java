/**
 * @author SAIFIDINE Dayar
 * @version 1.1
 */

/**
 * Implémente une arrête entre deux sommets (source et destinataire) d'un graphe.
 * (source)------->(destinataire)
 */
public class Arrete implements Cloneable{
    /**
     * L'étiquette du sommet source.
     */
    private int sommet1;
    /**
     * L'étiquette du sommet destinataire.
     */
    private int sommet2;
    /**
     * Le poids de l'arrête
     */
    private double poids;
    /**
     * Construteur de la classe
     * @param sommet1 Etiquette du sommet source
     * @param sommet2 Etiquette du sommet destinataire
     * @param poids   Poids du sommet
     */
    public Arrete(int sommet1,int sommet2, double poids){
        this.sommet1 = sommet1;
        this.sommet2 =sommet2;
        this.poids = poids;
    }
    /**
     * Permet de récupérer l'étiquette du sommet source.
     * @return le sommet source.
     */
    public int getSommet1(){
            return sommet1;
    }
    /**
     * Permet de récupérer l'étiquette du sommet destinataire.
     * @return le sommet destinataire.
     */
    public int getSommet2(){
        return sommet2;
    }
    /**
     * Permet de récupérer le poids de l'arrête.
     * @return poids de l'arrête.
     */
    public double getPoids(){
        return poids;
    }
    /**
     * Retourne la représentation d'une arrête qui est formaté comme ci-contre: source destinataire poids.
     * @return la représentation d'une arrête
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(sommet1);
        sb.append(" ");
        sb.append(sommet2);
        sb.append(" ");
        sb.append(poids);
        return sb.toString();
    }
    /**
     * Implémente la méthode de clonage.
     */
    @Override 
    public Arrete clone(){
        return new Arrete(sommet1, sommet2, poids);
    }
    
 }
