/**
 * @author SAIFIDINE Dayar
 * @version 1.1
 */


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Interface graphique pour la visualisation.
 * Permet de charger un fichier de grille, selectionner des algorithmes et visualiser
 * les chemins optimaux avec Dijkstra ou A*.
 */
public final class InterfaceGraphiqueFichier extends Frame {
    
    private Button choisirButton;
    private Label fichierLabel;
    private Choice algoChoice;
    private Choice heuristiqueChoice;
    private Button executerButton;
    private Button resetButton;
    private Canvas imageCanvas;
    private TextArea infoArea;
    private TextArea resultArea;
    private Label coutLabel;
    private Label sommetsLabel;
    private Label tempsLabel;
    private File fichier;
    private Graph graph;
    private Coordonnee depart;
    private Coordonnee arrivee;
    private List<Integer> chemin;

    /**
     * Constructeur de l'interface graphique
     */
    public InterfaceGraphiqueFichier() {
        super("TP7 - Recherche de chemin le plus court (Djikstra et A*) ");
        initUI();
        configurerEcouteurs();
    }
    
    /**
     * Initialise l'interface utilisateur
     */
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setSize(1100, 750);
        
        Panel panelGauche = new Panel();
        panelGauche.setLayout(new GridLayout(6, 1, 10, 10));
        panelGauche.setBackground(new Color(240, 240, 240));
        panelGauche.setPreferredSize(new Dimension(300, 0));
        
        Panel panelFichier = creerPanelCarte("FICHIER CONFIGURATION");
        choisirButton = new Button("Choisir un fichier");
        choisirButton.setBackground(new Color(0, 120, 215));
        choisirButton.setForeground(Color.WHITE);
        choisirButton.setFont(new Font("Arial", Font.BOLD, 12));
        fichierLabel = new Label("Aucun fichier selectionné", Label.CENTER);
        fichierLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panelFichier.setLayout(new BorderLayout(5, 5));
        panelFichier.add(choisirButton, BorderLayout.NORTH);
        panelFichier.add(fichierLabel, BorderLayout.CENTER);
        
        Panel panelAlgo = creerPanelCarte("ALGORITHME");
        panelAlgo.setLayout(new GridLayout(2, 2, 10, 10));
        panelAlgo.add(new Label("Algorithme:", Label.RIGHT));
        algoChoice = new Choice();
        algoChoice.add("Dijkstra");
        algoChoice.add("A*");
        panelAlgo.add(algoChoice);
        panelAlgo.add(new Label("Heuristique:", Label.RIGHT));
        heuristiqueChoice = new Choice();
        heuristiqueChoice.add("Manhattan");
        heuristiqueChoice.add("Euclidienne");
        heuristiqueChoice.add("Octile");
        heuristiqueChoice.setEnabled(false);
        panelAlgo.add(heuristiqueChoice);
        
        Panel panelActions = creerPanelCarte("ACTIONS");
        panelActions.setLayout(new GridLayout(2, 1, 10, 10));
        executerButton = new Button("EXECUTER L'ALGORITHME");
        executerButton.setBackground(Color.GREEN);
        executerButton.setForeground(Color.WHITE);
        executerButton.setFont(new Font("Arial", Font.BOLD, 12));
        executerButton.setEnabled(false);
        resetButton = new Button("REINITIALISER");
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 12));
        resetButton.setEnabled(false);
        panelActions.add(executerButton);
        panelActions.add(resetButton);
        
        Panel panelResultats = creerPanelCarte("RESULTATS");
        panelResultats.setLayout(new GridLayout(2, 1, 10, 10));
        Panel panelCout = new Panel(new BorderLayout());
        panelCout.add(new Label("Cout total", Label.CENTER), BorderLayout.NORTH);
        coutLabel = new Label("-", Label.CENTER);
        coutLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coutLabel.setForeground(new Color(0, 100, 200));
        panelCout.add(coutLabel, BorderLayout.CENTER);
        
        Panel panelSommets = new Panel(new BorderLayout());
        panelSommets.add(new Label("Sommets visites", Label.CENTER), BorderLayout.NORTH);
        sommetsLabel = new Label("-", Label.CENTER);
        sommetsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sommetsLabel.setForeground(new Color(0, 100, 200));
        panelSommets.add(sommetsLabel, BorderLayout.CENTER);
       
        tempsLabel = new Label("-", Label.CENTER);
        tempsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tempsLabel.setForeground(new Color(0, 100, 200));
        
        panelResultats.add(panelCout);
        panelResultats.add(panelSommets);
        
        panelGauche.add(panelFichier);
        panelGauche.add(panelAlgo);
        panelGauche.add(panelActions);
        panelGauche.add(panelResultats);
        panelGauche.add(new Panel());
        panelGauche.add(new Panel());
        
        Panel panelCentre = new Panel(new BorderLayout(10, 10));
        
       imageCanvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                peindre(g);
            }
            
            @Override
            public void update(Graphics g) {
                paint(g);
            }
            
            /**
             * Dessine le contenu du canvas
             */
            private void peindre(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                if (graph == null) {
                    g.setColor(Color.DARK_GRAY);
                    g.setFont(new Font("Arial", Font.BOLD, 18));
                    String msg = "VISUALISATION DE LA GRILLE";
                    FontMetrics fm = g.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth(msg)) / 2;
                    int y = getHeight() / 2 - 20;
                    g.drawString(msg, x, y);
                    
                    g.setFont(new Font("Arial", Font.PLAIN, 14));
                    msg = "Choisissez un fichier de configuration pour commencer";
                    fm = g.getFontMetrics();
                    x = (getWidth() - fm.stringWidth(msg)) / 2;
                    y = getHeight() / 2 + 10;
                    g.drawString(msg, x, y);
                } else {
                    dessinerGrille(g);
                }
            }
        };
        
        imageCanvas.setBackground(Color.LIGHT_GRAY);
        imageCanvas.setPreferredSize(new Dimension(700, 500));
        
        Panel panelInstructions = creerPanelCarte("INSTRUCTIONS");
        infoArea = new TextArea(6, 50);
        infoArea.setText("Bienvenue dans l'interface de visualisation du graphe. \n\n" +
                        "1. Choisissez un fichier de configuration (.txt)\n" +
                        "2. Le systeme génère automatiquement le graphe\n" +
                        "3. Choisissez l'algorithme et l'heuristique (pour A*)\n" +
                        "4. Cliquez sur EXECUTER pour trouver le chemin optimal");
        infoArea.setEditable(false);
        infoArea.setBackground(Color.WHITE);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInstructions.setLayout(new BorderLayout());
        panelInstructions.add(infoArea, BorderLayout.CENTER);
        
        panelCentre.add(imageCanvas, BorderLayout.CENTER);
        panelCentre.add(panelInstructions, BorderLayout.SOUTH);
        
        Panel panelBas = new Panel(new BorderLayout(5, 5));
        Panel panelTitreResultats = new Panel();
        panelTitreResultats.setBackground(new Color(220, 230, 240));
        panelTitreResultats.add(new Label("RESULTATS D'EXECUTION", Label.CENTER));
        panelTitreResultats.setFont(new Font("Arial", Font.BOLD, 14));
        
        resultArea = new TextArea(5, 80);
        resultArea.setText("Aucun fichier charge. Veuillez selectionner un fichier.");
        resultArea.setEditable(false);
        resultArea.setBackground(Color.WHITE);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panelBas.add(panelTitreResultats, BorderLayout.NORTH);
        panelBas.add(resultArea, BorderLayout.CENTER);
        
        add(panelGauche, BorderLayout.WEST);
        add(panelCentre, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);
        
        Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((ecran.width - 1100) / 2, (ecran.height - 750) / 2);
        setVisible(true);
    }
    
    /**
     * Crée un panneau avec un titre stylisé
     */
    private Panel creerPanelCarte(String titre) {
        Panel panel = new Panel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        
        if (titre != null && !titre.isEmpty()) {
            Panel panelTitre = new Panel();
            panelTitre.setBackground(new Color(230, 240, 250));
            panelTitre.setLayout(new FlowLayout(FlowLayout.LEFT));
            Label labelTitre = new Label("  " + titre + "  ");
            labelTitre.setFont(new Font("Arial", Font.BOLD, 12));
            labelTitre.setForeground(new Color(0, 80, 160));
            panelTitre.add(labelTitre);
            panel.add(panelTitre, BorderLayout.NORTH);
        }
        
        Panel panelContenu = new Panel();
        panelContenu.setBackground(Color.WHITE);
        panel.add(panelContenu, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Configure les écouteurs d'événements
     */
    private void configurerEcouteurs() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        choisirButton.addActionListener(e -> choisirFichier());
        executerButton.addActionListener(e -> executerAlgorithme());
        resetButton.addActionListener(e -> reinitialiser());
        
        algoChoice.addItemListener(e -> {
            boolean estAStar = "A*".equals(algoChoice.getSelectedItem());
            heuristiqueChoice.setEnabled(estAStar);
            if (!estAStar) {
                heuristiqueChoice.select(0);
            }
        });
    }
    
    /**
     * Ouvre une boîte de dialogue pour choisir un fichier
     */
    private void choisirFichier() {
        FileDialog dialogue = new FileDialog(this, "Choisir un fichier", FileDialog.LOAD);
        dialogue.setFile("*.txt");
        
        try {
            String repertoireCourant = new File("").getAbsolutePath();
            String cheminRessources = repertoireCourant + File.separator + "CodePartB" + 
                                    File.separator + "TP7" + File.separator + "ressources";
            File dossierRessources = new File(cheminRessources);
            
            if (dossierRessources.exists()) {
                dialogue.setDirectory(cheminRessources);
            } else {
                dossierRessources.mkdirs();
                dialogue.setDirectory(cheminRessources);
                resultArea.setText("Dossier créé: " + cheminRessources);
            }
            
            dialogue.setVisible(true);
            
            String repertoire = dialogue.getDirectory();
            String nomFichier = dialogue.getFile();
            
            if (nomFichier != null) {
                fichier = new File(repertoire, nomFichier);
                String nomCourt = nomFichier.length() > 20 ? nomFichier.substring(0, 17) + "..." : nomFichier;
                fichierLabel.setText(nomCourt);
                resultArea.setText("Chargement du fichier " + nomFichier + "...");
                
                traiterFichier();
            }
        } finally {
            dialogue.dispose();
        }
    }
    
    /**
     * Traite le fichier sélectionné et charge le graphe
     */
    private void traiterFichier() {
        if (fichier == null) return;
        
        chemin = null;
        coutLabel.setText("-");
        sommetsLabel.setText("-");
        tempsLabel.setText("-");
        graph = null;
        
        imageCanvas.repaint();
        
        new Thread(() -> {
            try {
                TraitementFichier traitement = new TraitementFichier(fichier);
                traitement.launch();
                graph = traitement.getGraph();
                depart = traitement.getDepart();
                arrivee = traitement.getArrivee();
                
                EventQueue.invokeLater(() -> {
                    if (graph != null && depart != null && arrivee != null) {
                        int etiquetteDepart = depart.getLigne() * graph.getColonne() + depart.getColonne();
                        int etiquetteArrivee = arrivee.getLigne() * graph.getColonne() + arrivee.getColonne();
                        
                        resultArea.setText("Fichier traité avec succès.\n" +
                                         "Graphe: " + graph.getNombreSommets() + " sommets (" + 
                                         graph.getLigne() + "x" + graph.getColonne() + ")\n" +
                                         "Départ: (" + depart.getLigne() + "," + depart.getColonne() + ")\n" +
                                         "Arrivée: (" + arrivee.getLigne() + "," + arrivee.getColonne() + ")");
                        resetButton.setEnabled(true);
                        executerButton.setEnabled(true);
                        
                        imageCanvas.repaint();
                        
                    } else {
                        resultArea.setText("Erreur: Impossible de charger le graphe.");
                    }
                });
                
            } catch (IllegalArgumentException e) {
                EventQueue.invokeLater(() -> {
                    resultArea.setText("Erreur de chargement: " + e.getMessage());
                    graph = null;
                    imageCanvas.repaint();
                });
            } catch (Exception e) {
                EventQueue.invokeLater(() -> {
                    resultArea.setText("Erreur lors du traitement: " + e.getMessage());
                    graph = null;
                    imageCanvas.repaint();
                });
            }
        }).start();
    }
    
    /**
     * Dessine la grille du graphe sur le canvas
     */
    private void dessinerGrille(Graphics g) {
        if (graph == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        int nbLignes = graph.getLigne();
        int nbColonnes = graph.getColonne();
        
        int canvasW = imageCanvas.getWidth();
        int canvasH = imageCanvas.getHeight();
        
        int tailleCase = Math.min(canvasW / nbColonnes, canvasH / nbLignes);
        int offsetX = (canvasW - tailleCase * nbColonnes) / 2;
        int offsetY = (canvasH - tailleCase * nbLignes) / 2;
        
        Map<String, Color> couleursTerrain = new HashMap<>();
        couleursTerrain.put("G", Color.GREEN);
        couleursTerrain.put("W", Color.GRAY);
        couleursTerrain.put("B", Color.BLUE);
        couleursTerrain.put("S", Color.YELLOW);
        
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                int etiquette = ligne * nbColonnes + colonne;
                Sommet sommet = graph.getSommet(etiquette);
                int x = offsetX + colonne * tailleCase;
                int y = offsetY + ligne * tailleCase;
                
                String type = sommet.getTypeTerrain();
                Color couleur = couleursTerrain.getOrDefault(type, Color.LIGHT_GRAY);
                g2d.setColor(couleur);
                g2d.fillRect(x, y, tailleCase, tailleCase);
                
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(x, y, tailleCase, tailleCase);
            }
        }
        
        if (chemin != null && !chemin.isEmpty()) {
            g2d.setColor(new Color(255, 0, 0, 200));
            g2d.setStroke(new java.awt.BasicStroke(Math.max(2, tailleCase/10)));
            
            for (int i = 0; i < chemin.size() - 1; i++) {
                Sommet s1 = graph.getSommet(chemin.get(i));
                Sommet s2 = graph.getSommet(chemin.get(i + 1));
                
                int x1 = offsetX + s1.getCoordonnee().getColonne() * tailleCase + tailleCase / 2;
                int y1 = offsetY + s1.getCoordonnee().getLigne() * tailleCase + tailleCase / 2;
                int x2 = offsetX + s2.getCoordonnee().getColonne() * tailleCase + tailleCase / 2;
                int y2 = offsetY + s2.getCoordonnee().getLigne() * tailleCase + tailleCase / 2;
                
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        
        if (depart != null) {
            int x = offsetX + depart.getColonne() * tailleCase + tailleCase / 2;
            int y = offsetY + depart.getLigne() * tailleCase + tailleCase / 2;
            
            g2d.setColor(Color.RED);
            g2d.fillOval(x - 3, y - 3, 6, 6);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new java.awt.BasicStroke(1));
            g2d.drawOval(x - 3, y - 3, 6, 6);
        }
        
        if (arrivee != null) {
            int x = offsetX + arrivee.getColonne() * tailleCase + tailleCase / 2;
            int y = offsetY + arrivee.getLigne() * tailleCase + tailleCase / 2;
            
            g2d.setColor(new Color(0, 180, 0));
            g2d.fillOval(x - 3, y - 3, 6, 6);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new java.awt.BasicStroke(1));
            g2d.drawOval(x - 3, y - 3, 6, 6);
        }
    }
    
    /**
     * Exécute l'algorithme sélectionné sur le graphe
     */
    private void executerAlgorithme() {
        if (graph == null || depart == null || arrivee == null) {
            resultArea.setText("Aucun graphe charge !");
            return;
        }
        
        int etiquetteDepart = depart.getLigne() * graph.getColonne() + depart.getColonne();
        int etiquetteArrivee = arrivee.getLigne() * graph.getColonne() + arrivee.getColonne();
        
        String algorithme = algoChoice.getSelectedItem();
        String heuristique = heuristiqueChoice.getSelectedItem();
        
        resultArea.setText("Execution de " + algorithme + 
                          (algorithme.equals("A*") ? " (" + heuristique + ")" : "") + "...");
        
        new Thread(() -> {
            long debut = System.currentTimeMillis();
            
            try {
                for (Sommet s : graph.getSommets()) {
                    s.parcouru = false;
                    s.setCoutDepuisSource(Double.POSITIVE_INFINITY);
                    s.setHeuristique(Double.MAX_VALUE);
                }
                
                Graph grapheExecution = graph.clone();
                
                if ("Dijkstra".equals(algorithme)) {
                    Djikstra dijkstra = new Djikstra(etiquetteDepart, etiquetteArrivee, grapheExecution);
                    dijkstra.launch();
                    chemin = dijkstra.getChemin();
                    afficherResultats("Dijkstra", dijkstra.getCout(), 
                                     dijkstra.getNbSommetsVisites(), debut);
                    
                } else if ("A*".equals(algorithme)) {
                    Sommet sommetArrivee = grapheExecution.getSommet(etiquetteArrivee);
                    InterfaceHeuristique h = null;
                    
                    switch (heuristique) {
                        case "Manhattan":
                            h = new HeuristiqueDeManhattan(grapheExecution, sommetArrivee);
                            break;
                        case "Euclidienne":
                            h = new HeuristiqueEuclidienne(grapheExecution, sommetArrivee);
                            break;
                        case "Octile":
                            h = new HeuristiqueOctile(grapheExecution, sommetArrivee);
                            break;
                    }
                    
                    AEtoile aStar = new AEtoile(etiquetteDepart, etiquetteArrivee, grapheExecution, h);
                    aStar.launch();
                    chemin = aStar.getChemin();
                    afficherResultats("A* (" + heuristique + ")", aStar.getCout(), 
                                     aStar.getNbSommetsVisites(), debut);
                }
                
            } catch (Exception e) {
                EventQueue.invokeLater(() -> {
                    resultArea.setText("Erreur lors de l'execution: " + e.getMessage());
                });
            }
        }).start();
    }
    
    /**
     * Affiche les résultats de l'exécution de l'algorithme
     */
    private void afficherResultats(String nomAlgo, double cout, int sommetsVisites, long debut) {
        EventQueue.invokeLater(() -> {
            long fin = System.currentTimeMillis();
            long tempsExecution = fin - debut;
            
            if (cout != Double.POSITIVE_INFINITY) {
                coutLabel.setText(String.format("%.2f", cout));
                sommetsLabel.setText(sommetsVisites + "/" + graph.getNombreSommets());
                tempsLabel.setText(tempsExecution + " ms");
                
                double tauxVisites = (sommetsVisites * 100.0) / graph.getNombreSommets();
                resultArea.setText(nomAlgo + " termine en " + tempsExecution + " ms\n" +
                                 "Chemin trouve: " + chemin.size() + " sommets\n" +
                                 "Cout total: " + String.format("%.2f", cout) + 
                                 "\nSommets visites: " + sommetsVisites + 
                                 "/" + graph.getNombreSommets() +
                                 "\nTaux: " + String.format("%.1f", tauxVisites) + "%");
            } else {
                resultArea.setText(nomAlgo + " termine en " + tempsExecution + " ms\n" +
                                 "Aucun chemin trouve entre les points.");
                coutLabel.setText("-");
                sommetsLabel.setText("-");
                tempsLabel.setText(tempsExecution + " ms");
            }
            imageCanvas.repaint();
        });
    }
    
    /**
     * Réinitialise l'interface pour une nouvelle exécution
     */
    private void reinitialiser() {
        chemin = null;
        coutLabel.setText("-");
        sommetsLabel.setText("-");
        tempsLabel.setText("-");
        
        if (graph != null) {
            resultArea.setText("Résultats réinitialisés.\n" +
                             "Graphe: " + graph.getNombreSommets() + " sommets (" + 
                             graph.getLigne() + "x" + graph.getColonne() + ")\n" +
                             "Prêt pour une nouvelle exécution.");
        }
        
        imageCanvas.repaint();
    }
}