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
    private final char dinding = '#';
    private final char player = '@';
    private final char kosong = '.';
    private final char end = 'O';
    private int lebar = 0;
    private int tinggi = 0;
    private int jarak = 30;
    private String isi;
    private boolean completed = false;
    private File alamatPeta;
    private LinkedList<String> undo = new LinkedList<>();
    private ArrayList DaftarPerintah = new ArrayList();

    public Tempat() {
        setFocusable(true);
    }

    public Tempat(File file) {
        bacaKonfigurasi(file);
    }

    public ArrayList<Wall> getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall.add(wall);
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

    private void bacaKonfigurasi(File file) {
        try {
            if (file != null) {
                FileInputStream fis = new FileInputStream(file);
                alamatPeta = file;
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
                        if ((char) data == dinding) {
                            tembok = new Wall(posisiX, posisiY, lebar, tinggi, (char) data);
                            setWall(tembok);
                            posisiX++;
                            lebar += jarak;
                        } else if ((char) data == player) {
                            pemain = new Player(posisiX, posisiY, lebar, tinggi, (char) data);
                            posisiX++;
                            lebar += jarak;
                        } else if ((char) data == kosong) {
                            posisiX++;
                            lebar += jarak;
                        } else if ((char) data == end) {
                            finish = new Finish(posisiX, posisiY, lebar, tinggi, (char) data);
                            posisiX++;
                            lebar += jarak;
                        }
                    } else {
                        posisiY++;
                        tinggi += jarak;
                        this.lebar = lebar;
                        posisiX = 0;
                        lebar = 0;
                    }
                    this.tinggi = tinggi;
                }
                setIsi(isi);
                setSel(pemain, wall, finish);
            }

        } catch (IOException ex) {
            Logger.getLogger(Tempat.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);

        g.fillRect(0, 0, this.getLebar(), this.getTinggi());
        if (!completed) {
            for (int i = 0; i < sel.size(); i++) {
                if (sel.get(i) != null) {
                    Sel item = (Sel) sel.get(i);
                    g.drawImage(item.getImage(), item.getLebar(), item.getTinggi(), this);
                }
            }
        }
        if (completed) {
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.BOLD, 40));
            g.drawString("Congratulation", 50, 80);
        }
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

    public void PerintahGerak(String input) {
        String in[] = input.split(" ");
        if (in[0].equalsIgnoreCase("undo") && in[1].matches("[123456789]")) {
            DaftarPerintah.add(input);
            if (!undo.isEmpty()) {
                for (int index = Integer.parseInt(String.valueOf(in[1])); index > 0; index--) {
                    String x = undo.removeLast();
                    String un[] = x.split(" ");
                    if (un[0].equalsIgnoreCase("u")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekPlayerTabrakTembok(pemain, "u")) {
                                return;
                            } else {
                                pemain.Gerak(0, jarak);
                                repaint();
                            }

                        }
                    } else if (un[0].equalsIgnoreCase("d")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekPlayerTabrakTembok(pemain, "d")) {
                                return;
                            } else {
                                pemain.Gerak(0, -jarak);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("r")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekPlayerTabrakTembok(pemain, "r")) {
                                return;
                            } else {
                                pemain.Gerak(-jarak, 0);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("l")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekPlayerTabrakTembok(pemain, "l")) {
                                return;
                            } else {
                                pemain.Gerak(jarak, 0);
                                repaint();
                            }
                        }

                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Undo Telah Mencapai Akhir");
            }

        } else if (in[0].matches("[udrl]") || in[0].matches("[UDRL]") && in[1].matches("[123456789]") && in.length == 2) {
            undo.addLast(input);
            DaftarPerintah.add(input);
            if (in[0].equalsIgnoreCase("u")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekPlayerTabrakTembok(pemain, "u")) {
                        return;
                    } else {
                        pemain.Gerak(0, -jarak);
                        isCompleted();
                        repaint();
                    }

                }
            } else if (in[0].equalsIgnoreCase("d")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekPlayerTabrakTembok(pemain, "d")) {
                        return;
                    } else {
                        pemain.Gerak(0, jarak);
                        isCompleted();
                        repaint();
                    }
                }
            } else if (in[0].equalsIgnoreCase("r")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekPlayerTabrakTembok(pemain, "r")) {
                        return;
                    } else {
                        pemain.Gerak(jarak, 0);
                        isCompleted();
                        repaint();
                    }
                }
            } else if (in[0].equalsIgnoreCase("l")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekPlayerTabrakTembok(pemain, "l")) {
                        return;
                    } else {
                        pemain.Gerak(-jarak, 0);
                        isCompleted();
                        repaint();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Input Salah! Cek Tutorial");
        }

    }

    private boolean cekPlayerTabrakTembok(Sel pemain, String input) {
        boolean bantu = false;
        if (input.equals("l")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);
                if (pemain.PosisiKiriObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }

        } else if (input.equals("r")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);
                if (pemain.PosisiKananObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equals("u")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);
                if (pemain.PosisiAtasObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equals("d")) {
            for (int i = 0; i < wall.size(); i++) {
                Wall tembok = (Wall) wall.get(i);
                if (pemain.PosisiBawahObjek(tembok)) {
                    bantu = true;
                    break;
                }
            }
        }
        return bantu;
    }

    public void cheat() {
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
        DaftarPerintah.clear();
        wall.clear();
        sel.clear();
        completed = false;
        bacaKonfigurasi(alamatPeta);
        repaint();
    }

    public String getTeksPerintah() {
        String bantu = "";
        for (int i = 0; i < DaftarPerintah.size(); i++) {
            bantu = bantu + DaftarPerintah.get(i) + "\n";
        }
        return bantu;
    }

}
