import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ToolBar extends JToolBar
{
	private static final long serialVersionUID = 1L;
	private Container parent;
	
	public ToolBar()
	{
		
		parent = this.getParent();
		URL imageUrl = this.getClass().getClassLoader().getResource("gfx/image.png");
		ImageIcon open = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/folder.png");
		ImageIcon folder = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/save.png");
		ImageIcon save = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/reset.png");
		ImageIcon reset = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/color.png");
		ImageIcon color = new ImageIcon(imageUrl);
		
		imageUrl = this.getClass().getClassLoader().getResource("gfx/go.png");
		ImageIcon go = new ImageIcon(imageUrl);
		
		JButton openButton = new JButton(open);
		openButton.setToolTipText("Load Images");
		openButton.addActionListener(new ActionListener() 
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
		
		
		JButton folderButton = new JButton(folder);
		folderButton.setToolTipText("Load Folder");
		folderButton.addActionListener(new ActionListener() 
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
  
		
		JButton saveButton = new JButton(save);
		saveButton.setEnabled(false);
		PackerValues.getInstance().setSaveButton(saveButton);
		saveButton.setToolTipText("Save Atlas");
		saveButton.addActionListener(new ActionListener() 
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
						createDirectory( "output" );
						
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

        		//fileChooser.setAcceptAllFileFilterUsed(false);
        		fileChooser.setFileFilter(filter);
        		fileChooser.setCurrentDirectory(new File(this.getClass().getClassLoader().getResource("").getPath()));
				//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
						int width = PackerValues.getInstance().getAtlasWidth();
						int height = PackerValues.getInstance().getAtlasHeight();
						TexturePacker texturePacker = PackerValues.getInstance().getTexturePacker();
						
						SaveC saveC = new SaveC( width,height,texturePacker.getCoords() );
						saveC.saveToFileCoords( folder.getAbsolutePath() + PackerValues.getInstance().getFileName() );
						
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
  
		JButton resetButton = new JButton(reset);
		resetButton.setToolTipText("Reset settings to default");
		resetButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				PackerValues.getInstance().getTexturePacker().printCoords();
			}
		});
  
		JButton colorButton = new JButton(color);
		colorButton.setToolTipText("Choose Transparent color");
		colorButton.addActionListener(new ActionListener() 
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
  
		JButton goButton = new JButton(go);
		goButton.setEnabled(false);
		PackerValues.getInstance().setGoButton(goButton);
		goButton.setToolTipText("Generate Atlas/Spritesheet");
		goButton.addActionListener(new ActionListener() 
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
						
						Graphics g2 = PackerValues.getInstance().getBgTexture().getGraphics();
				     	g2.setColor(Color.BLUE);
				     	g2.drawRect(0, 0, PackerValues.getInstance().getAtlasWidth()-1, PackerValues.getInstance().getAtlasHeight()-1);
				     	
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
  
		
		add(openButton);
		addSeparator();
		add(folderButton);
		addSeparator();
		add(saveButton);
		addSeparator();
		add(resetButton);
		addSeparator();
		add(colorButton);
		addSeparator();
		add(goButton);
		
		
	}
	
	
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
