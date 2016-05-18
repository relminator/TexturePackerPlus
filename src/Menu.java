import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	private JMenu file = new JMenu("File");
	private JMenu convert = new JMenu("Convert");
	private JMenu settings = new JMenu("Settings");
	private JMenu help = new JMenu("Help");
	private Container parent;
	
	public Menu()
	{
		
		parent = this.getParent();
	
		URL imageUrl = this.getClass().getClassLoader().getResource("gfx/exit.png");
		ImageIcon exit = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/image.png");
		ImageIcon open = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/folder.png");
		ImageIcon folder = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/save.png");
		ImageIcon save = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/reset.png");
		ImageIcon reset = new ImageIcon(imageUrl);
		
		// --------file----------------
		file.setMnemonic(KeyEvent.VK_F);
  
		JMenuItem openMenuItem = new JMenuItem("Load Images", open);
		openMenuItem.setMnemonic(KeyEvent.VK_L);
		openMenuItem.setToolTipText("Load mutiple images");
		openMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				JFileChooser fileChooser = new JFileChooser();
				
				FileFilter filter = new FileNameExtensionFilter( "Image files(png, bmp, jpg, gif)", 
																 "png",
																 "bmp",
																 "gif",
																 "jpg", 
																 "jpeg");
				fileChooser.addChoosableFileFilter(filter);
				fileChooser.setCurrentDirectory(new File(this.getClass().getClassLoader().getResource("").getPath()));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setMultiSelectionEnabled(true);
				int returnValue = fileChooser.showOpenDialog(parent);
            	if (returnValue == JFileChooser.APPROVE_OPTION) 
            	{
            		PackerValues.getInstance().getProgressBar().setValue(0);
    				
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    PackerValues.getInstance().setSelectedFiles(selectedFiles);
                    PackerValues.getInstance().getAnimatedLabel().setMyText("Ready to pack textures...");
                    PackerValues.getInstance().getGoButton().setEnabled(true);
                    PackerValues.getInstance().getGoMenuItem().setEnabled(true);
                }
            	else
            	{
            		
            	}
			}
		});
  
	
		JMenuItem folderMenuItem = new JMenuItem("Load Folder", folder);
		folderMenuItem.setMnemonic(KeyEvent.VK_L);
		folderMenuItem.setToolTipText("Load all images inside a folder");
		folderMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				JFileChooser fileChooser = new JFileChooser();
				
				fileChooser.setCurrentDirectory(new File(this.getClass().getClassLoader().getResource(".").getPath()));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileChooser.showOpenDialog(parent);
            	if (returnValue == JFileChooser.APPROVE_OPTION) 
            	{
            		PackerValues.getInstance().getProgressBar().setValue(0);
    				
            		FilenameFilter filter = new FilenameFilter() 
            		{
            		    public boolean accept(File dir, String name) 
            		    {
            		        return ( name.endsWith(".png") || 
            		        		 name.endsWith(".bmp") ||
            		        		 name.endsWith(".gif") ||
            		        		 name.endsWith(".jpg") ||
            		        		 name.endsWith(".jpeg") );
            		    }
            		};
            		
                    File folder = fileChooser.getSelectedFile();
                    File[] selectedFiles = folder.listFiles(filter);
                    PackerValues.getInstance().setSelectedFiles(selectedFiles);
                    PackerValues.getInstance().getAnimatedLabel().setMyText("Ready to pack textures...");
                    PackerValues.getInstance().getGoButton().setEnabled(true);
                    PackerValues.getInstance().getGoMenuItem().setEnabled(true);
                }
            	else
            	{
            		
            	}
			}
			
		});
  
	
		JMenuItem resetMenuItem = new JMenuItem("Reset", reset);
		resetMenuItem.setMnemonic(KeyEvent.VK_R);
		resetMenuItem.setToolTipText("Reset settings to default");
		resetMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
			}
		});
  
		
		JMenuItem saveMenuItem = new JMenuItem("Save", save);
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveMenuItem.setEnabled(false);
		saveMenuItem.setToolTipText("Save Atlas");
		PackerValues.getInstance().setSaveMenuItem(saveMenuItem);
		saveMenuItem.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				
				int n = JOptionPane.showConfirmDialog( parent, 
						   "Would you like to save the spritesheet?",
						   "Save Atlas",
						   JOptionPane.YES_NO_OPTION );
					if (n == JOptionPane.YES_OPTION) 
					{
						PackerValues.getInstance().setFileName(
								PackerValues.getInstance().getTxFileName().getText() );
						File folder = new File( "output/" + PackerValues.getInstance().getFileName() +".png" );
						try
						{
							ImageIO.write(PackerValues.getInstance().getPackerTexture(), "png", folder);
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						File folderIdx = new File( "output/" + PackerValues.getInstance().getFileName() +"Index.png" );
						try
						{
							PackerValues.getInstance().getTexturePacker().createTextureIndex();
							ImageIO.write(PackerValues.getInstance().getTexturePacker().getTextureIndex(), "png", folderIdx);
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						int width = PackerValues.getInstance().getAtlasWidth();
						int height = PackerValues.getInstance().getAtlasHeight();
						TexturePacker texturePacker = PackerValues.getInstance().getTexturePacker();
						
						SaveC saveC = new SaveC( width,height,texturePacker.getCoords() );
						saveC.saveToFileCoords( "output/" + PackerValues.getInstance().getFileName() );
						
						PackerValues.getInstance().getAnimatedLabel().setMyText("Atlas saved. Ready for another set of images...");
						
						JOptionPane.showMessageDialog( parent, 
								   "SpriteSheet Saved",
								   "Please load another set of images.",
								   JOptionPane.INFORMATION_MESSAGE );
					
					}
					
				
				
				/*
				JFileChooser fileChooser = new JFileChooser();
				
				FileFilter filter = new FileNameExtensionFilter( "Image files(png, bmp, jpg, tga, gif)", 
																 "png",
																 "bmp",
																 "gif",
																 "jpg", 
																 "jpeg");

        		fileChooser.setFileFilter(filter);
        		fileChooser.setCurrentDirectory(new File(this.getClass().getClassLoader().getResource("").getPath()));
				int returnValue = fileChooser.showSaveDialog(parent);
            	if (returnValue == JFileChooser.APPROVE_OPTION) 
            	{
            		
            	    File folder = fileChooser.getSelectedFile();
            	    
            	    String output = PackerValues.getInstance().getFileName()+".png";
            	    File outputfile = new File(folder.getAbsolutePath() + output);
            	    System.out.println(outputfile);
            	    try
					{
						ImageIO.write(PackerValues.getInstance().getPackerTexture(), "png", folder);
						PackerValues.getInstance().getAnimatedLabel().setMyText("Atlas saved. Ready for another set of images...");
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            	else
            	{
            		
            	}
            	*/
			}
			
			
			
		});
  
	
		JMenuItem saveAsMenuItem = new JMenuItem("Save as...", save);
		saveAsMenuItem.setMnemonic(KeyEvent.VK_A);
		saveAsMenuItem.setToolTipText("Save Atlas in a different folder");
		saveAsMenuItem.setEnabled(false);
		PackerValues.getInstance().setSaveAsMenuItem(saveAsMenuItem);
		saveAsMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				
				JFileChooser fileChooser = new JFileChooser();
				
				FileFilter f = new FileNameExtensionFilter( "JPEG", 
					    									"jpeg" );
				fileChooser.addChoosableFileFilter(f);
				
				f = new FileNameExtensionFilter( "JPG", 
												  "jpg" );
		
				f = new FileNameExtensionFilter( "PNG", 
					    						 "png" );
				fileChooser.addChoosableFileFilter(f);
			
				fileChooser.setCurrentDirectory(new File(this.getClass().getClassLoader().getResource("").getPath()));
				fileChooser.setSelectedFile(new File("texturepack.png"));
				int returnValue = fileChooser.showSaveDialog(parent);
            	if (returnValue == JFileChooser.APPROVE_OPTION) 
            	{
            		
            		FileFilter fileFilter = fileChooser.getFileFilter(); 
            	    File filename = fileChooser.getSelectedFile();
            	    //if (filename.getName().toLowerCase().endsWith(fileFilter.getDescription().toLowerCase()) )
            	    //{
            	    	//File filenameToSave = fileChooser.getSelectedFile().
            	    //}
            	    //else
            	    //{
            	    	
            	    //}
            	    try
					{
						ImageIO.write(PackerValues.getInstance().getPackerTexture(),fileFilter.getDescription().toLowerCase(), filename);
						PackerValues.getInstance().getAnimatedLabel().setMyText("Atlas saved. Ready for another set of images...");					
					} 
            	    catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            	else
            	{
            		
            	}
			

			}
		});
  
	
		JMenuItem exitMenuItem = new JMenuItem("Exit", exit);
		exitMenuItem.setMnemonic(KeyEvent.VK_X);
		exitMenuItem.setToolTipText("Exit application");
		exitMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				int n = JOptionPane.showConfirmDialog( parent, 
													   "Would you like to exit the application?",
													   "Exit",
													   JOptionPane.YES_NO_OPTION );
				if (n == JOptionPane.YES_OPTION) System.exit(0);
			}
		});
  
	
		file.add(openMenuItem);
		file.add(folderMenuItem);
		file.addSeparator();
		file.add(saveMenuItem);
		file.add(saveAsMenuItem);
		file.addSeparator();
		file.add(resetMenuItem);
		file.addSeparator();
		file.add(exitMenuItem);
		
		// --------Convert----------------
		
		convert.setMnemonic(KeyEvent.VK_C);
		  
		imageUrl = this.getClass().getClassLoader().getResource("gfx/color.png");
		ImageIcon colors = new ImageIcon(imageUrl);
		
		JMenuItem colorsMenuItem = new JMenuItem("Transparent Color", colors);
		colorsMenuItem.setMnemonic(KeyEvent.VK_F);
		colorsMenuItem.setToolTipText("Choose Transparent color");
		colorsMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				Color color = JColorChooser.showDialog(parent, "Choose Transparent Color",
						   Color.white);

				if( color != null )
				{
					PackerValues.getInstance().setBgRed(color.getRed());
					PackerValues.getInstance().setBgGreen(color.getGreen());
					PackerValues.getInstance().setBgBlue(color.getBlue());
					PackerValues.getInstance().getColorPanel().setBackground(color);
				}
			}
		});
  
		imageUrl = this.getClass().getClassLoader().getResource("gfx/go.png");
		ImageIcon go = new ImageIcon(imageUrl);
		
		JMenuItem goMenuItem = new JMenuItem("Generate Spritesheet", go);
		goMenuItem.setMnemonic(KeyEvent.VK_I);
		goMenuItem.setToolTipText("Generate Atlas/Spritesheet");
		goMenuItem.setEnabled(false);
		PackerValues.getInstance().setGoMenuItem(goMenuItem);
		goMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				int n = JOptionPane.showConfirmDialog( parent, 
						   "Would you like to generate the spritesheet?",
						   "Generate Atlas",
						   JOptionPane.YES_NO_OPTION );
					if (n == JOptionPane.YES_OPTION) 
					{
						PackerValues.getInstance().getAnimatedLabel().setMyText("Packing textures...");
						TexturePacker texturePacker = PackerValues.getInstance().getTexturePacker();
						boolean success = texturePacker.generateAtlas( PackerValues.getInstance().getSelectedFiles(), 
								PackerValues.getInstance().getAtlasWidth(), PackerValues.getInstance().getAtlasHeight(),
								PackerValues.getInstance().getMargin(),
								PackerValues.getInstance().getBgRed(), PackerValues.getInstance().getBgGreen(), PackerValues.getInstance().getBgBlue(),
								PackerValues.getInstance().isRotation(),
								PackerValues.getInstance().isPackIt() );
						PackerValues.getInstance().setPackerTexture(texturePacker.getTexture());
						PackerValues.getInstance().addPackerTextureToBg();
						Graphics g = PackerValues.getInstance().getImagePanel().getGraphics();
						g.drawImage( PackerValues.getInstance().getBgTexture(),0,0,null );
						
						PackerValues.getInstance().getAnimatedLabel().setMyText("Packing done! Please save the atlas...");
						
						PackerValues.getInstance().getSaveButton().setEnabled(true);
						PackerValues.getInstance().getSaveMenuItem().setEnabled(true);
						PackerValues.getInstance().getSaveAsMenuItem().setEnabled(true);
						PackerValues.getInstance().getComboBoxW().setSelectedIndex(
								PackerValues.getInstance().getComboBoxIndex(
								PackerValues.getInstance().getAtlasWidth() ) );
						PackerValues.getInstance().getComboBoxH().setSelectedIndex(
								PackerValues.getInstance().getComboBoxIndex(
								PackerValues.getInstance().getAtlasHeight() ) );
						
						if( success )
						{
							JOptionPane.showMessageDialog( parent, 
									   "SpriteSheet Done",
									   "Woot!",
									   JOptionPane.INFORMATION_MESSAGE );
						}
						else
						{
							JOptionPane.showMessageDialog( parent, 
									   "Atlas size too small!\n"
									   + "Please adjust the Width and the Height",
									   "ERROR!",
									   JOptionPane.ERROR_MESSAGE );
						}
					}
			
			
			}
		});
  
		convert.add(colorsMenuItem);
		convert.add(goMenuItem);
	
		
		// --------Settings----------------
		
		settings.setMnemonic(KeyEvent.VK_S);
		  
		imageUrl = this.getClass().getClassLoader().getResource("gfx/laf.png");
		ImageIcon laf = new ImageIcon(imageUrl);
		
		JMenu lafMenuItem = new JMenu("Change Theme");
		lafMenuItem.setMnemonic(KeyEvent.VK_C);
		lafMenuItem.setToolTipText("Changle the overall color scheme of the texture packer");
		
		String[] cbmiItems = { "Sea Glass", "Blue", "Dark", "Metal" };
		JMenuItem[] subItems = new JMenuItem[cbmiItems.length];
		
		for( int i = 0; i < cbmiItems.length; i++ )
		{
			subItems[i] = new JMenuItem(cbmiItems[i], laf);
			lafMenuItem.add(subItems[i]);
		}
		
		subItems[0].addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				
			}
		});
		
		subItems[1].addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				
			}
		});
		
		subItems[2].addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				
			}
		});
		
		subItems[3].addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
			
			}
		});
		
		
		settings.add(lafMenuItem);
		
		
		// --------help----------------
		
		help.setMnemonic(KeyEvent.VK_H);
		  
		imageUrl = this.getClass().getClassLoader().getResource("gfx/about.png");
		ImageIcon about = new ImageIcon(imageUrl);
		
		JMenuItem aboutMenuItem = new JMenuItem("About...", about);
		aboutMenuItem.setMnemonic(KeyEvent.VK_A);
		aboutMenuItem.setToolTipText("Open");
		aboutMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				JOptionPane.showMessageDialog(parent,
					    "Texture Atlas Generator \n"+
					    "\n"+
					    "Richard Eric M. Lope\n"+
					    "http://rel.phatcode.net",
					    "About",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		help.add(aboutMenuItem);
		
		
		// --------main----------------
		add(file);
		add(Box.createHorizontalStrut(30));
		add(convert);
		add(Box.createHorizontalStrut(30));
		add(settings);
		add(Box.createHorizontalGlue());
		add(help);
		    	
	}

	/*
	@Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }	
	*/
	
	
	private void createDirectory( String directoryName )
	{
	  File theDir = new File(directoryName);

	  // if the directory does not exist, create it
	  if (!theDir.exists())
	  {
	    System.out.println("creating directory: " + directoryName);
	    theDir.mkdir();
	  }
	}

}

