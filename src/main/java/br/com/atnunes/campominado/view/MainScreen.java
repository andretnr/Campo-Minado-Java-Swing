package br.com.atnunes.campominado.view;


import br.com.atnunes.campominado.model.Table;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import javax.swing.*;

/**
 * @author atnunes
 * @version 0.1
 * @since 10/06/2020
 * @email andretnr@gmail.com
 **/

public class MainScreen extends JFrame {

    public MainScreen(){

        try {
            UIManager.setLookAndFeel(new AluminiumLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Table table = new Table(16, 30, 5);



        add(new PanelTable(table));

        setTitle("Campo Minado by Dev. André Nunes");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }


    public static void main(String[] args) {
        new MainScreen();
    }
}
