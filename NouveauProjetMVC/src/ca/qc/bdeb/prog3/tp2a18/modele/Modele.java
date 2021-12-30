/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.modele;

import java.util.Observable;

/**
 *
 * @author MGrenon
 */
public class Modele extends Observable {

    private int ptsDeVie = 10;
    private int ptsDeVieMaximal = 10;
    private int pointsJoueur = 0; 

    private boolean gainPoints = false;

    /**
     * Classe qui permet de gérer les données du jeu 
     */
    public Modele() {
        majObserver();
    }

    /**
     *  Méthode qui met a jour les donnees du jeu 
     */
    public void majObserver() {
        setChanged();
        notifyObservers();
    }

    /**
     *
     * @return points de vie
     */
    public int getPtsDeVie() {
        return ptsDeVie;
    }

    /**
     *
     * @param pts points de vie voulant être enleves 
     */
    public void diminuerPtsDeVie(int pts) {
        if (ptsDeVie > 0) {
            ptsDeVie = ptsDeVie - pts;
            gainPoints = false;
        }
        majObserver();
    }

    /**
     *
     * @param ptsDeVie points de vie actuels dans le jeu 
     */
    public void reSetPtsDeVie() {
        this.ptsDeVie = ptsDeVieMaximal;
    }

    /**
     *
     * @param pts points de vie a augmenter 
     */
    public void AugmenterPtsDeVie(int pts) {
        if (ptsDeVie < ptsDeVieMaximal) {
            ptsDeVie = ptsDeVie + pts;
            gainPoints = true;
        }
        majObserver();
    }

    /**
     *
     * @return une valeur booleenne qui permet de savoir si on peut gagner de points ou pas 
     */
    public boolean isGainPoints() {
        return gainPoints;
    }

    /**
     *
     * @param gainPoints une valeur booleenne qui permet de savoir si on peut gagner de points ou pas 
     */
    public void setGainPoints(boolean gainPoints) {
        this.gainPoints = gainPoints;
    }



    /**
     *
     * @param pointsJoueur  points du joueur associes au nombre de montres eliminies ou de bonus obtenus  
     */
    public void setPointsJoueur(int pointsJoueur) {
        this.pointsJoueur = pointsJoueur;
    }

    /**
     *
     * @return le pointage du joueur (associe au nombre de montres eliminies ou de bonus obtenus ) 
     */
    public int getPointsJoueur() {
        return pointsJoueur;
    }

    /**
     *
     * @param pts points de vie a augmenter 
     */
    public void augmenterPointsJoueur(int pts){
         pointsJoueur = pts +pointsJoueur ; 
    }

}
