import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

public class FileChooser extends JPanel implements ActionListener {


    JFileChooser chooser;
    JButton go;
    Preferences prefs = Preferences.userNodeForPackage(this.getClass());
    String LAST_FOLDER_USED = "LAST_FOLDER_USED";

    public static String folder_Location;

    public FileChooser() {
        this.setBackground(new Color(0xe2f705));
        go = new JButton("choose the location");
        go.addActionListener(this);
        add(go);
    }

    public void actionPerformed (ActionEvent e){
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("choose where to save the file");
        chooser.setVisible(true);
        chooser.setApproveButtonText("ok");

        if (LAST_FOLDER_USED != null) {
            chooser.setCurrentDirectory(new File(prefs.get(LAST_FOLDER_USED, LAST_FOLDER_USED)));
        } else {
            chooser.setCurrentDirectory(new java.io.File("."));
        }

        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());

            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());

            folder_Location = chooser.getSelectedFile().toString();

            prefs.put(LAST_FOLDER_USED, folder_Location);
            return;
        }
        else {
            System.out.println("No Selection ");
        }
    }
}