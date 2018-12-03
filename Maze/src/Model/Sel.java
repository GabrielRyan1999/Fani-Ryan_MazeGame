/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Ryan
 */
public class Sel implements Serializable {

    private char nilai;
    private int posisiX;
    private int posisiY;
    private int baris;
    private int kolom;
    private int lebar;
    private int tinggi;

    public Sel() {
    }

    public Sel(char nilai) {
        this.nilai = nilai;
    }

    public Sel(int posisiX, int posisiY) {
        this.posisiX = posisiX;
        this.posisiY = posisiY;
    }

    public Sel(char nilai, int posisiX, int posisiY) {
        this.nilai = nilai;
        this.posisiX = posisiX;
        this.posisiY = posisiY;
    }

    public char getNilai() {
        return nilai;
    }

    public void setNilai(char nilai) {
        this.nilai = nilai;
    }

    public int getPosisiX() {
        return posisiX;
    }

    public void setPosisiX(int posisiX) {
        this.posisiX = posisiX;
    }

    public int getPosisiY() {
        return posisiY;
    }

    public void setPosisiY(int posisiY) {
        this.posisiY = posisiY;
    }

    public boolean isPemain() {
        return nilai == '@';
    }

    public boolean isHambatan() {
        return nilai == '#';
    }

    public boolean isBatasKanan() {
        if (kolom * lebar + lebar < Tempat.batasKanan) {
            return false;
        } else {
            return true;
        }
    }

    public void geserKanan() {
        if (isBatasKanan() == false) {
            kolom++;
        }
    }

    public boolean isBatasAtas() {
        return false;
    }

    public boolean isBatasBawah() {
        return false;
    }

    public int getBaris() {
        return baris;
    }

    public void setBaris(int baris) {
        this.baris = baris;
    }

    public int getKolom() {
        return kolom;
    }

    public void setKolom(int kolom) {
        this.kolom = kolom;
    }

    public int getLebar() {
        return lebar;
    }

    public void setLebar(int lebar) {
        this.lebar = lebar;
    }

    public int getTinggi() {
        return tinggi;
    }

    public void setTinggi(int tinggi) {
        this.tinggi = tinggi;
    }
    
    
}
