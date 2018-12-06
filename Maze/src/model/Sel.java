/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Tempat;
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
    private Color warna;

    public Sel() {
    }

    public Sel(char nilai, int baris, int kolom, Color warna) {
        this.nilai = nilai;
        this.baris = baris;
        this.kolom = kolom;
        this.warna = warna;
    }

    public Sel(char nilai, int baris, int lebar) {
        this.nilai = nilai;
        this.baris = baris;
        this.lebar = lebar;
    }

    public Sel(int baris, int kolom, int lebar, int tinggi, char nilai, Color warna) {

        this.baris = baris;
        this.kolom = kolom;
        this.lebar = lebar;
        this.tinggi = tinggi;
        this.nilai = nilai;
        this.warna = warna;
    }

    public Color getWarna() {
        return warna;
    }

    public void setWarna(Color warna) {
        this.warna = warna;
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

    public void geserKanan(int x) {
        if (isBatasKanan() == false) {
            this.setKolom(kolom += x);
        }
    }

    /**
     * Fungsi untuk menggeser sel ke kiri
     */
    public boolean isBatasKiri() {
        if (kolom * lebar <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBatasAtas() {
        return false;
    }

    public boolean isBatasBawah() {
        return false;
    }

    public void geserKiri(int x) {
        if (isBatasKiri() == false) {
            this.setKolom(kolom -= x);
        }
    }

    /**
     * Fungsi untuk menggeser sel ke atas
     */
    public void geserAtas(int x) {
        if (isBatasAtas() == false) {
            this.setBaris(baris -= x);
        }
    }

    /**
     * Fungsi untuk menggeser sel ke bawah
     */
    public void geserBawah(int x) {
        if (isBatasBawah() == false) {
            this.setBaris(baris += x);
        }
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
