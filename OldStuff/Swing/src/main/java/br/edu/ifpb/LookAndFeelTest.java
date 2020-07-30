package br.edu.ifpb;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class LookAndFeelTest extends JFrame implements ActionListener {
   private JRadioButton windows, metal, motif;
   private ButtonGroup bg;
   public LookAndFeelTest() {
      setTitle("Look And Feels");
      windows = new JRadioButton("Windows");
      windows.addActionListener(this);
      metal = new JRadioButton("Metal");
      metal.addActionListener(this);
      motif = new JRadioButton("Motif");
      motif.addActionListener(this);
      bg = new ButtonGroup();
      bg.add(windows);
      bg.add(metal);
      bg.add(motif);
      setLayout(new FlowLayout());
      add(windows);
      add(metal);
      add(motif);
      setSize(400, 300);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
   }
   @Override
   public void actionPerformed(ActionEvent ae) {
      String LAF;
      if(ae.getSource() == windows)
         LAF = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
      else if(ae.getSource() == metal)
         LAF = "javax.swing.plaf.metal.MetalLookAndFeel";
      else
         LAF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
      try {
         UIManager.setLookAndFeel(LAF);
         SwingUtilities.updateComponentTreeUI(this);
      } catch (Exception e) {
         System.out.println("Error setting the LAF..." + e);
      }
   }
   public static void main(String args[]) {
      new LookAndFeelTest();
      UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
      for (UIManager.LookAndFeelInfo look : looks) {
         System.out.println(look.getClassName());
      }
   }
}