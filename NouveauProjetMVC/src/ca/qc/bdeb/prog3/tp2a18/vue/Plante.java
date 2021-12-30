/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author User Daniel Diaz et Malek Jerbi 
 */
public class Plante extends Ennemi implements Bougeable, Colisionnable {

    private CopyOnWriteArrayList<Bougeable> listeBougeable;
    private CopyOnWriteArrayList<Entite> listeEntite;
    private int animation = 0;
    private ArrayList<Image> listeAnimation = new ArrayList<>();
    private SpriteSheet spriteSheetDivers;
    private int deltaX=2;

    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     * @param listeBougeable liste qui regroupe tous les objets qui se deplacent dans l'ecran 
     * @param listeEntite liste d'objets a afficher dans l'ecran 
     */
    public Plante(float x, float y, SpriteSheet spriteSheet, CopyOnWriteArrayList<Bougeable> listeBougeable, CopyOnWriteArrayList<Entite> listeEntite) {

        super(x, y, spriteSheet, 3, 0);
        listeAnimation.add(spriteSheet.getSubImage(0, 3));

        listeAnimation.add(spriteSheet.getSubImage(1, 3));

        listeAnimation.add(spriteSheet.getSubImage(2, 3));
        spriteSheetDivers = spriteSheet;
        this.listeBougeable = listeBougeable;
        this.listeEntite = listeEntite;
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
            case 60:
                this.image = listeAnimation.get(1);
                break;
            case 120:
                this.image = listeAnimation.get(2);
                BouleDeFeu boule = new BouleDeFeu(x, y, spriteSheetDivers);
                listeEntite.add(boule);
                listeBougeable.add(boule);
                animation = -1;
                break;
        }
        x = x - deltaX;
        if (x == -32) {
            detruire = true;
        }
        animation++;
    }

}
