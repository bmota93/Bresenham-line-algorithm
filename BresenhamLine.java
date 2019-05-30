/* Author: Brandon Mota
 * 
 * This program demonstrates the usage of Bresenham's line algorithm for rasterization
 * 
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;

/*
 * Main file to create a new JFrame for rasterization
 */

public class BresenhamLine 
{
	public BresenhamLine() 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) 
                {
                	
                }

                JFrame frame = new JFrame("Bresenham Line Draw");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new BresenhamPanel());
                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);                
            }            
        });
    }
	
	public static void main(String[] args) 
    {
        new BresenhamLine();
    }
}