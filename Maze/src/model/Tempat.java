/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author user only
 */
public class Tempat extends JPanel implements Serializable {  
    private ArrayList<Sel> sel = new ArrayList<>();
    private LinkedList<String> undo = new LinkedList<>();
    private int lebarTempat = 0;
    private int tinggiTempat = 0;
    private int jarak = 30;
    private String isi;
    private boolean completed = false;
    private ArrayList Allperintah = new ArrayList();

    public Tempat() {
        setFocusable(true);
    }

    public Tempat(File file) {
        bacaKonfigurasi(file);
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

   
    public ArrayList<Sel> getSel() {
        return sel;
    }

    public void setSel(Player pemain, ArrayList<Wall> tembok, Finish finish) {
        this.sel.add(pemain);
        this.sel.addAll(tembok);
        this.sel.add(finish);
    }

    public void simpanKonfigurasi(File file, Tempat tempat) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            for (int i = 0; i < tempat.getSel().size(); i++) {
                String data = tempat.getSel().get(i).toString();
                fos.write(data.getBytes());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tempat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tempat.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Tempat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void bacaKonfigurasi(File file) {
        
    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);	   // Hapus background
        // Tempat Gambar:
        g.setColor(new Color(255, 255, 255));//set panel warna putih
        g.fillRect(0, 0, this.getLebarTempat(), this.getTinggiTempat());// set tinggi lebar sesuai konfigurasi
        if (!completed) {
            for (int i = 0; i < sel.size(); i++) {
                if (sel.get(i) != null) {
                    Sel item = (Sel) sel.get(i);//map diterjemahkan dalam kelas pixel.
                    g.drawImage(item.getImage(), item.getLebar(), item.getTinggi(), this);//proses gambar di panel
                }
            }
        }
        if (completed) {
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Serif", Font.BOLD, 48));
            g.drawString("Winner", 50, 80);
        }
    }

    public int getLebarTempat() {
        return lebarTempat;
    }

    public void setLebarTempat(int lebarTempat) {
        this.lebarTempat = lebarTempat;
    }

    public int getTinggiTempat() {
        return tinggiTempat;
    }

    public void setTinggiTempat(int tinggiTempat) {
        this.tinggiTempat = tinggiTempat;
    }

    public void PerintahGerak(String input) {
        String in[] = input.split(" ");
        
    }

    private boolean cekPemainNabrakTembok(Sel pemain, String input) {  
        return false;//default return false
    }

    
}