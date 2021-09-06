import javax.swing.*;
import java.awt.*;

import static java.awt.Label.CENTER;

public class SttUI {
    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;

    JLabel waiting;
    FileChooser chooser1;
    String location;
    ImageIcon loading = new ImageIcon("ajax-loader.gif");
    JProgressBar bar = new JProgressBar();
    JLabel photo;
    JLabel lab1= new JLabel();
    JLabel lab2= new JLabel();
    ImageIcon imageIcon= new ImageIcon("logoApp.png");

    public SttUI()  {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        }catch(Exception e){
            e.printStackTrace();
        }

        frame = new JFrame("File reciver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setIconImage(imageIcon.getImage());
        frame.setLayout(new BorderLayout(1,1));

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();

        panel1.setBackground(new Color(0xe2f705));
        //panel2.setBackground(new Color(0x2a3457));
        //panel3.setBackground(new Color(0x2a3457));
        panel4.setBackground(new Color(0xe2f705));
        panel5.setBackground(new Color(0xffffff));

        panel1.setPreferredSize(new Dimension(150,50));
        panel2.setPreferredSize(new Dimension(30,50));
        panel3.setPreferredSize(new Dimension(30,50));
        panel4.setPreferredSize(new Dimension(100,50));
        panel5.setPreferredSize(new Dimension(150,50));
        panel5.setLayout(new GridLayout(0,1));

        frame.add(panel1,BorderLayout.NORTH); //qui metto il pulsante del filechooser
        frame.add(panel2,BorderLayout.WEST);
        frame.add(panel3,BorderLayout.EAST);
        frame.add(panel4,BorderLayout.SOUTH); //qui metto la barra di caricamento
        frame.add(panel5,BorderLayout.CENTER); //qui stampo gli indirizzi ip
        frame.pack();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null); //per posizionare la finestra al centro dello schermo
        frame.setVisible(true);
    }

    public void loading(){
        waiting = new JLabel("loading... ", loading, JLabel.CENTER);
        panel1.add(waiting);
        panel1.revalidate();
        panel1.repaint();
    }


    public void browse()  {
        panel1.removeAll();
        chooser1 = new FileChooser();
        panel1.add(chooser1);
        panel1.revalidate();
        panel1.repaint();
        //conn_open.setVisible(false);
        do{
            location = chooser1.folder_Location;
        }while(location ==null);

    }

    public void showImage(String path, int j){
        //num = new JLabel("the number of file saved until now is: " + j);
       // num.setVisible(true);
        String path1 = path.replace("\\", "/");
        ImageIcon image = new ImageIcon((path1));
        Image im = image.getImage();
        image= new ImageIcon(im.getScaledInstance(40,40,Image.SCALE_SMOOTH));
        photo = new JLabel("the number of file saved until now is: " + j, image, JLabel.CENTER);
        photo.setHorizontalTextPosition(JLabel.LEFT);
        photo.setVisible(true);
        //panel5.add(num);
        panel5.add(photo);
        panel5.revalidate();
        panel5.repaint();
    }
    public void ip1(String wifi){
        panel1.remove(chooser1);
        //lab1 = new JLabel("Se sei connesso al wifi usa questo IP: "+ wifi, SwingConstants.CENTER);
        lab1.setText("Se sei connesso al wifi usa questo IP: "+ wifi);
        panel5.add(lab1);
        panel5.revalidate();
        panel5.repaint();
    }
    public void ip2(String ethernet){
        //lab2 = new JLabel("Se sei connesso con cavo ethernet usa questo IP: "+ ethernet, SwingConstants.CENTER);
        lab2.setText("Se sei connesso con cavo ethernet usa questo IP: "+ ethernet);
        panel5.add(lab2);
        panel5.revalidate();
        panel5.repaint();
    }

    public void progress(){
        panel1.removeAll();
        panel1.add(new JLabel("Saving files...",SwingConstants.CENTER));
        panel4.removeAll();
        panel5.remove(lab1);
        panel5.remove(lab2);
        bar.setValue(0);
        bar.setBounds(0,0,800,50);
        bar.setStringPainted(true);
        bar.setForeground(new Color(0xaf307));
        bar.setBackground(Color.black);
        panel4.add(bar,SwingConstants.CENTER);
    }
}

