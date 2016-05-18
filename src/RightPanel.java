import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RightPanel extends JPanel 
{
    
    
    private static final long serialVersionUID = 1L;
    private final int DELAY = 60 * 3;
    
    public RightPanel() 
    {
		
		
		
    	ToolBar toolBar = new ToolBar();
		
    	AnimatedLabel readyLabel= new AnimatedLabel(DELAY, "Please load Images or Folders...");
    	readyLabel.setForeground(Color.RED);
    	PackerValues.getInstance().setAnimatedLabel(readyLabel);
    	
    	JTextField txFileName = new JTextField("texturepack");
    	txFileName.setBorder(BorderFactory.createLoweredBevelBorder());
    	txFileName.setToolTipText("Filename of the saved atlas.");
    	PackerValues.getInstance().setTxFileName(txFileName);
    	
    	JPanel colorPanel = new JPanel();
		colorPanel.setBackground( new Color(0,0,0,255) );
		colorPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		colorPanel.setToolTipText("Transparent background color of the saved atlas.");
		PackerValues.getInstance().setColorPanel(colorPanel);
		
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		PackerValues.getInstance().setProgressBar(progressBar);
		
		
		JRadioButton packButton = new JRadioButton("Pack", true);
		packButton.setToolTipText("Use this for sprites/tiles of different sizes.");
    	packButton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setPackIt(true);
            	}
            }           
        });
    	
		JRadioButton tileButton = new JRadioButton("Tile", true);
		tileButton.setToolTipText("Use this for sprites/tiles of the same size.");
    	tileButton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setPackIt(false);
            	}
            }           
        });
    	
		
		ButtonGroup modeGroup = new ButtonGroup();
    	modeGroup.add(packButton);
    	modeGroup.add(tileButton);
    	
		JCheckBox cbJava = new JCheckBox("Java", true);
		cbJava.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setJavaOutput( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	JCheckBox cbCpp = new JCheckBox("C/C++", true);
    	cbCpp.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setCppOutput( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	JCheckBox cbBasic = new JCheckBox("BASIC", false);
    	cbBasic.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setBasicOutput( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	JCheckBox cbText = new JCheckBox("Text", false);
    	cbText.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setTextOutput( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	JCheckBox cbXML = new JCheckBox("XML", false);
    	cbXML.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setXmlOutput( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	JCheckBox cbJson = new JCheckBox("Json", false);
    	cbJson.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setJsonOutput( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	ComboBoxSize comboBoxW = new ComboBoxSize();
    	comboBoxW.setToolTipText("Width of the atlas to pack the images.");
    	PackerValues.getInstance().setComboBoxW(comboBoxW);
    	comboBoxW.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	if( e.getStateChange() == ItemEvent.SELECTED )
            	{
            		PackerValues.getInstance().setAtlasWidth(Integer.parseInt((String)e.getItem()));
            	}
                	
            }           
        });
    	
    	ComboBoxSize comboBoxH = new ComboBoxSize();
    	comboBoxH.setToolTipText("Height of the atlas to pack the images.");
    	PackerValues.getInstance().setComboBoxH(comboBoxH);
    	comboBoxH.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	if( e.getStateChange() == ItemEvent.SELECTED )
            	{
            		PackerValues.getInstance().setAtlasHeight(Integer.parseInt((String)e.getItem()));
            	}
                	
            }           
        });
    	
    	
    	
    	JRadioButton pngRbutton = new JRadioButton("PNG", true);
    	pngRbutton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setFormat(PackerValues.Formats.PNG);
            	}
            }           
        });
    	
    	JRadioButton bmpRbutton = new JRadioButton("BMP", false);
    	bmpRbutton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setFormat(PackerValues.Formats.BMP);
            	}
            }           
         });
    	
    	JRadioButton jpgRbutton = new JRadioButton("JPG", false);
    	jpgRbutton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setFormat(PackerValues.Formats.JPG);
            	}
            }           
        });
    	
    	
    	ButtonGroup formatGroup = new ButtonGroup();
    	formatGroup.add(pngRbutton);
    	formatGroup.add(bmpRbutton);
    	formatGroup.add(jpgRbutton);
      
    	JRadioButton bit32Rbutton = new JRadioButton("32 bits", true);
    	bit32Rbutton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setBits(PackerValues.Bits.BITS32);
            	}
            }           
         });
    	
    	JRadioButton bit8Rbutton = new JRadioButton("8 bits", false);
    	bit8Rbutton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setBits(PackerValues.Bits.BITS8);
            	}
            }           
        });
    	
    	ButtonGroup bitGroup = new ButtonGroup();
    	bitGroup.add(bit32Rbutton);
    	bitGroup.add(bit8Rbutton);
    	
    	JCheckBox cbRotation = new JCheckBox("Allow Rotation for optimized packing", false);
    	cbRotation.setToolTipText("Rotates the images inside the atlas for optimized packing.");
    	cbRotation.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setRotation( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	
    	JRadioButton bleedButton = new JRadioButton("Bleed Edges", false);
    	bleedButton.setToolTipText("Fixes graphics artifacts when in filtered rendering.");
    	bleedButton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setBleedImages(true);
            		PackerValues.getInstance().setResizeImages(false);
            	}
            }           
        });
    	
    	JRadioButton resizeButton = new JRadioButton("Resize Images", false);
    	resizeButton.setToolTipText("Resizes the images to margin size.");
    	resizeButton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setResizeImages(true);
            		PackerValues.getInstance().setBleedImages(false);
            	}
            }           
        });
    	
    	JRadioButton noneButton = new JRadioButton("Default", true);
    	noneButton.setToolTipText("Don't bleed and don't resize.");
    	noneButton.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	
            	if( e.getStateChange() == 1 )
    			{
            		PackerValues.getInstance().setBleedImages(false);
            		PackerValues.getInstance().setResizeImages(false);
            	}
            }           
        });
    	
    	JCheckBox cbDebug = new JCheckBox("Output Debug Lines", false);
    	cbDebug.setToolTipText("Shows the size/margin of each image in the atlas.");
    	cbDebug.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
           	    PackerValues.getInstance().setDebug( (e.getStateChange() == 1) ? true : false );
            }           
        });
    	
    	
    	ButtonGroup fixGroup = new ButtonGroup();
    	fixGroup.add(bleedButton);
    	fixGroup.add(resizeButton);
    	fixGroup.add(noneButton);
    	
    	ComboBoxMargin cboMargin = new ComboBoxMargin();
    	cboMargin.setToolTipText("Margin between the images inside the atlas.");
    	cboMargin.addItemListener(new ItemListener() 
    	{
            public void itemStateChanged(ItemEvent e) 
            {  
            	if( e.getStateChange() == ItemEvent.SELECTED )
            	{
            		PackerValues.getInstance().setMargin(Integer.parseInt((String)e.getItem()));
            	}
                	
            }           
        });
    	
    	setPreferredSize(new Dimension(340,512));
    	setLayout(new BoxLayout(this, WIDTH));
    	
    	JPanel toolPanel = new JPanel();
    	toolPanel.add(toolBar);
    	toolPanel.add(readyLabel);
    	
    	JPanel namePanel = new JPanel();
    	namePanel.setLayout(new GridLayout(3,2));
    	namePanel.setBorder(BorderFactory.createEtchedBorder());
    	namePanel.add(new JLabel("Texture Pack FileName:"));
    	namePanel.add(txFileName);
    	namePanel.add(new JLabel("Transparent Color:"));
    	namePanel.add(colorPanel);
    	namePanel.add(new JLabel("Progress:"));
    	namePanel.add(progressBar);
    	
    	JPanel modePanel = new JPanel();
    	modePanel.setLayout(new GridLayout(1,3));
    	modePanel.setBorder(BorderFactory.createEtchedBorder());
    	modePanel.add(new JLabel("Atlas mode:"));
    	modePanel.add(packButton);
    	modePanel.add(tileButton);
    	
    	JPanel texPanel = new JPanel();
    	texPanel.setToolTipText("Texture Atlas Cooordinate format to import.");
    	texPanel.setLayout(new GridLayout(3,3));
    	texPanel.setBorder(BorderFactory.createEtchedBorder());
    	texPanel.add(new JLabel("Data Format:"));
    	texPanel.add( Box.createHorizontalGlue());
    	texPanel.add( Box.createVerticalGlue());
    	texPanel.add(cbJava);
    	texPanel.add(cbCpp);
    	texPanel.add(cbBasic);
    	texPanel.add(cbText);
    	texPanel.add(cbXML);
    	texPanel.add(cbJson);
        
    	JPanel rotPanel = new JPanel();
    	rotPanel.setLayout(new GridLayout(5,1));
    	rotPanel.setBorder(BorderFactory.createEtchedBorder());
    	rotPanel.add(cbRotation);
    	rotPanel.add(bleedButton);
    	rotPanel.add(resizeButton);
    	rotPanel.add(noneButton);
    	rotPanel.add(cbDebug);
    	
    	
    	JPanel formatPanel = new JPanel();
    	formatPanel.setToolTipText("Texture Atlas image format to import.");
    	formatPanel.setLayout(new GridLayout(1,4));
    	formatPanel.setBorder(BorderFactory.createEtchedBorder());
    	formatPanel.add(new JLabel("Format:"));
    	formatPanel.add(pngRbutton);
    	formatPanel.add(bmpRbutton);
    	formatPanel.add(jpgRbutton);
        
    	JPanel depthPanel = new JPanel();
    	depthPanel.setToolTipText("Texture Atlas bit depth to import.");
    	depthPanel.setLayout(new GridLayout(1,3));
    	depthPanel.setBorder(BorderFactory.createEtchedBorder());
    	depthPanel.add(new JLabel("Depth:"));
    	depthPanel.add(bit32Rbutton);
    	depthPanel.add(bit8Rbutton);
        
    	JPanel sizePanel = new JPanel();
    	sizePanel.setLayout(new GridLayout(3,1));
    	sizePanel.setBorder(BorderFactory.createEtchedBorder());
    	sizePanel.add(new JLabel("Image Atlas Width:"));
    	sizePanel.add(comboBoxW);
    	sizePanel.add(new JLabel("Image Atlas Height:"));
    	sizePanel.add(comboBoxH);
    	sizePanel.add(new JLabel("Margin Between Images:"));
    	sizePanel.add(cboMargin);
    	
        
    	add(toolPanel);
    	add(namePanel);
    	add(modePanel);
    	add(texPanel);
    	add(formatPanel);
    	add(depthPanel);
    	add(sizePanel);
    	add(rotPanel);
    	
    } 
    
}
