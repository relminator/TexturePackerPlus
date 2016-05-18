import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TexturePacker
{
	private BufferedImage texture;
	private BufferedImage textureIndex;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private Node root = new Node();
	private List<Coord> coords = new ArrayList<Coord>();
	
	private float progressPercentage = 0;
	
	public TexturePacker()
	{
		
	}
	
	
	public boolean generateAtlas( String[] files, 
							   int width, int height, 
							   int margin,
							   int redTrans, int greenTrans, int blueTrans,
							   boolean rotate, boolean packIt )
	{
		reset();
		loadImages(files, redTrans, greenTrans, blueTrans);
		if( packIt )
		{
			return packImages( width, height, 
					   margin,
					   redTrans, greenTrans, blueTrans,
					   rotate );
	     	
		}
		else
		{
			return tileImages( width, height, 
					   margin,
					   redTrans, greenTrans, blueTrans );
		}
		
		 
	}
	
	public boolean generateAtlas( File[] files, int width, int height, 
							   int margin,
							   int redTrans, int greenTrans, int blueTrans,
							   boolean rotate, boolean packIt )
	{
		reset();
		loadImages(files, redTrans, greenTrans, blueTrans);
		
		if( packIt )
		{
			return packImages( width, height, 
					   margin,
					   redTrans, greenTrans, blueTrans,
					   rotate );
		}
		else
		{
			return tileImages( width, height, 
					   margin,
					   redTrans, greenTrans, blueTrans );
		} 	
	}
	
	private boolean packImages( int width, int height, 
							    int margin,
							    int redTrans, int greenTrans, int blueTrans,
							    boolean rotate)
	{
		
		/*Arrays.sort(files, new Comparator<File>() 
				{
		            @Override
		            public int compare(File o1, File o2) 
		            {
		                String n1 = o1.getName();
		                String n2 = o2.getName();
		                return n1.compareTo(n2);
		            }
	            });
			
			for( int i = 0; i < files.length; i++ )
			{
				System.out.println(files[i].getName());
			}
			*/
			
			
		// add margins
		if( margin < 0 ) margin = 0;
		for( int i = 0; i < sprites.size(); i++ )
		{
			if(PackerValues.getInstance().isBleedImages())
			{
				sprites.get(i).addMarginAndBleed(margin);
			}
			else if( PackerValues.getInstance().isResizeImages() )
			{
				sprites.get(i).addMarginAndResize(margin);
			}
			else
			{
				sprites.get(i).addMargin(margin);
			}
		}
				
		SortImages();
		
		int w = width;
		int h = height;
		
		// get smallest sprite dimensions
		for( int i = 0; i < sprites.size(); i++ )
		{
			if( sprites.get(i).getWidth() > w ) w = nextPowerOf2(sprites.get(i).getWidth());
			if( sprites.get(i).getHeight() > h ) h = nextPowerOf2(sprites.get(i).getHeight());
		}
		
		int spriteCount = 0;
		boolean invalid = true;
		while( invalid  || (spriteCount < sprites.size()) )
		{
			createTexture(w, h);
		
			int size = sprites.size();
			for( int i = 0; i < size; i++ )
			{
				boolean success = insertImages(i, rotate );
				progressPercentage = i/(float)(size-1);
				if( !success )
				{
					invalid = true; 	
				}
				else
				{
					invalid = false;
					spriteCount++;
					PackerValues.getInstance().getProgressBar().setValue((int)(progressPercentage*100));
					PackerValues.getInstance().getProgressBar().paint(PackerValues.getInstance().getProgressBar().getGraphics());
				}
			}
			
			if( invalid )
			{
				spriteCount = 0;
				root = new Node();
				if( w <= h)
				{
					w = nextPowerOf2( w + 1 );
				}
				else
				{
					h = nextPowerOf2( h + 1 );
				}
			}
			
		}

		SortImagesByIndex();
 		
		PackerValues.getInstance().setAtlasWidth(w);
		PackerValues.getInstance().setAtlasHeight(h);
		
		
     	if( spriteCount < sprites.size() )
     	{
     		return false;
     	}
     	else
     	{
     		return true;
     	}
	
	}

	
	private boolean tileImages( int width, int height, 
		    int margin,
		    int redTrans, int greenTrans, int blueTrans )
	{
		
		if( margin < 0 ) margin = 0;
		for( int i = 0; i < sprites.size(); i++ )
		{
			if(PackerValues.getInstance().isBleedImages())
			{
				sprites.get(i).addMarginAndBleed(margin);
			}
			else if( PackerValues.getInstance().isResizeImages() )
			{
				sprites.get(i).addMarginAndResize(margin);
			}
			else
			{
				sprites.get(i).addMargin(margin);
			}
		}
				
		SortImages();
		
		int w = width;
		int h = height;
		
		// get smallest sprite dimensions
		for( int i = 0; i < sprites.size(); i++ )
		{
			if( sprites.get(i).getWidth() > w ) w = nextPowerOf2(sprites.get(i).getWidth());
			if( sprites.get(i).getHeight() > h ) h = nextPowerOf2(sprites.get(i).getHeight());
		}
		
		int size = sprites.size();
		
		int tileW = sprites.get(0).getWidth();
		int tileH = sprites.get(0).getHeight();
			
		int rows = w / tileW;
		int cols = h / tileH;
		
		
		int spriteCount = 0;
		boolean invalid = true;
		
		
		w--;
		h--;
		while( invalid )
		{
			if( w <= h)
			{
				w = nextPowerOf2( w + 1 );
			}
			else
			{
				h = nextPowerOf2( h + 1 );
			} 
			
			rows = h / tileH;
			cols = w / tileW;
			
			invalid = (rows * cols) < size;
			
		}
		
		
		
		createTexture(w, h);
		
		for( int i = 0; i < size; i++ )
		{
			Sprite img = sprites.get(i);
		    
			int x = (i % cols) * tileW;
			int y = (i / cols) * tileH;
			
			img.setX(x);
	     	img.setY(y);
	     	
	     	Graphics g = texture.getGraphics();
	     	g.drawImage(img.getImage(), x, y, null);

	     	if( PackerValues.getInstance().isDebug() )
	     	{
		     	g.setColor(Color.BLUE);
		     	g.drawRect(x, y, img.getWidth(), img.getHeight());
		     	g.setColor(Color.MAGENTA);
		     	if(PackerValues.getInstance().isResizeImages())
		     	{
		     		g.drawRect(x, y, img.getOrigWidth() , img.getOrigHeight());
			    }
		     	else
		     	{
		     		g.drawRect(x + img.getMargin(), y  + img.getMargin(), img.getOrigWidth() , img.getOrigHeight());
		     	}
	     	}
	     	
	     	coords.add(new Coord( x + img.getMargin(), 
	     						  y + img.getMargin(), 
	     						  img.getOrigWidth() , 
	     						  img.getOrigHeight(),
	     						  img.isRotated(),
	     						  img.getIndex() ));

	     	spriteCount++;
		}
		
		
		PackerValues.getInstance().setAtlasWidth(w);
		PackerValues.getInstance().setAtlasHeight(h);
		
		//Graphics g = texture.getGraphics();
     	//g.setColor(Color.BLUE);
     	//g.drawRect(0, 0, w-1, h-1);
     	
     	if( spriteCount < sprites.size() )
     	{
     		return false;
     	}
     	else
     	{
     		SortImagesByIndex();
     		return true;
     	}
	
	}
	
	public void reset()
	{
		sprites = new ArrayList<Sprite>();
		coords = new ArrayList<Coord>();
		root = new Node();
	}
	
	private void createTexture( int width, int height )
	{
		root.createTexture(width, height);
		
		texture = new BufferedImage( width, 
							         height, 
							         BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) texture.getRaster().getDataBuffer()).getData();	

		int offset = 0;
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ )
			{
				int col = 0;
		        int c = (col << 24) | (col << 16) | (col << 8) | (col);
				pixels[offset++] = c;
			}
			
		}
    	
	}
	
	private void loadImages( File[] filenames, int redTrans, int greenTrans, int blueTrans )
	{
		int size = filenames.length;
		for( int i  = 0; i < size; i++ )
		{
			Sprite sprite = new Sprite();
			sprite.loadImage(filenames[i], redTrans, greenTrans, blueTrans);
			sprite.setIndex(i);
			sprites.add(sprite);
			//System.out.println(filenames[i] );
			
		}
		
	}
	
	private void loadImages( String[] filenames, int redTrans, int greenTrans, int blueTrans )
	{
		int size = filenames.length;
		for( int i  = 0; i < size; i++ )
		{
			Sprite sprite = new Sprite();
			sprite.loadImage(filenames[i], redTrans, greenTrans, blueTrans);
			sprite.setIndex(i);
			sprites.add(sprite);
			//System.out.println(filenames[i] );
			
		}
		
	}
	
	private void SortImages()
	{
		int size = sprites.size();
		for( int i = 0; i < size; i++ )
		{
			for( int j = i; j < size; j++ )
			{
				Sprite s1 = sprites.get(i);
				Sprite s2 = sprites.get(j);
				int a1 = s1.getArea();
				int a2 = s2.getArea();
				if( a1 < a2 )
				{
					Sprite temp = sprites.get(i);
					sprites.set(i, sprites.get(j));
					sprites.set(j, temp);
				}
			}
				
		}
				
	}
	
	private void SortImagesByIndex()
	{
		int size = sprites.size();
		for( int i = 0; i < size; i++ )
		{
			for( int j = i; j < size; j++ )
			{
				int idx1 = sprites.get(i).getIndex();
				int idx2 = sprites.get(j).getIndex();
				if( idx1 < idx2 )
				{
					Sprite temp = sprites.get(i);
					sprites.set(i, sprites.get(j));
					sprites.set(j, temp);
					
					Coord t2 = coords.get(i);
					coords.set(i, coords.get(j));
					coords.set(j, t2);
					
				}
			}
				
		}
				
	}
	
	private boolean insertImages( int index, boolean rotate )
	{
    
		Sprite img = sprites.get(index);
	    
		Node pnode = root.insert(img, rotate );
	    
	    if(pnode != null ) 
	    {
	        // copy pixels over from img into pnode->rect part of texture
	        int x = pnode.getRect().getX1();
	        int y = pnode.getRect().getY1();
	     	
	     	img.setX(x);
	     	img.setY(y);
	     	
	     	Graphics g = texture.getGraphics();
	     	g.drawImage(img.getImage(), x, y, null);
	     	pnode.setImageID(img.getIndex() + 1);

	     	if( PackerValues.getInstance().isDebug() )
	     	{
		     	g.setColor(Color.BLUE);
		     	g.drawRect(x, y, img.getWidth(), img.getHeight());
		     	g.setColor(Color.MAGENTA);
		     	if(PackerValues.getInstance().isResizeImages())
		     	{
		     		g.drawRect(x, y, img.getOrigWidth() , img.getOrigHeight());
			    }
		     	else
		     	{
		     		g.drawRect(x + img.getMargin(), y  + img.getMargin(), img.getOrigWidth() , img.getOrigHeight());
		     	}
	     	}
	     	
	     	coords.add(new Coord( x + img.getMargin(), 
	     						  y  + img.getMargin(), 
	     						  img.getOrigWidth() , 
	     						  img.getOrigHeight(),
	     						  img.isRotated(),
	     						  img.getIndex() ));
	     	return true;
		}
	    else
	    {
	       return false;
	    }
	
	}
	
	public void createTextureIndex()
	{
		int maxHeight = 0;
		int totalWidth = 0;
		
		for( int i = 0; i < sprites.size(); i++ )
		{
			int idx = sprites.get(i).getIndex();
			Sprite s = sprites.get(idx);
			if( s.getHeight() > maxHeight ) maxHeight = s.getHeight();
			totalWidth += s.getWidth();
		}
		
		textureIndex = new BufferedImage( totalWidth, 
		         maxHeight, 
		         BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = textureIndex.getGraphics();
     	
		g.setColor(Color.ORANGE);;
		int x = 0;
		for( int i = 0; i < sprites.size(); i++ )
		{
			int idx = sprites.get(i).getIndex();
			Sprite s = sprites.get(idx);
			g.drawImage(s.getImage(), x, 0, null);
			if( (i & 1) == 0 )
				g.drawString( "" + i, x, 10 );
			else
				g.drawString( "" + i, x, maxHeight - 10 );
			x += s.getWidth();
		}

	}
	public void printCoords()
	{
		int size = coords.size();
		for( int i  = 0; i < size; i++ )
		{
			Coord c = coords.get(i);
			System.out.println("Coord #" + i);
			System.out.println("    x: " + c.getX() );
			System.out.println("    y: " + c.getY() );
			System.out.println("    w: " + c.getWidth() );
			System.out.println("    h: " + c.getHeight() );
			System.out.println("    r: " + c.isRotated() );
		}
	}
	
	private int nextPowerOf2( int val )
	{
		val--;
		val = (val >> 1) | val;
		val = (val >> 2) | val;
		val = (val >> 4) | val;
		val = (val >> 8) | val;
		val = (val >> 16) | val;
		val++; 
		
		return val;
		
	}
	
	public BufferedImage getTexture()
	{
		return texture;
	}

	public BufferedImage getTextureIndex()
	{
		return textureIndex;
	}

	public List<Sprite> getSprites()
	{
		return sprites;
	}


	public float getProgressPercentage()
	{
		return progressPercentage;
	}


	public List<Coord> getCoords()
	{
		return coords;
	}
	
	

}
