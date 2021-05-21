package com.crypto.mail;


import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class WelcomeScreen extends JFrame{

    private JTextField from_field;
    private JTextField to_field;
    private Box BoxResult;

    JButton calculateButton = new JButton("Войти");
    JCheckBox javaScript = new JCheckBox("Сохранить Логин и Пароль");


    public WelcomeScreen() { // Публичный конструктор, куда передаем весь приватный метод

        init();
        Box();

         calculateButton.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent actionEvent) {
                try {

                    // Считать значения границ отрезка, шага из полей ввода
                    String user =(from_field.getText());
                    String pass =(to_field.getText());
                    MainApp.Me.username = user;
                    MainApp.Me.password = pass;


                    if((user != null && pass != null )) {

                        Properties properties = new Properties();
                        properties.put("mail.store.protocol", "imaps");
                        Session emailSession = Session.getInstance(properties);

                        MainApp.store = emailSession.getStore("imaps");
                        MainApp.store.connect("imap.gmail.com", user, pass);

                        setVisible(false);
                        dispose();
                        MainApp.It=1;

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(WelcomeScreen.this,
                                "Проверьте введенные данные\nНевозможно провести вычисления", "" +
                                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // В случае ошибки преобразования показать сообщение об ошибке
                    JOptionPane.showMessageDialog(WelcomeScreen.this, "Ошибка в формате записи числа с плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                } catch (NoSuchProviderException e) {
                    JOptionPane.showMessageDialog(WelcomeScreen.this,
                            "Проверьте введенные данные\nНевозможно провести вычисления", "" +
                                    "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                } catch (MessagingException e) {
                    JOptionPane.showMessageDialog(WelcomeScreen.this,
                            "Проверьте введенные данные\nНевозможно провести вычисления", "" +
                                    "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        javaScript.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

            }
        });



    }

    private void init() { // Приватный метод, где работаем с окном
        setTitle("Авторизация");
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



    private void Box() { // Приватный метод, где работаем с окном
        Box dataBox= Box.createHorizontalBox();
        dataBox.add(Box.createHorizontalGlue());

        dataBox.add(Box.createHorizontalStrut(40));
        from_field = new JTextField("bobtestbgr@gmail.com",20);
        from_field.setMaximumSize(from_field.getPreferredSize());
        dataBox.add(from_field);
        dataBox.add(Box.createHorizontalStrut(10));

        dataBox.add(Box.createHorizontalStrut(20));
        to_field = new JTextField("view/2pvjrh",20);
        to_field.setMaximumSize(to_field.getPreferredSize());
        dataBox.add(to_field);
        dataBox.add(Box.createHorizontalStrut(20));
        dataBox.add(Box.createHorizontalStrut(10));

        dataBox.add(Box.createHorizontalGlue());
        dataBox.setPreferredSize(new
                Dimension(new Double(dataBox.getMaximumSize().getWidth()).intValue(), new
                Double(dataBox.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(dataBox, BorderLayout.NORTH);

        Box buttonBox=Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(calculateButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add( javaScript);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.setPreferredSize(new
                Dimension(new Double(buttonBox.getMaximumSize().getWidth()).intValue(), new
                Double(buttonBox.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(buttonBox, BorderLayout.SOUTH);

        BoxResult=Box.createHorizontalBox();
        BoxResult.add(new JPanel());
        getContentPane().add(BoxResult, BorderLayout.CENTER);
    }


}
