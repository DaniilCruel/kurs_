package com.crypto.mail;

import javax.mail.Store;
import javax.swing.*;

public class MainApp {


    static Store store;
    static int It = 0;
    public static void main(String[] args)
    {

        WelcomeScreen Window = new WelcomeScreen(); // Создал экземпляр класса
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.setVisible(true);

        System.out.println( store );

        while(  It==0  ) {
            System.out.println("0");
        }

        System.out.println("store != null");

        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }



}
