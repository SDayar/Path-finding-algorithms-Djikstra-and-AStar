/**
 * @author SAIFIDINE Dayar
 * @version 1.0
 */
import java.util.HashMap;
import java.util.Map;
/**
 * Implémente un sommet et ses caractéristiques.
 */
public class Sommet implements Cloneable{
    /**
     * Coordoonnées du sommet : ligne x colonne ou weidht x height sur l'image.
     */
    private Coordonnee coordonnees;
    /**
     * Etiquette du sommet.
     */
    private int etiquette;
    /**
     * Distance théorique du sommet à un autre sommet du graphe. Elle est initialisée à plus l'infinie.
     */
    private double heuristique;
    /**
     * Cout affectée au sommet.
     */
    private double cout;
    /**
     * Dictionnaire des sommets voisins ayant comme valeur respectif, l'arrête qui en résulte de ce voisinage.
     */
    private Map<Sommet, Arrete> voisins;
    /**
     * La distance depuis un sommets source. Elle est initialisée à plus l'infinie.
     */
    private double CoutDepuisSource;
    /**
     * Indique si le sommet est parcouru (true) ou non (false) par un algorithe tiers.
     */
    public boolean parcouru;
    private String typeTerrain = null;
    /**
     * Constructeur d'un sommet.
     * @param etiquette Etiquette du sommet.
     * @param cout Cout du sommet indiqué sur le fichier .txt
     * @param coordonnees Position du sommet sur le graphe.
     */
    public Sommet(int etiquette, double cout, Coordonnee coordonnees){
        this.etiquette = etiquette;
        this.heuristique = Double.MAX_VALUE;//Plus grance heuristique (le plus éloigné de la destination
        this.CoutDepuisSource = Double.POSITIVE_INFINITY;
        this.cout = cout; 
        this.coordonnees = coordonnees;
        this.parcouru=false;
        voisins = new HashMap<>();
    }
    /**
     * Permet de recupérer l'étiquette du sommet.
     * @return étiquette du sommet
     */
    public int getEtiquette(){
        return etiquette;
    }
    /**
     * Permet de récupérer le cout du sommet.
     * @return le cout du sommet.
     */
    public double getCout(){
        return cout;
    }
    /**
     * Permet de recupérer les coordonnnées du sommet.
     * @return les coordonnées(positions) du sommet.
     */
    public Coordonnee getCoordonnee(){
        return coordonnees;
    }
    /**
     * Permet d'ajouter un voisin et l'arrête qui correspond à ce voisinage.
     * @return l'arrête entre ce sommet et le sommet destinataire.
     */
    public Arrete ajouterVoisin(Sommet sommet, Arrete arrete){
        return voisins.putIfAbsent(sommet, arrete);
        
    }
    /**
     * Permet de récupérer tout le voisinage du sommet.
     * @return le voisinage du sommet.
     */
    public Map<Sommet, Arrete> getVoisins(){
        return voisins;
    }
    /**
     * Permet de mettre à jour le cout du sommet depuis un sommet source.
     * @param nouveauCout nouveau cout depuis un sommet source.
     */
    public void setCoutDepuisSource(double nouveauCout){
        this.CoutDepuisSource = nouveauCout;
    }
    /**
     * Permet de récupérer le cout depuis un sommet source.
     * @return le cout depuis un sommet source.
     */
    public double getCoutDepuisSource(){
        return CoutDepuisSource;
    }
    /**
     * Permet de mettre à jour la valeur h(n) du sommet. 
     */
    public void setHeuristique(double heuristique){
        this.heuristique = heuristique;
    }
    /**
     * Permet de récuper la veleur h(n) du sommet.
     * @return la valeur heuristique affectée au sommet.
     */
    public double getHeuristique(){
        return heuristique;
    }
    /**
     * Permet de mettre à jour tout le voisinage de sommet.
     * @param nouv_voisins le nouveau voisinage de sommet.
     */
    public void setVoisins(Map<Sommet, Arrete> nouv_voisins){
        this.voisins = nouv_voisins;
    }
    public String getTypeTerrain(){
        return typeTerrain;
    }
    public void setTypeTerrain(String typeTerrain){
        this.typeTerrain = typeTerrain;
    }
    /**
     * Permet de comparer des sommets selon leur étiquette.
     * @param o Un objet
     * @return {@code true} si l'objet en argument est un sommet et a le même étiquette que l'objet "this" et false sinon.
     */
    @Override
    public boolean equals(Object o){
       if(o == null || o instanceof Sommet == false){
        return false;
       }else if(o==this){
        return true;
       }
       else{
        Sommet v = (Sommet)o;
        if(v.getEtiquette()==getEtiquette()){
            return true;

        }return false;
       }
    }
    /**
     * Implémente la méthode de clonage
     */
    @Override 
    public Sommet clone(){
        Sommet s= new Sommet(this.etiquette, this.cout, this.coordonnees);//Un clone aura ces mêmes informations
        return s;
    }
 }

