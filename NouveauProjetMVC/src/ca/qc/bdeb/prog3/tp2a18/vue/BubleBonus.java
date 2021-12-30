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
public class BubleBonus extends Bonus implements Bougeable, Colisionnable {

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
    public BubleBonus(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 0, 0);
        listeAnimation.add(spriteSheet.getSubImage(0, 0));
        listeAnimation.add(spriteSheet.getSubImage(1, 0));
        listeAnimation.add(spriteSheet.getSubImage(2, 0));
        listeAnimation.add(spriteSheet.getSubImage(3, 0));
        listeAnimation.add(spriteSheet.getSubImage(4, 0));
        listeAnimation.add(spriteSheet.getSubImage(5, 0));
        listeAnimation.add(spriteSheet.getSubImage(6, 0));
        listeAnimation.add(spriteSheet.getSubImage(7, 0));
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
                break;
            case 15:
                this.image = listeAnimation.get(3);
                break; 
            case 20 : 
                  this.image = listeAnimation.get(4);
                  break; 
            case 25 : 
                  this.image = listeAnimation.get(5);
                   animation = -1;
                  break;                
        }
        animation++;
        x = x - deltaX;
        if (y <= HAUTEUR - 3 * 32) { //en haut du sol
            y = y + deltaY;
        }
    }

}
