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

    private ArrayList<Wall> wall = new ArrayList<>();
    private ArrayList<Sel> sel = new ArrayList<>();
    private Player pemain;
    private Finish finish;
    private LinkedList<String> undo = new LinkedList<>();
    private final char TEMBOK = '#';
    private final char PLAYER = '@';
    private final char KOSONG = '.';
    private final char FINISH = 'O';
    private int lebarTempat = 0;
    private int tinggiTempat = 0;
    private int jarak = 30;
    private String isi;
    private boolean completed = false;
    private File Alamatpeta;
    private ArrayList DaftarPerintah = new ArrayList();

    public Tempat() {
        setFocusable(true);
    }

    public Tempat(File file) {
        bacaKonfigurasi(file);
    }

    public String getIsi() {
        return isi;
    }

    public ArrayList<Wall> getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall.add(wall);
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
        try {
            if (file != null) {
                FileInputStream fis = new FileInputStream(file);
                Alamatpeta = file;
                int lebar = 0;
                int tinggi = 0;
                int posisiX = 0;
                int posisiY = 0;
                Wall tembok;
                String isi = "";
                int data;
                while ((data = fis.read()) != -1) {
                    isi = isi + (char) data;
                    if ((char) data != '\n') {
                        if ((char) data == TEMBOK) {
                            tembok = new Wall(posisiX, posisiY, lebar, tinggi, (char) data);
                            setWall(tembok);
                            posisiX++;
                            lebar += jarak;
                        } else if ((char) data == PLAYER) {
                            pemain = new Player(posisiX, posisiY, lebar, tinggi, (char) data);
                            posisiX++;
                            lebar += jarak;
                        } else if ((char) data == KOSONG) {
                            posisiX++;
                            lebar += jarak;
                        } else if ((char) data == FINISH) {
                            finish = new Finish(posisiX, posisiY, lebar, tinggi, (char) data);
                            posisiX++;
                            lebar += jarak;
                        }
                    } else {
                        posisiY++;
                        tinggi += jarak;
                        lebarTempat = lebar;
                        posisiX = 0;
                        lebar = 0;
                    }
                    tinggiTempat = tinggi;
                }
                setIsi(isi);
                setSel(pemain, wall, finish);
            }

        } catch (IOException ex) {
            Logger.getLogger(Tempat.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        boolean bantu = false;
        if (input.equals("l")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);//ambil posisi tembok
                if (pemain.PosisiKiriObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }

        } else if (input.equals("r")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);//ambil posisi tembok
                if (pemain.PosisiKananObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equals("u")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);//ambil posisi tembok
                if (pemain.PosisiAtasObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equals("d")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);//ambil posisi tembok
                if (pemain.PosisiBawahObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }
        }
        return bantu;
    }
    
       public void undo() {
        for (int i = semuaPerintah.size() - 1; i >= 0; i--) {
            String input = semuaPerintah.get(i).toString();
            String[] undo = input.split(" ");
            if (undo[1].equalsIgnoreCase("l")) {
                if (cekObjekNabrakDinding(pemain, "r")) {
                    return;
                } else {
                    pemain.Gerak((Integer.valueOf(undo[0]) * jarak), 0);
                    repaint();
                }
                break;
            } else if (undo[1].equalsIgnoreCase("r")) {
                if (cekObjekNabrakDinding(pemain, "l")) {
                    return;
                } else {
                    pemain.Gerak((Integer.valueOf(undo[0]) * -jarak), 0);
                    repaint();
                }
                break;
            } else if (undo[1].equalsIgnoreCase("u")) {
                if (cekObjekNabrakDinding(pemain, "d")) {
                    return;
                } else {
                    pemain.Gerak(0, (Integer.valueOf(undo[0]) * jarak));
                    repaint();
                }
                break;
            } else if (undo[1].equalsIgnoreCase("d")) {
                if (cekObjekNabrakDinding(pemain, "u")) {
                    return;
                } else {
                    pemain.Gerak(0, (Integer.valueOf(undo[0]) * -jarak));
                    repaint();
                }
                break;
            }
        }
    }

    public void redo() {
        for (int i = semuaPerintah.size() - 1; i >= 0; i--) {
            String input = semuaPerintah.get(i).toString();
            String[] redo = input.split(" ");
            if (redo[1].equalsIgnoreCase("u")) {
                for (int j = 0; j < Integer.parseInt(String.valueOf(redo[0])); j++) {
                    if (cekObjekNabrakDinding(pemain, "u")) {
                        return;
                    } else {
                        pemain.Gerak(0, -jarak);
                        repaint();
                    }

                }
                break;
            } else if (redo[1].equalsIgnoreCase("d")) {
                for (int j = 0; j < Integer.parseInt(String.valueOf(redo[0])); j++) {
                    if (cekObjekNabrakDinding(pemain, "d")) {
                        return;
                    } else {
                        pemain.Gerak(0, jarak);
                        repaint();
                    }
                }
                break;
            } else if (redo[1].equalsIgnoreCase("r")) {
                for (int j = 0; j < Integer.parseInt(String.valueOf(redo[0])); j++) {
                    if (cekObjekNabrakDinding(pemain, "r")) {
                        return;
                    } else {
                        pemain.Gerak(jarak, 0);
                        repaint();
                    }
                }
                break;
            } else if (redo[1].equalsIgnoreCase("l")) {
                for (int j = 0; j < Integer.parseInt(String.valueOf(redo[0])); j++) {
                    if (cekObjekNabrakDinding(pemain, "l")) {
                        return;
                    } else {
                        pemain.Gerak(-jarak, 0);
                        repaint();
                    }
                }
            }
        }

    }

    public void pintas() {
        pemain.setLebar(finish.getLebar());
        pemain.setTinggi(finish.getTinggi());
        isCompleted();
        repaint();
    }

    public void isCompleted() {
        if (pemain.getLebar() == finish.getLebar() && pemain.getTinggi() == finish.getTinggi()) {
            completed = true;
        }
    }

    public void restartLevel() {
        Allperintah.clear();//hapus semua perintah yang tersimpan
        wall.clear();//hapus tembok
        sel.clear();//hapus map
        completed = false;
        bacaKonfigurasi(Alamatpeta);//set ulang gambar peta
        repaint();//gambar ulang
    }

    public String getTeksPerintah() {
        String bantu = "";
        for (int i = 0; i < Allperintah.size(); i++) {
            bantu = bantu + Allperintah.get(i) + "\n";
        }
        return bantu;
    }

}
