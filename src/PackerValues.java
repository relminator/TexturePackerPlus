import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



public class PackerValues
{
	
	public enum Formats
	{
		PNG,
		BMP,
		JPG,
	}
	
	public enum Bits
	{
		BITS32,
		BITS8, 
	}
	
	private static PackerValues instance = new PackerValues();

	private TexturePacker texturePacker = new TexturePacker();
	
	private Map<Integer,Integer> cbValues = new HashMap<Integer,Integer>(12);		
	
	private ImagePanel imagePanel;
	private MainPanel mainPanel;
	private JScrollPane scrollPane;
	private AnimatedLabel animatedLabel;
	private JButton goButton;
	private JButton saveButton;
	private JMenuItem goMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	
	private JTextField txFileName;
	private JPanel colorPanel;
	private JProgressBar progressBar;
	
	private ComboBoxSize comboBoxW;
	private ComboBoxSize comboBoxH;
	
	Graphics graphics;
	
	private BufferedImage packerTexture;
	private BufferedImage transparencyTexture;
	private BufferedImage bgTexture;
	
	private File[] selectedFiles;
	
	private String fileName = "texturepack";
	private int bgRed, bgGreen, bgBlue = 0;
	
	private boolean javaOutput = true;
	private boolean cppOutput = true;
	private boolean basicOutput = false;
	private boolean textOutput = false;
	private boolean xmlOutput = false;
	private boolean jsonOutput = false;
	
	
	private int atlasWidth = 512;
	private int atlasHeight = 512;
	private boolean rotation = false;
	private int margin = 0;
	
	private boolean bleedImages = false;
	private boolean resizeImages = false;
	private boolean debug = false;

	private boolean packIt = true;
	
	
	private Formats format = Formats.PNG;
	private Bits bits = Bits.BITS32;
	
	private PackerValues()
	{
		createTransparencyTexture();
		createBgTexture(4096, 4096);
		for( int i = 0; i < 12; i++ )
		{
			cbValues.put(2 << i, i );
		}
	}

	public static PackerValues getInstance()
	{
		return instance;
	}
	
	public void reset()
	{
		bgRed = bgGreen = bgBlue = 0;
		
		javaOutput = true;
		cppOutput = true;
		basicOutput = false;
		textOutput = false;
		xmlOutput = false;
		jsonOutput = false;
		
		
		atlasWidth = 512;
		atlasHeight = 512;
		rotation = false;
		margin = 0;
		
	}
	private void createTransparencyTexture()
	{
		
		int width = 4096;
		int height = 4096;
		transparencyTexture = new BufferedImage( width, 
									           	 height, 
									             BufferedImage.TYPE_INT_ARGB);

		
		// Pixel stuff but slower
		/*
		int[] pixels = ((DataBufferInt) bgTexture.getRaster().getDataBuffer()).getData();	
		
		int offset = 0;
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ )
			{
				int col = 128;
				if( ((x/8) + (y/8) & 1) == 0) col = 255;
				
				int c = (255 << 24) | (col << 16) | (col << 8) | (col);
				pixels[offset++] = c;
			}

		}
		*/
		
		Graphics g = transparencyTexture.getGraphics();
		
		int rectSize = 8;
		g.setColor(Color.WHITE);
		g.fillRect( 0, 0, width, height);
		    
		int rows = width/rectSize;
		int cols = height/rectSize;
		
		int flag = 0;
		g.setColor( new Color( 180,180,180, 255) );

		for( int y = 0; y < cols; y++ )
		{
			for( int x = 0; x < rows; x++ )
			{
				int sx = x * rectSize;
				int sy = y * rectSize;
				if( ((flag++) & 1) == 0)
				{	
					g.fillRect( sx, sy, rectSize, rectSize);
				}
			}
			flag++;
		}
		
	}
	
	public void createBgTexture( int width, int height )
	{
		
		bgTexture = new BufferedImage( width, 
							           height, 
							           BufferedImage.TYPE_INT_ARGB);
		
		// Pixel stuff but slower
		/*
		int[] pixels = ((DataBufferInt) bgTexture.getRaster().getDataBuffer()).getData();	
		
		int offset = 0;
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ )
			{
				int col = 128;
				if( ((x/8) + (y/8) & 1) == 0) col = 255;
				
				int c = (255 << 24) | (col << 16) | (col << 8) | (col);
				pixels[offset++] = c;
			}

		}
		*/
		
		Graphics g = bgTexture.getGraphics();
		
		int rectSize = 8;
		g.setColor(Color.WHITE);
		g.fillRect( 0, 0, width, height);
		    
		int rows = width/rectSize;
		int cols = height/rectSize;
		
		int flag = 0;
		g.setColor( new Color( 180,180,180, 255) );

		for( int y = 0; y < cols; y++ )
		{
			for( int x = 0; x < rows; x++ )
			{
				int sx = x * rectSize;
				int sy = y * rectSize;
				if( ((flag++) & 1) == 0)
				{	
					g.fillRect( sx, sy, rectSize, rectSize);
				}
			}
			flag++;
		}
	}
	
	public void addPackerTextureToBg()
	{
		
		Graphics g = bgTexture.getGraphics();
		g.drawImage( transparencyTexture, 0, 0, null );
		g.drawImage( packerTexture, 0, 0, null );

	}
	
	public int getComboBoxIndex( int value )
	{
		return cbValues.get(value);
	}
	
	public File[] getSelectedFiles()
	{
		return selectedFiles;
	}

	public String getFileName()
	{
		return fileName;
	}

	public int getBgRed()
	{
		return bgRed;
	}

	public int getBgGreen()
	{
		return bgGreen;
	}

	public int getBgBlue()
	{
		return bgBlue;
	}

	public boolean isJavaOutput()
	{
		return javaOutput;
	}

	public boolean isCppOutput()
	{
		return cppOutput;
	}

	public boolean isBasicOutput()
	{
		return basicOutput;
	}

	public boolean isTextOutput()
	{
		return textOutput;
	}

	public boolean isXmlOutput()
	{
		return xmlOutput;
	}

	public boolean isJsonOutput()
	{
		return jsonOutput;
	}

	public int getAtlasWidth()
	{
		return atlasWidth;
	}

	public int getAtlasHeight()
	{
		return atlasHeight;
	}

	public boolean isRotation()
	{
		return rotation;
	}

	public Formats getFormat()
	{
		return format;
	}

	public Bits getBits()
	{
		return bits;
	}

	public void setSelectedFiles(File[] selectedFiles)
	{
		this.selectedFiles = selectedFiles;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public void setBgRed(int bgRed)
	{
		this.bgRed = bgRed;
	}

	public void setBgGreen(int bgGreen)
	{
		this.bgGreen = bgGreen;
	}

	public void setBgBlue(int bgBlue)
	{
		this.bgBlue = bgBlue;
	}

	public void setJavaOutput(boolean javaOutput)
	{
		this.javaOutput = javaOutput;
	}

	public void setCppOutput(boolean cppOutput)
	{
		this.cppOutput = cppOutput;
	}

	public void setBasicOutput(boolean basicOutput)
	{
		this.basicOutput = basicOutput;
	}

	public void setTextOutput(boolean textOutput)
	{
		this.textOutput = textOutput;
	}

	public void setXmlOutput(boolean xmlOutput)
	{
		this.xmlOutput = xmlOutput;
	}

	public void setJsonOutput(boolean jsonOutput)
	{
		this.jsonOutput = jsonOutput;
	}

	public void setAtlasWidth(int atlasWidth)
	{
		this.atlasWidth = atlasWidth;
	}

	public void setAtlasHeight(int atlasHeight)
	{
		this.atlasHeight = atlasHeight;
	}

	public void setRotation(boolean rotation)
	{
		this.rotation = rotation;
	}

	public void setFormat(Formats format)
	{
		this.format = format;
	}

	public void setBits(Bits bits)
	{
		this.bits = bits;
	}

	
	public boolean isDebug()
	{
		return debug;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	public ImagePanel getImagePanel()
	{
		return imagePanel;
	}

	public void setImagePanel(ImagePanel imagePanel)
	{
		this.imagePanel = imagePanel;
	}

		public Graphics getGraphics()
	{
		return graphics;
	}

	public void setGraphics(Graphics graphics)
	{
		this.graphics = graphics;
	}

	public int getMargin()
	{
		return margin;
	}

	public void setMargin(int margin)
	{
		this.margin = margin;
	}

	public boolean isBleedImages()
	{
		return bleedImages;
	}

	public void setBleedImages(boolean bleedImages)
	{
		this.bleedImages = bleedImages;
	}

	public boolean isResizeImages()
	{
		return resizeImages;
	}

	public void setResizeImages(boolean resizeImages)
	{
		this.resizeImages = resizeImages;
	}

	public BufferedImage getPackerTexture()
	{
		return packerTexture;
	}

	public BufferedImage getBgTexture()
	{
		return bgTexture;
	}

	public void setPackerTexture(BufferedImage packerTexture)
	{
		this.packerTexture = packerTexture;
	}

	public MainPanel getMainPanel()
	{
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}

	public JScrollPane getScrollPane()
	{
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane)
	{
		this.scrollPane = scrollPane;
	}

	public JProgressBar getProgressBar()
	{
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar)
	{
		this.progressBar = progressBar;
	}

	public AnimatedLabel getAnimatedLabel()
	{
		return animatedLabel;
	}

	public void setAnimatedLabel(AnimatedLabel animatedLabel)
	{
		this.animatedLabel = animatedLabel;
	}

	public JButton getGoButton()
	{
		return goButton;
	}

	public void setGoButton(JButton goButton)
	{
		this.goButton = goButton;
	}

	public JButton getSaveButton()
	{
		return saveButton;
	}

	public void setSaveButton(JButton saveButton)
	{
		this.saveButton = saveButton;
	}

	public JMenuItem getGoMenuItem()
	{
		return goMenuItem;
	}

	public JMenuItem getSaveMenuItem()
	{
		return saveMenuItem;
	}

	public JMenuItem getSaveAsMenuItem()
	{
		return saveAsMenuItem;
	}

	public void setGoMenuItem(JMenuItem goMenuItem)
	{
		this.goMenuItem = goMenuItem;
	}

	public void setSaveMenuItem(JMenuItem saveMenuItem)
	{
		this.saveMenuItem = saveMenuItem;
	}

	public void setSaveAsMenuItem(JMenuItem saveAsMenuItem)
	{
		this.saveAsMenuItem = saveAsMenuItem;
	}

	public JPanel getColorPanel()
	{
		return colorPanel;
	}

	public void setColorPanel(JPanel colorPanel)
	{
		this.colorPanel = colorPanel;
	}

	public JTextField getTxFileName()
	{
		return txFileName;
	}


	public void setTxFileName(JTextField txFileName)
	{
		this.txFileName = txFileName;
	}

	public ComboBoxSize getComboBoxW()
	{
		return comboBoxW;
	}

	public ComboBoxSize getComboBoxH()
	{
		return comboBoxH;
	}

	public void setComboBoxW(ComboBoxSize comboBoxW)
	{
		this.comboBoxW = comboBoxW;
	}

	public void setComboBoxH(ComboBoxSize comboBoxH)
	{
		this.comboBoxH = comboBoxH;
	}

	public TexturePacker getTexturePacker()
	{
		return texturePacker;
	}

	public boolean isPackIt()
	{
		return packIt;
	}

	public void setPackIt(boolean packIt)
	{
		this.packIt = packIt;
	}

	
	
}
