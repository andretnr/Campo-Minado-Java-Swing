package br.com.atnunes.campominado.view;


import br.com.atnunes.campominado.model.EventField;
import br.com.atnunes.campominado.model.Field;
import br.com.atnunes.campominado.model.ObserverField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * @author atnunes
 * @version 0.1
 * @email andretnr@gmail.com
 * @since 10/06/2020
 **/

@SuppressWarnings("serial")
public class FieldButton extends JButton implements ObserverField, MouseListener {

    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_CHECKED = new Color(8, 179, 247);
    private final Color BG_EXPLODED = new Color(189, 66, 68);
    private final Color TXT_GREEN = new Color(0, 100, 0);

    private Field field;

    public FieldButton(Field field) {
        this.field = field;
        setBackground(BG_DEFAULT);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        field.observerRegister(this);

    }


    @Override
    public void eventOcurred(Field field, EventField event) {
        switch (event) {
            case OPEN:
                aplyOpenStyle();
                break;
            case CHECK:
                aplyCheckStyle();
                break;
            case EXPLODE:
                aplyExplodeStyle();
                break;
            default:
                aplyDefaultStyle();
        }
    SwingUtilities.invokeLater(() -> {
        repaint();
        validate();
    });
    }

    private void aplyDefaultStyle() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplyOpenStyle() {
        if (field.isPumped()){
            setBackground(BG_EXPLODED);
            return;

        }
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createLineBorder(Color.CYAN));

       switch (field.neighborhoodPumped()){
           case 1:
               setForeground(TXT_GREEN);
               break;
           case 2:
               setForeground(Color.BLUE);
               break;
           case 3:
               setForeground(Color.YELLOW);
               break;
           case 4:
           case 5:
           case 6:
               setForeground(Color.RED);
           default:
               setForeground(Color.PINK);
       }
       String value = !field.neighborHoodSecure() ? field.neighborhoodPumped() + "" : "";
       setText(value);
    }

    private void aplyCheckStyle() {
        setBackground(BG_CHECKED);
        setText("M");
    }

    private void aplyExplodeStyle() {
        setBackground(BG_EXPLODED);
        setForeground(Color.WHITE);
        setText("X");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1){
            field.open();
        }else{
            field.switchChecked();
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

}
