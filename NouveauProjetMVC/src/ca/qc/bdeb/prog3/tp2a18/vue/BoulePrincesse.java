/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author User Daniel Diaz et Malek Jerbi
 */
public class BoulePrincesse extends Entite implements Bougeable, Colisionnable {

    private boolean deplacementDiagonalHaut = false;
    private boolean deplacementDiagonalBas = false;
    private int deltaX=15;
    private int deltaY=3;
    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran
     */
    public BoulePrincesse(float x, float y) {
        super(x, y, 16, 16, "images/boulet.png");
    }

    /**
     * méthode définissant le déplacement de l'objet dans l'écran
     */
    @Override
    public void bouger() {

        if (deplacementDiagonalHaut) {
            y = y - deltaY;
        } else if (deplacementDiagonalBas) {
            y = y + deltaY;
        }
        x = x + deltaX;
        if (x >= LARGEUR + 15) {
            detruire = true;
        }
        if (y >= HAUTEUR - 3 * 32) {
            detruire = true;
        }
    }

    /**
     *
     * @param deplacementDiagonalHaut valeur booleenne qui permet de deplacer le
     * boule selon un deplacement diagonal vers le haut
     */
    public void setDeplacementDiagonalHaut(boolean deplacementDiagonalHaut) {
        this.deplacementDiagonalHaut = deplacementDiagonalHaut;
    }

    /**
     *
     * valeur booleenne qui permet de deplacer le boule selon un deplacement
     * diagonal vers le haut
     */
    public void setDeplacementDiagonalBas(boolean deplacementDiagonalBas) {
        this.deplacementDiagonalBas = deplacementDiagonalBas;
    }
}
