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
public class OiseauRouge  extends Oiseau implements Bougeable, Colisionnable  {
    private int animation = 0;
    private ArrayList<Image> listeAnimation = new ArrayList<>();
    private int deltaX = 2;
    private int deltaY = 8;
    boolean bonifiable; 
    
    /**
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     */
    public OiseauRouge(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 2, 2);
        listeAnimation.add(spriteSheet.getSubImage(2, 2));
        listeAnimation.add(spriteSheet.getSubImage(3, 2));
    }

    /**
     * méthode définissant le déplacement de l'objet dans l'écran 
     */
    @Override
    public void bouger() {
        switch (animation) {
            case 0:
                this.image = listeAnimation.get(0);
                break;
            case 5:
                this.image = listeAnimation.get(1);
                animation = -1;
                break;
        }
        animation++;

        if (y >= HAUTEUR - 3* 32 ) {
            deltaY = -deltaY;
        }
        if (y <= 0) {
            deltaY = -deltaY;
        }

        x = x - deltaX;
        y = y + deltaY;
        
        if (x == -32) {
            detruire = true;
        }
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
