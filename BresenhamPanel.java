/* Author: Brandon Mota
 * 
 * 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JPanel;

public class BresenhamPanel extends JPanel
{
	/*Three array lists are necessary. One for the grids, one for the rectangles to be filled in,
	  and one for each point the line goes through. */
        List<Shape> grid;
        List<Shape> fill;
        List<Point> points = new ArrayList<Point>();
        /*Three pointers for the start and end. The temp is used to fill in the arraylist of points*/
        Point pointStart = null;
        Point pointEnd   = null;
        Point temp = null;        
        
        int x0 = 0;
        int x1 = 0;
        int y0 = 0;
        int y1 = 0;
        
        /*You can change the size of the window from here. 'sizex' determines the width, 
          'sizey' determines the height.*/
        int sizex = 800;
        int sizey = 800;
        /*You can change the ratio size of the "pixels." Bigger number means smaller pixels.
          Bigger number will require more hardware power and will result in noticeable lag.
          For a perfect square grid, the ratio must equal to 10. 
          (e.g. (sizex + sizey) / squaresize = 10) */
        int squaresize = 80;
        

        public BresenhamPanel() 
        {
            grid = new ArrayList<>(5);
            fill = new ArrayList<>(5);
            addMouseListener(new MouseAdapter() 
            {
				@Override
                public void mousePressed(MouseEvent e) 
                {
                	pointStart = e.getPoint();
                	temp = e.getPoint();                    
                	x0 = (int) pointStart.getX();
                	y0 = (int) pointStart.getY();
                }
                public void mouseReleased(MouseEvent e) 
                { 
                	pointStart = null;
                	Point b = e.getPoint();
                	x1 = (int) b.getX();
                	y1 = (int) b.getY();
                	
                	for (Shape shape : grid) 
                	{ 
                		/*Bresenham's line algorithm starts here.*/
                		int dx, dy;
            			int stepx = 0, stepy;
            			
            			dx = x1 - x0;
            			dy = y1 - y0;
            			
            			if (dy < 0)
            			{
            				dy = -dy;
            				stepy = -1;
            			}
            			else
            			{
            				stepy = 1;
            			}
            			if (dx < 0)
            			{
            				dx = -dx;
            				stepx = -1;
            			}
            			else
            			{
            				stepx = 1;				
            			}
            			dy = 2 * dy;
            			dx = 2 * dx;
            			if ((0 <= x0) && (x0 < sizex) && (0 <= y0) && (y0 < sizey))
            			{            				
            				temp.setLocation(x0,y0);            				
            				points.add(temp.getLocation());
            			}
            			if (dx > dy)
            			{
            				int fraction = dy - (dx >> 1);
            				while (x0 != x1)
            				{
            					x0 += stepx;
            					if (fraction >= 0)
            					{
            						y0 += stepy;
            						fraction -= dx;
            					}
            					fraction += dy;
            					if ((0 <= x0) && (x0 < sizex) && (0 <= y0) && (y0 < sizey))
            					{
                    				temp.setLocation(x0,y0);            				
                    				points.add(temp.getLocation());
            					}
            				}				
            			}
            			else
            			{
            				int fraction = dx - (dy >> 1);
            				while (y0 != y1)
            				{
            					if (fraction >= 0)
            					{
            						x0 += stepx;
            						fraction -= dy;
            					}
            					y0 += stepy;
            					fraction += dx;
            					if ((0 <= x0) && (x0 < sizex) && (0 <= y0) && (y0 < sizey))
            					{
                    				temp.setLocation(x0,y0);            				
                    				points.add(temp.getLocation());
            					}
            				}
            			}
            			/*Bresenham line algorithm ends here*/            			
            			
            			/*Hashset will remove all duplicate coordinates from the arraylist*/
            			HashSet removedup = new HashSet();
            			removedup.addAll(points);
            			points.clear();
            			points.addAll(removedup);
            			
            			for (int i = 0; points.size() > i; i++) 
                        {                				
                            if (shape.contains(points.get(i))) 
                            {
                            	fill.add(shape);                        	                            
                            }
                        }             			
            			repaint();
            			
                    }
                }
            });
            
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) 
                {
                	pointEnd = e.getPoint();
                }
                public void mouseDragged(MouseEvent e) 
                {                    
                	pointEnd = e.getPoint();
                    repaint();
                }
            });
            
            /*The rectangles/"pixels" are created here along with the size of the grids*/
            int colWidth = sizex / squaresize;
            int rowHeight = sizey / squaresize;

            for (int row = 0; row < squaresize; row++) 
            {
                for (int col = 0; col < squaresize; col++) 
                {
                    grid.add(new Rectangle(colWidth * col, rowHeight * row, colWidth, rowHeight));
                }
            }

        }

        @Override
        public Dimension getPreferredSize() 
        {
        	/*This sets the size of the window*/
            return new Dimension(sizex, sizey);
        }  
        
        @Override
        protected void paintComponent(Graphics g) 
        {        	
            super.paintComponent(g);
            /*Draws the line that follows the mouse*/
            if (pointStart != null) 
            {
                g.setColor(Color.GREEN);
                g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
            }
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            /*Fills in the rectangles, or "pixels"*/
            for (Shape cell : fill) 
            {
                g2d.fill(cell);                
            }
            g2d.setColor(Color.BLACK);
            /*Colors the grid separating the rectangles/"pixels"*/
            for (Shape cell : grid) 
            {
                g2d.draw(cell);
            }            
            points.clear(); //arrraylist of points will be cleared to increase performance
        }        	
}