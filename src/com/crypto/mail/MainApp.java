package com.crypto.mail;

import javax.mail.Store;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class MainApp {


    static Store store;
    protected static User Me = new User();

    static int It = 0;

    public static void main(String[] args)
    {

        WelcomeScreen Window = new WelcomeScreen(); // Создал экземпляр класса
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.setVisible(true);

        System.out.println( store );

        while(  It==0  ) {

            System.out.println("");
        }

        System.out.println("store != null");

        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }



}
