import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MainPanel extends JPanel 
{
 
   private static final long serialVersionUID = 1L;
   private JScrollPane scrollpane;
   private ImagePanel imagePanel;
   private RightPanel rightPanel;
   
   
   public MainPanel() 
   {
	  
	  
      imagePanel = new ImagePanel();
      imagePanel.setImage(PackerValues.getInstance().getBgTexture());
      
      PackerValues.getInstance().setImagePanel(imagePanel);
      
      
      scrollpane = new JScrollPane(imagePanel);
      scrollpane.setHorizontalScrollBarPolicy(
    		   JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollpane.setVerticalScrollBarPolicy(
    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
       
      PackerValues.getInstance().setGraphics(imagePanel.getGraphics());
      rightPanel = new RightPanel();
      
      setLayout(new BorderLayout());
      	
      scrollpane.setPreferredSize(new Dimension(512,512));
      PackerValues.getInstance().setScrollPane(scrollpane);
		
      
      add(scrollpane, BorderLayout.CENTER);
      add(rightPanel, BorderLayout.EAST);
      
            
    }


	public ImagePanel getImagePanel()
	{
		return imagePanel;
	}

}