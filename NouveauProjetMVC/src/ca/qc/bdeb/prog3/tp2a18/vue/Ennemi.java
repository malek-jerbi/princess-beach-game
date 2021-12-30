/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import org.newdawn.slick.SpriteSheet;

/**
 *
  * @author User Daniel Diaz et Malek Jerbi 
 */
public abstract class Ennemi extends Entite {
    
    boolean bonifiable; 
    
    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     * @param ligne position verticale de l'image dans le SpriteSheet 
     * @param colonne position horizontale de l'image dans le SpriteSheet 
     */
    public Ennemi(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
    }

    /**
     *
     * @return valeur booleene qui permet d'identifie si l'objet est bonifiable 
     */
    public boolean isBonifiable() {
        return bonifiable;
    }
    
}
