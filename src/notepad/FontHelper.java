/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontHelper extends JDialog implements ListSelectionListener {

    private static final long serialVersionUID = 1L;
    JPanel pan1, pan2, pan3;
    JLabel fontLabel, sizeLabel, typeLabel, previewLabel;
    JTextField label, fontText, sizeText, typeText;
    JList fontList, typeList, sizeList;
    JScrollPane fontScroll, typeScroll, sizeScroll;
    JButton ok, cancel;
    GridBagLayout gbl;
    GridBagConstraints gbc;

    public FontHelper() {
        setTitle("Choose Font");
        setSize(300, 400);
        setResizable(false);
        gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        sizeLabel = new JLabel("Fonts");
        getContentPane().add(sizeLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        typeLabel = new JLabel("Size");
        getContentPane().add(typeLabel, gbc);
        
         gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        typeLabel = new JLabel("Font-style");
        getContentPane().add(typeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fontText = new JTextField("Arial", 12);
        getContentPane().add(fontText, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        sizeText = new JTextField("18", 4);
        getContentPane().add(sizeText, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        typeText = new JTextField("Reguler", 6);
        getContentPane().add(typeText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList = new JList(fonts);
        fontList.setFixedCellWidth(110);
        fontList.addListSelectionListener(this);
        fontScroll = new JScrollPane(fontList);
        getContentPane().add(fontScroll, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] size = {"8", "10", "12", "14", "18", "20", "22", "24", "26", "28", "30"};
        sizeList = new JList(size);
        sizeList.setFixedCellWidth(30);
        sizeList.addListSelectionListener(this);
        sizeScroll = new JScrollPane(sizeList);
        getContentPane().add(sizeScroll, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] type = {"Reguler", "Bold", "Italic", "Bold Italic"};
        typeList = new JList(type);
        typeList.setFixedCellWidth(60);
        typeList.setSelectedIndex(0);
        typeList.addListSelectionListener(this);
        typeScroll = new JScrollPane(typeList);
        getContentPane().add(typeScroll, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        pan1 = new JPanel();
        pan1.setLayout(new FlowLayout());
        previewLabel = new JLabel("Preview:");
        pan1.add(previewLabel);
        getContentPane().add(pan1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());
        label = new JTextField("AaBaCcDdEeFfGgHhIiJj");
        label.setEditable(false);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        pan2.add(label);
        getContentPane().add(pan2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        pan3 = new JPanel();
        pan3.setLayout(new FlowLayout());
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");
        pan3.add(ok);
        pan3.add(cancel);
        getContentPane().add(pan3, gbc);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    try{
      if(e.getSource()== fontList){
       Font f1 =  new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue()))); 
       fontText.setText(String.valueOf(fontList.getSelectedValue()));
       label.setFont(f1);
      }
      else if(e.getSource()== sizeList){
         Font f2 =  new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue()))); 
       sizeText.setText(String.valueOf(sizeList.getSelectedValue()));
       label.setFont(f2);  
      }
      else{
          Font f3 =  new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue()))); 
       typeText.setText(String.valueOf(typeList.getSelectedValue()));
       label.setFont(f3); 
      }
    } catch(Exception ee){
        
    }   

    }

    public Font font() {
        Font font = new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
        return font;
    }

    public JButton getok() {
        return ok;
    }

    public JButton getCancel() {
        return cancel;
    }
}
