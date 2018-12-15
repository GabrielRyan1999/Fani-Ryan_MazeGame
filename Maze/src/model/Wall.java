/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ryan
 */
public class Wall extends Sel {

    private int lebar;
    private int tinggi;
    private char nilai;

    public Wall(int x, int y, int lebar, int tinggi, char nilai) {
        super(x, y);
        this.lebar = lebar;
        this.tinggi = tinggi;
        this.nilai = nilai;
    }

    @Override
    public int getLebar() {
        return lebar;
    }

    @Override
    public void setLebar(int lebar) {
        this.lebar = lebar;
    }

    @Override
    public int getTinggi() {
        return tinggi;
    }

    @Override
    public void setTinggi(int tinggi) {
        this.tinggi = tinggi;
    }

    @Override
    public char getNilai() {
        return nilai;
    }

    @Override
    public void setNilai(char nilai) {
        this.nilai = nilai;
    }

}
