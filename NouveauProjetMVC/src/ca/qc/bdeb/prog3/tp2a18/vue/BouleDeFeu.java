/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
  * @author User Daniel Diaz et Malek Jerbi 
 */
public class BouleDeFeu extends Entite implements Bougeable, Colisionnable {
private int animation = 0;
private ArrayList<Image> listeAnimation = new ArrayList<>();
private int deltaX=2;
private int deltaY=2;
    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     */
    public BouleDeFeu(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 5, 3);
        listeAnimation.add(spriteSheet.getSubImage(3, 5));

        listeAnimation.add(spriteSheet.getSubImage(4, 5));

        listeAnimation.add(spriteSheet.getSubImage(5, 5));
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
                    break;
                case 10:
                    this.image = listeAnimation.get(2);                    
                    animation = -1; 
                    break;
            }
    x=x-deltaX;    
    y=y-deltaY;
    if (y<=-20) { //quand ca sort de l'ecran
        detruire=true;
    }
    animation++;
    }

    
}
