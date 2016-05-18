import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TexturePackerMain
{

	   
	private static void createAndShowUI()
	{
    
		
	    JFrame frame = new JFrame("Texture Packer Plus");
			
		PackerValues.getInstance().createBgTexture(4096, 4096);
		  
		MainPanel mainPanel = new MainPanel();
		Menu menu = new Menu();
		
		PackerValues.getInstance().setMainPanel(mainPanel);
		
		frame.getContentPane().add(mainPanel, BorderLayout.WEST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(860, 700);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(menu);
		

		frame.setVisible(true);
		
		
   }

   public static void main(String[] args) 
   {
	   	java.awt.EventQueue.invokeLater(new Runnable() 
	   	{
	   		public void run() 
	   		{
	   			createAndShowUI();
	   		}
	   	});
   }

}








