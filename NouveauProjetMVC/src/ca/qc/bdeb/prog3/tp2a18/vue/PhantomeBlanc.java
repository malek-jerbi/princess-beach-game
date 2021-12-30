/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author User Daniel Diaz et Malek Jerbi 
 */
public class PhantomeBlanc extends Phantome implements Bougeable, Colisionnable {

    private int deltaX = 5;


    boolean bonifiable;



    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     */
    public PhantomeBlanc(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 3, 4);
    }

    /**
     * méthode définissant le déplacement de l'objet dans l'écran 
     */
    @Override
    public void bouger() {

         x = x - deltaX;       
       if (x == -32) {
            detruire = true;
        }
    }
    /**
     *
     * @param deltaX nouvelle valeur a donner au depalcemement vers la droite de l'objet 
     */
    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }
        /**
     *
     * @param bonifiable valeur booleeene qui permet de savoir si le l'objet est bonifiable 
     */
    public void setBonifiable(boolean bonifiable) {
        this.bonifiable = bonifiable;
    }

    /**
     *
     * @return valeur booleeene qui permet de savoir si le l'objet est bonifiable 
     */
    public boolean isBonifiable() {
        return bonifiable;
    }

}
