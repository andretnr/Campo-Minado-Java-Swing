package br.com.atnunes.campominado.view;

import br.com.atnunes.campominado.model.Table;

import javax.swing.*;
import java.awt.*;

public class PanelTable extends JPanel {
    public PanelTable(Table table){
        setLayout(new GridLayout(table.getLines(), table.getColumns()));

      table.forEachField(f -> add(new FieldButton(f)));
      table.registerObserver(e -> {
          SwingUtilities.invokeLater(() -> {
              if(e.isWin()){
                  JOptionPane.showMessageDialog(this, "Você Venceu!");
              }else {
                  JOptionPane.showMessageDialog(this, "BUUUUMMMMMM!");
              }
              table.restart();

          });

      });


    }


}
