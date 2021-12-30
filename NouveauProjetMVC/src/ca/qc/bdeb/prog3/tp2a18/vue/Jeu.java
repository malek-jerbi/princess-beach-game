/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import ca.qc.bdeb.prog3.tp2a18.controleur.Controleur;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import ca.qc.bdeb.prog3.tp2a18.modele.Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.scene.input.KeyCode;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Le jeu Slick2D
 *
  * @author User Daniel Diaz et Malek Jerbi 
 */
public class Jeu extends BasicGame implements Observer {

    private Random r = new Random();
    private Controleur controleur;
    private Modele modele;
    private CopyOnWriteArrayList<Bougeable> listeBougeable = new CopyOnWriteArrayList<>(); // Tout ce qui bouge
    private CopyOnWriteArrayList<Entite> listeEntite = new CopyOnWriteArrayList<>(); // Toutes les entités
    private ArrayList<Entite> listeTemp = new ArrayList<>();
    private ArrayList<KeyCode> listeKeys = new ArrayList<>(); // Les touches enfoncées
    private Input input; // L’entrée (souris/touches de clavier, etc.)
    private SpriteSheet spriteSheetMonde; // Image qui regroupe toutes les composantes de l'environnement 
    private SpriteSheet spriteSheetPrincesse;// Image qui regroupe toutes les composantes de l'animation de la princesse 
    private SpriteSheet spriteSheetDivers;// Image qui qui regroupe toutes les composantes des monstre/ Bonus 
    private Princesse princesse; //  Entite (objet) qui regroupe les informations visuelles propres a la princesse
    private int tailleSheetMonde = 32; //  taille de chaque sub-division dans la SheeetMonde
    private ArrayList<Coeur> listeCoeurs = new ArrayList<>(); //  Liste qui regroupe les éléments de type coeur dans le jeu 
    private long millis = System.currentTimeMillis(); // Temps actuel dans le jeu 
    private int monstreBonifie; //  Position du monstre dans la vague qui est bonifie
    private boolean BonusBallesPrincesse = false; //  Boolean qui permet de savoir si la princesse peut tirer trois balles ou pas 
    private boolean partieFinie; // Boolean qui permet de savoir si le jeu est fini ou pas 

    /**
     * Contructeur de Jeu
     *
     * @param gamename Le nom du jeu
     * @param controleur Le controleur du jeu
     * @param modele Le modèle du jeu
     */
    public Jeu(String gamename, Controleur controleur, Modele modele) {
        super(gamename);
        this.modele = modele;
        this.controleur = controleur;
        modele.addObserver(this);
        // …
    }

    /**
     * Initialiser le jeu
     *
     * @param container le container du jeu
     * @throws SlickException si le jeu plante
     */
    public void init(GameContainer container) throws SlickException {
        input = container.getInput();
        spriteSheetMonde = new SpriteSheet("images/sprites_monde.png", 32, 32);
        spriteSheetDivers = new SpriteSheet("images/sprites_divers.png", 32, 32);

        for (int i = 0; i <= LARGEUR / tailleSheetMonde; i++) {

            Paturage paturage = new Paturage(i * tailleSheetMonde, HAUTEUR - (2 * tailleSheetMonde), spriteSheetMonde);
            listeEntite.add(paturage);
            listeBougeable.add(paturage);

            Sable sable = new Sable(i * tailleSheetMonde, HAUTEUR - (tailleSheetMonde), spriteSheetMonde);
            listeEntite.add(sable);
            listeBougeable.add(sable);

            for (int j = 0; j < HAUTEUR - 2; j++) {
                Ciel ciel = new Ciel(i * tailleSheetMonde, HAUTEUR - (3 * tailleSheetMonde) - (j * tailleSheetMonde), spriteSheetMonde);
                listeEntite.add(ciel);
            }
        }

        spriteSheetPrincesse = new SpriteSheet("images/sprites_princess.png", 32, 64);
        princesse = new Princesse(0, HAUTEUR - (4 * tailleSheetMonde), spriteSheetPrincesse);
        listeEntite.add(princesse);
        for (int i = 0; i < 10; i++) {
            Coeur coeur = new Coeur(10 + i * 30, 40);
            listeEntite.add(coeur);
            listeCoeurs.add(coeur);
        }

    }

    /**
     * Update du jeu
     *
     * @param container le container du jeu
     * @param delta N/A
     * @throws SlickException Si le update plante
     */
    private long tempsVague = 0;
    private long tempsVague2 = 0;

    /**
     *
     * @param container le container du jeu
     * @param delta N/A
     * @throws SlickException Si le update plante
     */
    public void update(GameContainer container, int delta) throws SlickException {
        if (!partieFinie) {
            millis = System.currentTimeMillis();
            tempsVague2 = 3000;
            getKeys();
            traiterKeys();
            listeTemp = new ArrayList<>();
            creationDesArbres();
            creationPlantes();
            if (millis >= tempsVague) {
                int choix = r.nextInt(4);
                switch (choix) {
                    case 0:
                        vagueOiseauxJaune();
                        tempsVague = millis + tempsVague2;
                        break;
                    case 1:
                        vagueOiseauxRouge();
                        tempsVague = millis + tempsVague2;
                        break;
                    case 2:
                        vaguePhantomeNoir();
                        tempsVague = millis + tempsVague2;
                        break;
                    case 3:
                        vaguePhantomeBlanc();
                        tempsVague = millis + tempsVague2;
                        break;
                }
            }

            if (BonusBallesPrincesse) {
                traiterBoulesMultiples();
            }

            for (int i = 0; i < listeBougeable.size(); i++) {
                listeBougeable.get(i).bouger();
            }

            for (int i = 0; i < listeEntite.size(); i++) {
                if (listeEntite.get(i).getDetruire()) {
                    listeTemp.add(listeEntite.get(i));
                }
            }
            listeBougeable.removeAll(listeTemp); //Supprimer les éléments marqués
            listeEntite.removeAll(listeTemp);
            listeTemp.clear();

            gererCollisions();
        } else {
            millis = System.currentTimeMillis();
            tempsVague2 = 3000;
            listeTemp = new ArrayList<>();
            creationDesArbres();
            creationPlantes();
            if (millis >= tempsVague) {
                int choix = r.nextInt(4);
                switch (choix) {
                    case 0:
                        vagueOiseauxJaune();
                        tempsVague = millis + tempsVague2;
                        break;
                    case 1:
                        vagueOiseauxRouge();
                        tempsVague = millis + tempsVague2;
                        break;
                    case 2:
                        vaguePhantomeNoir();
                        tempsVague = millis + tempsVague2;
                        break;
                    case 3:
                        vaguePhantomeBlanc();
                        tempsVague = millis + tempsVague2;
                        break;
                }
            }
            for (int i = 0; i < listeEntite.size(); i++) {
                if (listeEntite.get(i).getDetruire()) {
                    listeTemp.add(listeEntite.get(i));
                }
            }
            listeBougeable.removeAll(listeTemp); //Supprimer les éléments marqués
            listeEntite.removeAll(listeTemp);
            listeTemp.clear();
            gererCollisions();
        }
    }

    /**
     * Dessiner le jeu
     *
     * @param container le container du jeu
     * @param g le graphics du container
     * @throws SlickException Si le render plante
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

        if (modele.getPtsDeVie() <= 0) {

            millis = System.currentTimeMillis();
            if (millis > dureeGameOverPlantage) {
                if (millis < dureeGameOver) {
                    listeEntite.clear();
                    listeBougeable.clear();
                    for (Entite entite : listeEntite) {
                        g.drawImage(entite.getImage(), entite.getX(), entite.getY());
                    }
                    g.drawString("GAME OVER !", 360, 300); // Texte et sa position
                } else {
                    controleur.reSetPtsDeVie();
                    controleur.setPointsJoueur(0);
                    partieFinie = false;
                    recommencer(container);
                }
            }
        }

        for (Entite entite : listeEntite) {
            g.drawImage(entite.getImage(), entite.getX(), entite.getY());
        }
        g.drawString("Beach Wonderland !", 10, 10); // Texte et sa position
        g.drawString("Points joueur : " + modele.getPointsJoueur(), 10, 80);
    }

    /**
     * Update du patron observateur (MVC)
     *
     * @param o N/A
     * @param arg N/A
     */
    @Override
    public void update(Observable o, Object arg) {

        if (modele.isGainPoints() && listeCoeurs.size() < 10) {

            Coeur coeur = new Coeur(10 + listeCoeurs.size() * 30, 40);
            listeEntite.add(coeur);
            listeCoeurs.add(coeur);
        } else if (modele.getPtsDeVie() != 10) {
            try {listeTemp.add(listeCoeurs.get(listeCoeurs.size() - 1));
            listeCoeurs.remove(listeCoeurs.size() - 1);}
            catch (ArrayIndexOutOfBoundsException e ) {
                
            }
        }

    }
    private long dureeGameOver = 0;
    private long dureeGameOverPlantage = 0;
/*
    *
    *Methode qui gere les collisions dans le jeu 
    *
    *
    */
    private void gererCollisions() {

        for (Bougeable b1 : listeBougeable) {

            if (b1 instanceof Colisionnable) {

                if (b1.getRectangle().intersects(princesse.getRectangle()) && !(b1 instanceof BoulePrincesse)) {

                    listeTemp.add((Entite) b1);

                    if (b1 instanceof Bonus) {
                        gererBonus(b1);
                        controleur.augmenterPointsJoueur(25);

                    } else {
                        controleur.diminuerPtsDeVie(1);
                        if (modele.getPtsDeVie() <= 0) {
                            partieFinie = true;
                            dureeGameOver = millis + 6000;
                            dureeGameOverPlantage = millis + 2000;
                        }
                    }
                }
                if (b1 instanceof BoulePrincesse) {
                    for (Bougeable e1 : listeBougeable) {
                        if (e1 instanceof Ennemi) {
                            if (b1.getRectangle().intersects(e1.getRectangle())) {
                                listeTemp.add((Entite) e1);
                                listeTemp.add((Entite) b1);
                                gererMortEnnemi(e1);
                            }
                        }
                    }

                }
            }

        }

        listeBougeable.removeAll(listeTemp); //Supprimer les éléments marqués
        listeEntite.removeAll(listeTemp); //Supprimer les éléments marqués
    }
/*
    *
    *
    * methode qui ajoute les commandes cliquees du clavier 
    *
    */
    private void getKeys() {

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            if (!listeKeys.contains(KeyCode.RIGHT)) {
                listeKeys.add(KeyCode.RIGHT);
            }
        } else {
            listeKeys.remove(KeyCode.RIGHT);
        }

        if (input.isKeyDown(Input.KEY_LEFT)) {
            if (!listeKeys.contains(KeyCode.LEFT)) {
                listeKeys.add(KeyCode.LEFT);
            }
        } else {
            listeKeys.remove(KeyCode.LEFT);
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            if (!listeKeys.contains(KeyCode.UP)) {
                listeKeys.add(KeyCode.UP);
            }
        } else {
            listeKeys.remove(KeyCode.UP);
        }
        if (input.isKeyDown(Input.KEY_SPACE)) {
            if (!listeKeys.contains(KeyCode.SPACE)) {
                listeKeys.add(KeyCode.SPACE);
            }
        } else {
            listeKeys.remove(KeyCode.SPACE);
        }

        if (input.isKeyDown(Input.KEY_DOWN)) {
            if (!listeKeys.contains(KeyCode.DOWN)) {
                listeKeys.add(KeyCode.DOWN);
            }
        } else {
            listeKeys.remove(KeyCode.DOWN);
        }
    }
    private long tempsBoulet = 0;
/*
    *
    *
    * Methode qui traiter les commandes cliquees du clavier 
    *
    */
    private void traiterKeys() {

        millis = System.currentTimeMillis();
        if (listeKeys.contains(KeyCode.SPACE)) {
            if (millis >= tempsBoulet) {
                BoulePrincesse bouleP = new BoulePrincesse(princesse.getX(), princesse.getY() + 30);
                listeEntite.add(bouleP);
                listeBougeable.add(bouleP);
                tempsBoulet = millis + 500;
            }

        }
        princesse.bouger(listeKeys);
    }

    private long temps = 0;
    private long temps2 = 0;
/*
    *
    *
    * Methode qui creer les arbres dans l'ecran 
    *
    */
    private void creationDesArbres() {
        temps2 = r.nextInt(6000) + 1000;
        millis = System.currentTimeMillis();

        int b = r.nextInt(12) + 5;

        if (millis >= temps) {
            ArbreFeuilles feuilles = new ArbreFeuilles(LARGEUR + 64, HAUTEUR - (b * tailleSheetMonde), spriteSheetMonde);
            listeEntite.add(feuilles);
            listeBougeable.add(feuilles);

            for (int k = 3; k < b; k++) {
                TroncArbre tronc = new TroncArbre(LARGEUR + 64, HAUTEUR - k * tailleSheetMonde, spriteSheetMonde);
                listeEntite.add(tronc);
                listeBougeable.add(tronc);
            }
            temps = millis + temps2;
        }

    }

    private long tempsPlante = 0;
    private long temps2Plante = 0;
/*
    *
    *
    * Methode qui permet de creer des plantes ennemies 
    *
    */
    private void creationPlantes() {
        temps2Plante = r.nextInt(6000) + 1000;
        millis = System.currentTimeMillis();

        if (millis >= tempsPlante) {
            Plante plante = new Plante(LARGEUR + 50, HAUTEUR - (3 * tailleSheetMonde), spriteSheetDivers, listeBougeable, listeEntite);
            listeEntite.add(plante);
            listeBougeable.add(plante);

            tempsPlante = millis + temps2Plante;
        }
    }


/*
    *
    *
    *
    * Methode qui permet de creer une vague d'oiseaux jaunes ennemis 
    */
    private void vagueOiseauxJaune() {
        monstreBonifie = r.nextInt(7);

        for (int i = 0; i < 7; i++) {
            OiseauJaune oiseauJaune = new OiseauJaune(LARGEUR + i * 32 + 64, 200, spriteSheetDivers);
            if (i == monstreBonifie) {
                oiseauJaune.setBonifiable(true);
            }
            listeEntite.add(oiseauJaune);
            listeBougeable.add(oiseauJaune);
        }
    }
/*
    *
    *
    *Methode qui permet de creer une vague d'oiseaux rouges ennemis 
    *
    */
    private void vagueOiseauxRouge() {
        monstreBonifie = r.nextInt(7);

        for (int i = 0; i < 7; i++) {
            OiseauRouge oiseauRouge = new OiseauRouge(LARGEUR + i * 32, HAUTEUR - 32 * 4 - i * 32, spriteSheetDivers);
            if (i == monstreBonifie) {
                oiseauRouge.setBonifiable(true);
            }
            listeEntite.add(oiseauRouge);
            listeBougeable.add(oiseauRouge);
        }
    }
/*
    *
    *
    *Methode qui permet de creer une vague de phantomes noirs ennemis 
    *
    */
    private void vaguePhantomeNoir() {
        boolean versHaut;
        monstreBonifie = r.nextInt(7);

        for (int i = 0; i < 7; i++) {
            PhantomeNoir phantomeNoir = new PhantomeNoir(LARGEUR + i * 32, HAUTEUR / 2 - i * 32 + 32, spriteSheetDivers);
            if (i % 2 == 0) {
                versHaut = false;
                phantomeNoir.setVersHaut(versHaut);
            }
            if (i == monstreBonifie) {
                phantomeNoir.setBonifiable(true);
            }
            listeEntite.add(phantomeNoir);
            listeBougeable.add(phantomeNoir);
        }
    }
/*
    *
    *
    *Methode qui permet de creer une vague phantomes noirs ennemis 
    *
    */
    private void vaguePhantomeBlanc() {
        monstreBonifie = r.nextInt(7);
        for (int i = 0; i < 7; i++) {
            PhantomeBlanc phantomeBlanc = new PhantomeBlanc(LARGEUR, 200 + 2*i * 32-64, spriteSheetDivers);
            if (i % 2 == 0) {
                phantomeBlanc.setDeltaX(8);
            }
            if (i == monstreBonifie) {
                phantomeBlanc.setBonifiable(true);
            }
            listeEntite.add(phantomeBlanc);
            listeBougeable.add(phantomeBlanc);
        }
    }
/*
    *
    *
    * Methode qui permet de gerer les consequences de la mort dun ennemi (soit gagner exclusivement de points ou gagnes un bonus) 
    *
    */
    private void gererMortEnnemi(Bougeable e1) {
        Ennemi e2 = (Ennemi) e1;
         controleur.augmenterPointsJoueur(5);
        if (e2.isBonifiable()) {
            int choixBonus = r.nextInt(3);
            switch (choixBonus) {
                case 0:
                    BonusEtoileteDore bonusEtoileteDore = new BonusEtoileteDore(e2.getX(), e2.getY(), spriteSheetDivers);
                    listeEntite.add(bonusEtoileteDore);
                    listeBougeable.add(bonusEtoileteDore);

                    break;
                case 1:
                    BonusPotion bonusPotion = new BonusPotion(e2.getX(), e2.getY(), spriteSheetDivers);
                    listeEntite.add(bonusPotion);
                    listeBougeable.add(bonusPotion);
 
                    break;
                case 2:
                    BubleBonus bubleBonus = new BubleBonus(e2.getX(), e2.getY(), spriteSheetDivers);
                    listeEntite.add(bubleBonus);
                    listeBougeable.add(bubleBonus);

                    break;
            }
        }
            


    }
    private long dureeBonusBalles = 0;
/*
    *
    *
    * Methode qui permet de gerer les prix associes au cliquage d'un bonus 
    *
    */
    private void gererBonus(Bougeable b1) {

        millis = System.currentTimeMillis();
        Bonus b2 = (Bonus) b1;
        if (b2 instanceof BonusEtoileteDore) {
            
            controleur.AugmenterPtsDeVie(1);
            
        } else if (b2 instanceof BonusPotion) {
            
            dureeBonusBalles = millis + 10000;
            BonusBallesPrincesse = true;
        } else {
            
            for (Entite a : listeEntite) {
                if (a instanceof Ennemi) {
                    a.setDetruire(true);
                    controleur.augmenterPointsJoueur(5);
                }
            }
        }
    }
    private long tempsBonusBallesMultiples = 0;
    private long tempsBonusBallesMultiples2 = 0;
/*
    *
    *
    * Methode qui permet de traiter le bonus de la potion, soit les balles multiples pendant 10 secondes 
    *
    */
    private void traiterBoulesMultiples() {
        millis = System.currentTimeMillis();
        tempsBonusBallesMultiples2 = 500;

        if (millis < dureeBonusBalles) {
            if (listeKeys.contains(KeyCode.SPACE)) {
                if (millis >= tempsBonusBallesMultiples) {

                    BoulePrincesse bouleDiagonaleHaut = new BoulePrincesse(princesse.getX(), princesse.getY() + 30);
                    bouleDiagonaleHaut.setDeplacementDiagonalHaut(true);
                    listeEntite.add(bouleDiagonaleHaut);
                    listeBougeable.add(bouleDiagonaleHaut);

                    BoulePrincesse bouleDiagonaleBas = new BoulePrincesse(princesse.getX(), princesse.getY() + 30);
                    bouleDiagonaleBas.setDeplacementDiagonalBas(true);
                    listeEntite.add(bouleDiagonaleBas);
                    listeBougeable.add(bouleDiagonaleBas);

                    tempsBonusBallesMultiples = millis + tempsBonusBallesMultiples2;
                }
            }
        }

    }
/*
    *
    * Methode qui permet de reinitialiser l'environnement initial du jeu 
    *
    *
    */
    private void recommencer(GameContainer container) throws SlickException {

        input = container.getInput();
        spriteSheetMonde = new SpriteSheet("images/sprites_monde.png", 32, 32);
        
        spriteSheetDivers = new SpriteSheet("images/sprites_divers.png", 32, 32);

        for (int i = 0; i <= LARGEUR / tailleSheetMonde; i++) {

            Paturage paturage = new Paturage(i * tailleSheetMonde, HAUTEUR - (2 * tailleSheetMonde), spriteSheetMonde);
            listeEntite.add(paturage);
            listeBougeable.add(paturage);

            Sable sable = new Sable(i * tailleSheetMonde, HAUTEUR - (tailleSheetMonde), spriteSheetMonde);
            listeEntite.add(sable);
            listeBougeable.add(sable);

            for (int j = 0; j < HAUTEUR - 2; j++) {
                Ciel ciel = new Ciel(i * tailleSheetMonde, HAUTEUR - (3 * tailleSheetMonde) - (j * tailleSheetMonde), spriteSheetMonde);
                listeEntite.add(ciel);
            }
        }

        spriteSheetPrincesse = new SpriteSheet("images/sprites_princess.png", 32, 64);
        princesse = new Princesse(0, HAUTEUR - (4 * tailleSheetMonde), spriteSheetPrincesse);
        listeEntite.add(princesse);
        for (int i = 0; i < 10; i++) {
            Coeur coeur = new Coeur(10 + i * 30, 40);
            listeEntite.add(coeur);
            listeCoeurs.add(coeur);
        }
    }

    
  
}
