package data;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class boot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("Fixed size content");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = f.getContentPane();
        c.setBackground(Color.BLACK);
        JLabel label = new JLabel(new ImageIcon("datos/sprite.jpg"));
        f.add(label);
        
         // adjust to need.
        Dimension d = new Dimension(400,400);
        c.setPreferredSize(d);
        f.pack();
        f.setResizable(true);
        f.setVisible(true);
	}

}
