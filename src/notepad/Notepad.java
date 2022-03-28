/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class Notepad extends JFrame {
    
     JTextArea mainarea;
    private static final long serialVersionUID = 1L;
    JMenuBar mbar;
    JMenu mnuFile, mnuEdit, mnuFormat, mnuHelp;
    JMenuItem itmNew, itmOpen, itmSave, itmSaveas, itmExit, itmCut, itmCopy, itmPaste, itmUndo, itmDlt, itmFontColor, itmFind, itmReplace, itmFont;
    JCheckBoxMenuItem wordWrap;
    String filename;
    JFileChooser jc;
    String filecontent;
    UndoManager undo;
    UndoAction undoAction;
    RedoAction redoAction;
    FontHelper font;

    public Notepad() {
        initComponent();
        itmSave.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
            
        });
        
        itmSaveas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveAs();
            }
        });
        
        itmOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        
        itmNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_open();
            }
        });
        
        itmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        itmCut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.cut();
            }
        });
        itmCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.copy();
            }
        });
        
        itmPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.paste();
            }
        });
        
        itmFontColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color();
            }
        });
        
        itmFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font.setVisible(true);
            }
        });
        
         font.getok().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.setFont(font.font());
                font.setVisible(false);
            }
        });
         
          font.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                font.setVisible(false);
            }
        });
        
        mainarea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
                undoAction.update();
                redoAction.update();
            }
        });
        mainarea.setLineWrap(true);
        mainarea.setWrapStyleWord(true);
        wordWrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wordWrap.isSelected()) {
                    mainarea.setWrapStyleWord(true);
                } else {
                    mainarea.setWrapStyleWord(false);                    
                }
            }
        });
    }
    
    private void initComponent() {
        font = new FontHelper();
        undo = new UndoManager();
        ImageIcon UndoIcon = new ImageIcon(getClass().getResource("/img/Undo.gif"));
        ImageIcon RedoIcon = new ImageIcon(getClass().getResource("/img/Redo.gif"));
        undoAction = new UndoAction(UndoIcon);
        redoAction = new RedoAction(RedoIcon);
        jc = new JFileChooser(".");
        mainarea = new JTextArea();
        getContentPane().add(mainarea);
        getContentPane().add(new JScrollPane(mainarea), BorderLayout.CENTER);
        setTitle("Untitle notepad");
        setSize(800, 600);

        //menubar
        mbar = new JMenuBar();
        //menu
        mnuFile = new JMenu("File");
        mnuEdit = new JMenu("Edit");
        mnuFormat = new JMenu("Format");
        mnuHelp = new JMenu("Help");
        //add icon
        ImageIcon newIcon = new ImageIcon(getClass().getResource("/img/Create.png"));
        ImageIcon openIcon = new ImageIcon(getClass().getResource("/img/ok.gif"));
        ImageIcon saveIcon = new ImageIcon(getClass().getResource("/img/save.gif"));
        ImageIcon ExitIcon = new ImageIcon(getClass().getResource("/img/Exit.gif"));
        ImageIcon CutIcon = new ImageIcon(getClass().getResource("/img/Cut.png"));
        ImageIcon CopyIcon = new ImageIcon(getClass().getResource("/img/Copy.gif"));
        ImageIcon PasteIcon = new ImageIcon(getClass().getResource("/img/Paste.gif"));
        ImageIcon DltIcon = new ImageIcon(getClass().getResource("/img/Delete.gif"));
        ImageIcon FndIcon = new ImageIcon(getClass().getResource("/img/Find.gif"));
        ImageIcon RplcIcon = new ImageIcon(getClass().getResource("/img/Refresh.gif"));
        //menu item
        itmNew = new JMenuItem("New", newIcon);
        itmOpen = new JMenuItem("Open", openIcon);
        itmSave = new JMenuItem("Save", saveIcon);
        itmSaveas = new JMenuItem("Saveas", saveIcon);
        itmExit = new JMenuItem("Exit", ExitIcon);
        
        itmCut = new JMenuItem("Cut", CutIcon);
        itmCopy = new JMenuItem("Copy", CopyIcon);
        itmPaste = new JMenuItem("Paste", PasteIcon);
        itmDlt = new JMenuItem("Delete", DltIcon);
        wordWrap = new JCheckBoxMenuItem("Word Wrap");
        itmFontColor = new JMenuItem("FontColor");
        itmFind = new JMenuItem("Find", FndIcon);
        itmReplace = new JMenuItem("Replace", RplcIcon);
        itmFont = new JMenuItem("Font");
        //addig shorting
        itmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        itmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        itmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        
        itmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        itmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        //add menu item
        //add item to file
        mnuFile.add(itmNew);
        mnuFile.add(itmOpen);
        mnuFile.add(itmSave);
        mnuFile.add(itmSaveas);
        mnuFile.addSeparator();
        mnuFile.add(itmExit);

        //add item to edit menu
        mnuEdit.add(undoAction);
        mnuEdit.add(redoAction);
        mnuEdit.add(itmCut);
        mnuEdit.add(itmCopy);
        mnuEdit.add(itmPaste);
        mnuEdit.add(itmFind);
        mnuEdit.add(itmReplace);
        mnuEdit.add(itmDlt);
        
        mnuEdit.addSeparator();

        //add item to format
        mnuFormat.add(wordWrap);
        mnuFormat.add(itmFontColor);
        mnuFormat.add(itmFont);
        // add menu to menubar
        mbar.add(mnuFile);
        mbar.add(mnuEdit);
        mbar.add(mnuFormat);
        mbar.add(mnuHelp);
        setJMenuBar(mbar);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
    }
    
    private void save() {
        PrintWriter fout = null;
        int retval = -1;
        try {
            if (filename == null) {
                SaveAs();
            } else {
                fout = new PrintWriter(new FileWriter(filename));
                String s = mainarea.getText();
                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                while (st.hasMoreElements()) {
                    fout.println(st.nextToken());
                }
                JOptionPane.showMessageDialog(rootPane, "File save");
                filecontent = mainarea.getText();
            }
        } catch (IOException e) {
            
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
        
    }
    
    private void SaveAs() {
        PrintWriter fout = null;
        int retval = -1;
        try {
            retval = jc.showSaveDialog(this);
            
            if (retval == JFileChooser.APPROVE_OPTION) {
                fout = new PrintWriter(new FileWriter(jc.getSelectedFile()));
            }
            
            String s = mainarea.getText();
            StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
            while (st.hasMoreElements()) {
                fout.println(st.nextToken());
            }
            JOptionPane.showMessageDialog(rootPane, "File save");
            filecontent = mainarea.getText();
            filename = jc.getSelectedFile().getName();
            setTitle(filename = jc.getSelectedFile().getName());
        } catch (IOException e) {
            
        }
    }
    
    private void open() {
        try {
            int retval = jc.showOpenDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                mainarea.setText(null);
                Reader in = new FileReader(jc.getSelectedFile());
                char[] buff = new char[10000];
                int inc;
                while ((inc = in.read(buff, 0, buff.length)) != -1) {
                    mainarea.append(new String(buff, 0, buff.length));
                }
                filename = jc.getSelectedFile().getName();
                setTitle(filename = jc.getSelectedFile().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void new_open() {
        if (!mainarea.getText().equals("") && !mainarea.getText().equals(filecontent)) {
            if (filename == null) {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you Want to Save the Changes?");
                if (option == 0) {
                    save();
                    clear();
                    
                }
            } else {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you WAnt to Save the Changes?");
                if (option == 0) {
                    save();
                    clear();
                } else if (option == 2) {
                    clear();
                }
            }
        } else {
            clear();
        }
    }
    
    private void clear() {
        mainarea.setText(null);
        setTitle("Untitle Notepad");
        filename = null;
        filecontent = null;
    }

    private void color() {
        Color c = JColorChooser.showDialog(rootPane, "Choose Font Color", Color.yellow);
        mainarea.setForeground(c);
        
    }
    
    class UndoAction extends AbstractAction {
        
        public UndoAction(ImageIcon UndoIcon) {
            super("Undo", UndoIcon);
            setEnabled(false);
        }
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                ex.printStackTrace();
            }
            update();
            redoAction.update();
        }
        
        protected void update() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, "Undo");
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
        
    }
    
    class RedoAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        public RedoAction(ImageIcon RedoIcon) {
            super("Redo", RedoIcon);
            setEnabled(false);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                ex.printStackTrace();
            }
            update();
            undoAction.update();
        }
        
        protected void update() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, "Redo");
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
        
    }
    
    public static void main(String[] args) {
        Notepad nd = new Notepad();
    }

   
    
}
