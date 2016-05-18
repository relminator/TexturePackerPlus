import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImagePanel extends JPanel 
{
   private static final long serialVersionUID = 1L;
   private BufferedImage image;
   
   public ImagePanel() 
   {
   }
   
   public void loadImage( String filename )
   {
   	
	   	URL imageUrl = this.getClass().getClassLoader().getResource(filename);
	   		
	   	try 
	   	{                
	          image = ImageIO.read(imageUrl);
	    } 
	   	catch (IOException ex) 
	   	{
	            // handle exception...
	    }
	   	
	    setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

   }
   
   @Override
   protected void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, null);
   }

	public BufferedImage getImage()
	{
		return image;
	}
	
	public void setImage(BufferedImage image)
	{
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		this.image = image;
	}
   
}
