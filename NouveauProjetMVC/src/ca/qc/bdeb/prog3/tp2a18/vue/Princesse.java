/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author User Daniel Diaz et Malek Jerbi 
 */
public class Princesse extends Entite {

    private int deltaX = 4;
    private int deltaY = 4;
    private int animation = 0;
    private ArrayList<Image> listeAnimation = new ArrayList<>();
    private boolean estEnVol = false;
    
    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     */
    public Princesse(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 0, 0);
        listeAnimation.add(spriteSheet.getSubImage(0, 0));

        listeAnimation.add(spriteSheet.getSubImage(1, 0));

        listeAnimation.add(spriteSheet.getSubImage(2, 0));
        listeAnimation.add(spriteSheet.getSubImage(3, 0));

        listeAnimation.add(spriteSheet.getSubImage(4, 0));

        listeAnimation.add(spriteSheet.getSubImage(5, 0));

    }

    /**
     * méthode définissant le déplacement de l'objet dans l'écran 
     * @param listeKeys liste qui cotient les touches cliquees par l'utilsateur 
     */
    public void bouger(ArrayList<KeyCode> listeKeys) {
        if (y==HAUTEUR-32*4) {
            estEnVol=false;
        } 
        if (estEnVol) {
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
        } else {
            switch (animation) {
                case 0:
                    this.image = listeAnimation.get(3);
                    break;
                case 5:
                    this.image = listeAnimation.get(4);
                    break;
                case 10:
                    this.image = listeAnimation.get(5);
                    animation = -1; 
                    break;
            }
        }
        animation++;

        if (listeKeys.contains(KeyCode.RIGHT)) {
            if (x + deltaX >= 0 && x + deltaX <= LARGEUR - 32) {
                x = x + deltaX;
            }
        }
        if (listeKeys.contains(KeyCode.LEFT)) {
            if (x - deltaX >= 0 && x - deltaX <= LARGEUR - 32) {
                x = x - deltaX;
            }
        }
        if (listeKeys.contains(KeyCode.UP)) {
            if (y - deltaY >= 0 && y - deltaY <= HAUTEUR - 32 - 96) {
                y = y - (deltaY+4);
            }
            estEnVol = true;
            
        }

        if (listeKeys.contains(KeyCode.DOWN)) {
            if (y + deltaY >= 0 && y + deltaY <= HAUTEUR - 32 - 96) {
                y = y + deltaY;
            }
           
        }
        
        
     if ( estEnVol && y + deltaY >= 0 && y + deltaY <= HAUTEUR - 32 - 96 )  {
     y = y + deltaY;
     }      
        
    }
}
