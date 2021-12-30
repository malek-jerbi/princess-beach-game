/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author User Daniel Diaz et Malek Jerbi 
 */
public class PhantomeNoir extends Phantome implements Bougeable, Colisionnable {

    private int deltaX = 5;
    private int deltaY = 6;
    private Boolean versHaut = true;
    boolean bonifiable; 

    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     */
    public PhantomeNoir(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 3, 3);

    }

    /**
     * méthode définissant le déplacement de l'objet dans l'écran 
     */
    @Override
    public void bouger() {

        if (versHaut) {
            y = y - deltaY;
        } else {
            y = y + deltaY;
        }
        if (y >= HAUTEUR - 3 * 32 ) {
            deltaY = -deltaY;
        }
        if (y <= 0) {
            deltaY = -deltaY;
        }

        x = x - deltaX;

        if (x == -32) {
            detruire = true;
        }

    }

    /**
     *
     * @param versHaut parametre qui permet de modifier le sens initial (haut ou bas) du fantome 
     */
    public void setVersHaut(Boolean versHaut) {
        this.versHaut = versHaut;
    }
        /**
     *
     * @return valeur booleeene qui permet de savoir si le l'objet est bonifiable 
     */
    public boolean isBonifiable() {
        return bonifiable;
    }

    /**
     *
     * @param bonifiable valeur booleeene qui permet de savoir si le l'objet est bonifiable 
     */
    public void setBonifiable(boolean bonifiable) {
        this.bonifiable = bonifiable;
    }
}
