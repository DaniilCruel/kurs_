package com.crypto.mail;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomeScreen extends JFrame{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    Image img;
    JButton b1;
    JLabel l1;

    public WelcomeScreen() { // Публичный конструктор, куда передаем весь приватный метод


        JFrame.setDefaultLookAndFeelDecorated(true);

        init();



        l1=new JLabel("Here is a button");
        b1=new JButton("I am a button");
        add(l1);
        add(b1);

    }


    private void init() { // Приватный метод, где работаем с окном
        setTitle("Background Color for JFrame");
        setSize(500,300);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource("/IMG/BG.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
        setLayout(new FlowLayout());

    }
}
