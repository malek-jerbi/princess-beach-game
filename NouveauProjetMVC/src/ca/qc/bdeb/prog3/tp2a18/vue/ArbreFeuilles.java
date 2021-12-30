/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import org.newdawn.slick.SpriteSheet;

/**
 *
  * @author User Daniel Diaz et Malek Jerbi 
 */
public class ArbreFeuilles extends Entite implements Bougeable {
private int deltaX=2;
    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     */
    public ArbreFeuilles(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 1, 5);
    }

    /**
     * méthode définissant le déplacement de l'objet dans l'écran 
     */
    @Override
    public void bouger() {
        x = x - deltaX;
        if (x == -32) { //quand ca depasse l'ecran
            detruire =true; 
        }
    }

}
