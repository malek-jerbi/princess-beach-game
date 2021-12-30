/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.controleur;

import ca.qc.bdeb.prog3.tp2a18.modele.Modele;
import ca.qc.bdeb.prog3.tp2a18.vue.Jeu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author MGrenon
 */
public class Controleur {

    private Modele modele = new Modele();

    /**
     * Hauteur de la fenetre du jeu 
     */
    public static final int HAUTEUR = 640,

    /**
     *  Largeur de la fenetre du jeu 
     */
    LARGEUR = 800;

    /**
     * Classe qui permet de gérer les donnees du jeu avant d'acceder et de modifier le modele 
     */

    public Controleur() {

        try {
            AppGameContainer app;
            app = new AppGameContainer(new Jeu("BEACH", this, modele));
            app.setDisplayMode(LARGEUR, HAUTEUR, false);
            app.setShowFPS(false);
            app.setVSync(false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pts le nombre de points de vie a diminuer 
     */
    public void diminuerPtsDeVie(int pts) {
        modele.diminuerPtsDeVie(pts);
    }

    /**
     *
     */
    public void reSetPtsDeVie() {
        modele.reSetPtsDeVie();
    }

    /**
     *
     * @param pts points de vie a augmenter 
     */
    public void AugmenterPtsDeVie(int pts) {
        modele.AugmenterPtsDeVie(pts); 
    }

    /**
     *
     * @param gainPoints une valeur booleenne qui permet de savoir si on peut gagner de points ou pas 
     */
    public void setGainPoints(boolean gainPoints) {
        modele.setGainPoints(gainPoints);
    }

    /**
     *
     * @param pointsJoueur points actuels du joueur (associes au nombre d'ennemis tues ou de bonus atttapes) 
     */
    public void setPointsJoueur(int pointsJoueur) {
        modele.setPointsJoueur(pointsJoueur);
    }

    /**
     *
     * @param pts pointage a augmenter associes au nombre d'ennemis tues ou de bonus atttapes 
     */
    public void augmenterPointsJoueur(int pts) {
        modele.augmenterPointsJoueur(pts);
    }

}
