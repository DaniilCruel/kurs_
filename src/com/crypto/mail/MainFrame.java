package com.crypto.mail;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;



public class MainFrame extends JFrame{
    private static final int FRAME_MINIMUM_WIDTH = 900;
    private static final int FRAME_MINIMUM_HEIGHT = 550;

    //public static String username = "bobtestbgr@gmail.com";
   // public static String password = "view/2pvjrh";
    public static String username = "bobtestbgr@gmail.com";
    public static String password = "view/2pvjrh";
    private JTextArea forall;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private HashMap<String, JTextArea> map = new HashMap();
    private HashMap<String, String> map2 = new HashMap();


    private ImageIcon  icon = null;

    private MainFrame() throws Exception{
        super("Клиент мгновенных сообщений");

/*
        username= JOptionPane.showInputDialog(MainFrame.this,"","Введите ваш логин", JOptionPane.PLAIN_MESSAGE);
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите логин!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        password= JOptionPane.showInputDialog(MainFrame.this,"","Введите ваш пороль", JOptionPane.PLAIN_MESSAGE);

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите пороль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
*/



        setMinimumSize(new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2
                , (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);
        setLayout(null);
        setTitle("Чат, ваше имя: "+username);



        listModel=new DefaultListModel< >();
        listModel.addElement("Все");
        list=new JList< >(listModel);
        list.setLayoutOrientation(JList.VERTICAL);
        final JScrollPane scrollPaneList=new JScrollPane(list);

        scrollPaneList.setLocation(3,3);
        scrollPaneList.setSize(getWidth()-20,60);

        tabbedPane.setLocation(7,70);
        tabbedPane.setSize(getWidth()-20,getHeight()-40);

        this.add(scrollPaneList);
        this.add(tabbedPane);
        this.getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener() {
            public void ancestorResized(HierarchyEvent arg0) {

                scrollPaneList.setSize(getWidth()-22,50);
                tabbedPane.setSize(getWidth()-20,getHeight()-80);
            }
            public void ancestorMoved(HierarchyEvent arg0) {}
        });


        String algorithm = "AES";
        KeyGenerator generator = KeyGenerator.getInstance(algorithm);
        generator.init(128); //generate 128 bit key
        SecretKey secretKey = generator.generateKey();
        byte[] key = secretKey.getEncoded();

        //----------------------------
        byte[] mySecretKey = {48, -126, 4, -65, 2, 1, 0, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 4, -126, 4, -87, 48, -126, 4, -91, 2, 1, 0, 2, -126, 1, 1, 0, -95, -31, 108, -69, 59, 77, -11, 43, -116, 118, 74, -75, -123, 117, -44, 39, 121, 89, 67, 10, -57, -27, 25, 110, -86, 28, -57, -116, 119, -93, 40, -84, 123, -105, 30, -66, 69, -67, 104, 121, -68, 102, 71, -44, 87, -3, -45, -21, -88, 59, 64, 88, -124, -20, -90, 21, 52, -115, 125, -33, 93, 0, 43, -67, -6, 76, 22, -123, 88, -91, -43, 63, 32, 74, 107, 12, -69, -80, -80, 124, -79, 16, 0, -55, -113, 84, -43, 17, -15, 26, -29, -2, 124, 0, -26, 25, 97, -64, 32, -22, 99, 91, 91, -42, -85, 93, -52, 76, -71, -53, 45, 38, 52, -28, -6, 92, 108, -105, -84, -53, 29, 80, 60, -98, -103, 63, 71, 3, -30, -118, -94, 76, 39, 46, -112, -65, 14, 112, -82, -38, 31, 20, -10, -53, 101, -52, 37, 3, -20, -84, -83, -128, -88, 27, -109, -5, 69, -18, -42, 123, -31, 85, -101, -4, -93, -95, 13, -8, 49, -120, 90, -42, -102, -73, 85, -59, -56, 69, -79, 106, 32, 51, -77, -3, 108, -101, -99, -96, 33, 63, -66, -82, 16, 60, -45, 52, -125, -114, -23, -84, -47, -19, 75, -24, 67, -62, 34, 124, -18, -38, 7, -24, -126, 123, 80, 34, 104, -107, -85, -11, -6, -8, -81, -59, -121, 81, 99, -90, -27, 0, 80, 30, -122, 71, 112, 116, 114, -79, -45, 3, -40, -119, 119, -61, 19, 16, -95, -28, 64, -73, -87, 4, 62, -70, 54, 17, 2, 3, 1, 0, 1, 2, -126, 1, 0, 103, -34, 95, -55, 7, 53, 111, 65, -95, 31, -23, -79, -19, 79, 124, 112, 83, -95, -99, -83, -42, 51, 63, 13, 77, -29, -89, 122, -114, -19, 70, -44, -35, 124, 74, -62, -91, -74, -15, -55, 98, -60, 114, 4, 98, -19, 64, 68, 46, 46, -50, -117, -67, -58, -90, -114, 102, -88, -1, 2, 10, 7, 105, 9, -66, 7, -126, 79, -49, 96, -96, -94, -97, -110, -128, 123, -84, 22, -92, 55, 109, -39, 41, -40, 42, -70, -80, -8, -111, 46, -62, -23, -16, 33, 23, -125, 99, -64, 70, -98, 126, 96, 9, 94, -123, -104, 106, -43, 52, 69, -108, -45, -74, 14, -46, 65, -23, 127, -84, -28, -85, 11, 103, -54, 15, -28, -107, 40, 117, 100, -3, 87, 31, -31, -9, 90, -40, 10, -72, -3, 64, -44, 90, -16, -113, -118, 93, -107, -1, 12, -72, 88, 100, -10, 49, 119, -6, 8, -29, -48, 83, -122, 53, 16, 54, -28, -1, 33, -33, -63, 75, 42, -55, 3, -63, 48, -91, -67, -21, 116, 60, 74, -111, 66, 60, 9, 10, -12, 55, -107, 104, -123, -100, -48, -84, -4, -40, 109, 65, -78, 104, -69, -76, -42, -22, -111, 122, -70, 97, 37, -102, 115, -120, -22, -57, 4, 25, 121, 127, -98, 63, -48, 125, 19, 126, 95, 55, -7, 45, 24, 12, 40, 40, 62, -94, 72, 35, -122, 60, -72, -109, -4, -40, -13, 0, 33, 101, -124, 106, -108, 97, -11, -58, -112, -46, -83, 2, -127, -127, 0, -18, -29, 44, -70, 107, 16, -29, -89, -95, -108, -51, 86, -119, 78, 1, -5, 66, -73, -98, -19, -36, -79, -125, -100, -14, -23, 1, -6, 37, -59, 46, -50, 111, -7, -77, -124, -77, -70, -117, 76, 81, -116, -78, 51, -83, 45, -9, -47, -20, 36, 93, -56, 69, 75, 126, -105, -60, -96, -1, -15, 6, -101, -99, -110, -109, 98, -107, -19, -48, -112, 27, 114, 47, -14, -15, 65, 102, -108, 44, 43, -20, 9, 35, 111, 70, 86, 27, 100, -106, 47, 101, -30, -8, -106, -1, 66, -3, 4, 54, 96, -73, 78, 101, 93, -53, 42, -56, 74, 70, 55, 39, -52, -54, 121, -25, -123, -34, -127, 84, -75, -22, 38, 95, 58, -88, -77, 104, -9, 2, -127, -127, 0, -83, 122, 16, 85, 115, -99, 126, -66, -14, -70, 40, -1, 101, 10, 78, -69, 36, -87, -40, 126, -63, -32, 50, -70, 116, 102, -87, 81, -33, 43, 112, -116, -2, 22, -89, -66, -3, 21, -34, 28, -110, -27, -48, -26, -91, -31, 29, -81, -44, 32, -106, -65, -14, -23, -94, -56, -30, -30, 30, 87, -53, -123, 9, -67, -7, -15, -100, -98, -17, 20, -19, -107, 94, -108, 37, -38, -82, 101, -21, 123, 31, 19, 108, 67, 27, 12, -58, -95, -95, -43, 71, 104, -74, 27, -53, -84, -117, 44, 125, 54, 19, 99, 86, -13, 109, -108, 18, -89, -101, -32, -35, 45, -37, -93, 127, -101, -90, -45, -125, 9, 4, -31, -51, -125, -23, -35, 95, 55, 2, -127, -127, 0, -92, -85, -124, -111, -100, 108, 48, 1, -59, -88, 69, 67, 121, -78, -124, 59, 39, 106, 91, -21, -85, 77, -46, 99, -58, 46, 72, 102, -98, -46, -91, -55, 55, -10, -16, -128, 113, 68, 13, -15, -75, -27, 62, -111, -48, -74, -9, 53, -123, -118, 43, -5, 121, -120, -24, -30, -59, 112, 21, -3, -105, -120, 125, -66, 36, 74, -72, -1, 13, -35, -56, -115, -107, -17, -23, -50, 35, 95, -48, 115, 22, 105, -42, 59, 70, 72, -28, -23, 25, 125, -30, -59, -22, -122, 107, -65, 73, -102, 53, -63, -59, -102, 12, -43, -14, -50, -78, -14, 64, 36, -107, 18, -34, 31, -38, 48, -75, -120, -127, 84, 68, -21, -34, -119, 24, -15, 83, 81, 2, -127, -127, 0, -98, 24, 85, -10, 106, 77, 40, 11, 65, 14, 34, -6, -51, -52, 92, -1, -12, 99, -51, 103, -121, -84, -104, -50, -113, -14, 87, 112, -61, -97, -59, 12, -39, 43, 48, 104, -64, 33, 67, -80, 106, -73, -126, 112, 16, -48, 93, -53, -75, -40, -107, 74, 13, 72, -101, 15, -44, -91, 25, -34, 13, 30, 11, 72, -43, 22, 58, 20, 37, -14, -66, -86, -105, -19, 15, -86, -127, -79, 100, -81, 106, 28, -69, 87, 84, -71, -119, -12, 23, -106, 85, 99, -70, 67, 14, -107, 10, -88, -38, -37, -125, 67, -49, 36, 61, -62, -22, 85, 81, -101, -42, 54, 74, -86, 112, 75, 27, -8, 58, -68, -25, 116, 25, 101, -108, -90, 40, 117, 2, -127, -127, 0, -125, -46, 114, 101, 122, -57, 48, -39, 96, 24, -28, -13, -42, -113, 29, -75, -57, 43, -76, -91, 107, 105, 107, 32, -84, -101, 112, -52, -97, -111, -23, 87, -40, 102, 73, 40, -86, 46, -114, -76, -108, 120, -47, -4, -95, 16, 109, 63, -103, -56, -42, -29, -92, 13, -90, -32, -48, 4, -15, 91, -124, -45, -124, 96, 76, -84, 51, -61, -11, 100, -33, 65, -121, -95, -123, -43, -41, -11, 34, -120, 33, -79, -37, 55, 81, -32, -103, -42, 3, -40, 66, -10, 49, 4, 23, 107, 61, 20, -106, -53, -50, 122, 17, 105, -102, 78, 35, 55, -88, 21, -53, 125, -34, 90, -100, 91, 71, 84, 43, 21, 116, 65, 32, -65, -71, 49, 11, -45};


        //---------------------------------------
        //key exchange
        //Alice's public key
        byte[] publicKey = {48, -126, 1, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 1, 15, 0, 48, -126, 1, 10, 2, -126, 1, 1, 0, -95, -31, 108, -69, 59, 77, -11, 43, -116, 118, 74, -75, -123, 117, -44, 39, 121, 89, 67, 10, -57, -27, 25, 110, -86, 28, -57, -116, 119, -93, 40, -84, 123, -105, 30, -66, 69, -67, 104, 121, -68, 102, 71, -44, 87, -3, -45, -21, -88, 59, 64, 88, -124, -20, -90, 21, 52, -115, 125, -33, 93, 0, 43, -67, -6, 76, 22, -123, 88, -91, -43, 63, 32, 74, 107, 12, -69, -80, -80, 124, -79, 16, 0, -55, -113, 84, -43, 17, -15, 26, -29, -2, 124, 0, -26, 25, 97, -64, 32, -22, 99, 91, 91, -42, -85, 93, -52, 76, -71, -53, 45, 38, 52, -28, -6, 92, 108, -105, -84, -53, 29, 80, 60, -98, -103, 63, 71, 3, -30, -118, -94, 76, 39, 46, -112, -65, 14, 112, -82, -38, 31, 20, -10, -53, 101, -52, 37, 3, -20, -84, -83, -128, -88, 27, -109, -5, 69, -18, -42, 123, -31, 85, -101, -4, -93, -95, 13, -8, 49, -120, 90, -42, -102, -73, 85, -59, -56, 69, -79, 106, 32, 51, -77, -3, 108, -101, -99, -96, 33, 63, -66, -82, 16, 60, -45, 52, -125, -114, -23, -84, -47, -19, 75, -24, 67, -62, 34, 124, -18, -38, 7, -24, -126, 123, 80, 34, 104, -107, -85, -11, -6, -8, -81, -59, -121, 81, 99, -90, -27, 0, 80, 30, -122, 71, 112, 116, 114, -79, -45, 3, -40, -119, 119, -61, 19, 16, -95, -28, 64, -73, -87, 4, 62, -70, 54, 17, 2, 3, 1, 0, 1};
        Cipher publicKeyEncryption = Cipher.getInstance("RSA");
        publicKeyEncryption.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey)));
        byte[] encryptedKey = publicKeyEncryption.doFinal(key);
        //String encryptedByteKey = Arrays.toString(encryptedKey);
        String encryptedByteKey = new String(Base64.getEncoder().encode(encryptedKey));
        //key exchange end

        //---------------------------------------


        reciveMSG(algorithm, mySecretKey);
        newTab("Все",null, key, algorithm, encryptedByteKey);


    }



    private void newTab(final String addres, String address, byte[]  key, String algorithm, String encryptedByteKey){

        JPanel panel = new JPanel();
        final JTextArea textAreaHist = new JTextArea(1, 15);
        textAreaHist.setEditable(false);
        textAreaHist.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent arg0) {

            }

            @Override
            public void focusGained(FocusEvent arg0) {

            }
        });
        final JScrollPane scrollPaneOutgoing = new JScrollPane(textAreaHist);
        map.put(addres, textAreaHist);
        final JTextArea textAreaMsg = new JTextArea(1, 15);
        final JScrollPane scrollPaneMsg = new JScrollPane(textAreaMsg);
        final JButton btn=new JButton("Отправить");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    sendMessage(addres, textAreaMsg, textAreaHist, key , algorithm, encryptedByteKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        String hist=map2.get(addres);
        if(hist!=null)
            textAreaHist.append(hist);
        tabbedPane.addTab(addres, panel);
        forall=textAreaHist;
        panel.setLayout(null);
        panel.add(btn);
        panel.add(scrollPaneOutgoing);
        panel.add(scrollPaneMsg);
        btn.setSize(120,20);
        btn.setLocation(tabbedPane.getWidth()-400,tabbedPane.getHeight()-82);
        scrollPaneOutgoing.setSize(tabbedPane.getWidth()-20,tabbedPane.getHeight()-250);
        scrollPaneMsg.setSize(tabbedPane.getWidth()-80,140);
        scrollPaneMsg.setLocation(40,tabbedPane.getHeight()-230);
        panel.addHierarchyBoundsListener(new HierarchyBoundsListener() {
            public void ancestorResized(HierarchyEvent e) {
                btn.setSize(120,20);
                btn.setLocation(tabbedPane.getWidth()-400,tabbedPane.getHeight()-82);
                scrollPaneOutgoing.setSize(tabbedPane.getWidth()-20,tabbedPane.getHeight()-250);
                scrollPaneMsg.setSize(tabbedPane.getWidth()-80,140);
                scrollPaneMsg.setLocation(40,tabbedPane.getHeight()-230);
            }
            public void ancestorMoved(HierarchyEvent e) {}
        });
        if(address!=null)
            tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1,new com.crypto.mail.ButtonTabComponent(tabbedPane,this,addres));
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
    }

    private void sendMessage(String recipient, JTextArea textAreaMsg, JTextArea textAreaHist, byte[]  key, String algorithm, String encryptedByteKey) throws Exception {
        String message = textAreaMsg.getText();
        String subject = "You've got an encrypted mail!";

        String encryptedContent = encryptContent(key, algorithm, message);


        sendMail(recipient, subject, encryptedContent, encryptedByteKey);

        writeText(textAreaHist, "Я",message);
    }

    public static void sendMail(String r, String subject, String content, String encryptedKey) throws Exception{

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });
        String recipient = "bobtestbgr@gmail.com";
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        //message.setText(content, "ISO-8859-1");
        //message.setText(content);
        //---------------------------------------------------

        //attachment
        Multipart multipart = new MimeMultipart();

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(content);
        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart attachment = new MimeBodyPart();
        attachment.setText(encryptedKey);
        attachment.setFileName("encrypted_key.txt");
        multipart.addBodyPart(attachment);

        message.setContent(multipart);

        //attachment end

        //-------------------------------------------------
        Transport.send(message);

        System.out.println("mail delivered successfully...");

    }

    public static String encryptContent(byte[] key, String algorithm, String content) throws Exception{

        byte[] plainContent = content.getBytes();
        Cipher encryption = Cipher.getInstance(algorithm);
        encryption.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, 0, key.length, algorithm));
        byte[] encryptedContent = encryption.doFinal(plainContent);

        //return new String(encryptedContent, "ISO-8859-1");
        //return Arrays.toString(encryptedContent);

        return new String(Base64.getEncoder().encode(encryptedContent));

    }

    private void writeText(JTextArea textarea,String name, String message){

        textarea.append(name+": "+message+"\n");
        textarea.moveCaretPosition(textarea.getText().length());
    }

    private void reciveMSG(String algorithm, byte[]  mySecretKey){
        new Thread(new Runnable() {
            Date date= null;
            int endi = 0;
            public void run() {
                try {
                    Properties properties = new Properties();
                    properties.put("mail.store.protocol", "imaps");
                    Session emailSession = Session.getInstance(properties);

                    Store store = emailSession.getStore("imaps");
                    store.connect("imap.gmail.com", username, password);


                    while (!Thread.interrupted()) {
                        Folder emailFolder = store.getFolder("INBOX");
                        emailFolder.open(Folder.READ_ONLY);
                        Message[] messages = emailFolder.getMessages();



                        Message mesd = messages[messages.length-1];
                        Date date1 = mesd.getReceivedDate();
                        System.out.println( " -------");
                        System.out.println( date);
                        System.out.println( date1);
                        System.out.println( " -------");

                        if ( date != null && date.compareTo(date1) == 0) {
                            System.out.println("+==+");
                            endi = messages.length;
                            continue;
                        }
                        date = mesd.getReceivedDate();

                        for (int i = endi; i < messages.length; i++) {

                            Message message = messages[i];


                            System.out.println("Index " + (i + 1));
                            System.out.println(message.getReceivedDate());
                            System.out.println("From: " + message.getFrom()[0]);
                            System.out.println("Subject: " + message.getSubject());

                            Multipart multipart = (Multipart) message.getContent();
                            BodyPart bodyPart = multipart.getBodyPart(0);
                            String encryptedContent = bodyPart.getContent().toString();

                            byte encryptedByteContent[] = Base64.getDecoder().decode(encryptedContent.getBytes());

                            //----------------------------------
                            //key exchange

                            String encryptedKey = ((Multipart) message.getContent()).getBodyPart(1).getContent().toString();
                            //byte[] encryptedByteKey = toByteArray(encryptedKey);
                            byte[] encryptedByteKey = Base64.getDecoder().decode(encryptedKey);

                            Cipher publicKeyEncryption = Cipher.getInstance("RSA");
                            publicKeyEncryption.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(mySecretKey)));
                            byte[] key = publicKeyEncryption.doFinal(encryptedByteKey);

                            //----------------------------------


                            String descrypmess = ("  " + decryptContent(algorithm, key, encryptedByteContent));
                            procMSG(descrypmess);


                        }

                        emailFolder.close(false);
                    }

                    store.close();


                    } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | MessagingException noSuchProviderException) {
                    noSuchProviderException.printStackTrace();

            } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static String decryptContent(String algorithm, byte[] key, byte[] encryptedByteContent) throws Exception{

        Cipher decryption = Cipher.getInstance(algorithm);
        decryption.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, 0, key.length, algorithm));
        //byte[] decryptedContent = decryption.doFinal(encryptedContent.getBytes("ISO-8859-1"));
        byte[] decryptedContent = decryption.doFinal(encryptedByteContent);

        //return new String(decryptedContent, "ISO-8859-1");
        return new String(decryptedContent);

    }


    private void procMSG(String msg){
        String nick=("aboba");

            writeText(forall, nick,msg);

    }


    private void addUser(String nick, String ip, String port){
        String item = nick+"@"+ip+":"+port;
        listModel.addElement(item);
    }





    public static void main(String[] args) throws Exception{

        final MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

