import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class Sprite
{
	private BufferedImage image;
	private int index;
	private int x;
	private int y;
	private int width;
	private int height;
	private int origWidth;
	private int origHeight;
	private int area;
	private int margin;
	private boolean rotated = false;
	private int angle = 0;
	
	public Sprite()
	{
		
	}
	
	public void loadImage( String filename )
	{
	   	
		URL imageUrl = this.getClass().getClassLoader().getResource(filename);
		   		
	   	try 
	   	{                
	          image = ImageIO.read(imageUrl);
	          if( image.getColorModel().getPixelSize() == 8 ) image = convert32(image);
	    } 
	   	catch (IOException ex) 
	   	{
	           // handle exception...
	    }
	   	
	   	width = image.getWidth();
	   	height = image.getHeight();
	   	origWidth = width;
	   	origHeight = height;
	   	area = width * height;
	}

	public void loadImage( File filename )
	{
	   	
		   		
	   	try 
	   	{                
	          image = ImageIO.read(filename);
	          if( image.getColorModel().getPixelSize() == 8 ) image = convert32(image);
	    } 
	   	catch (IOException ex) 
	   	{
	           // handle exception...
	    }
	   	
	   	width = image.getWidth();
	   	height = image.getHeight();
	   	origWidth = width;
	   	origHeight = height;
	   	area = width * height;
	}

	public void loadImage( String filename, int redTrans, int greenTrans, int blueTrans )
	{
	   	
		URL imageUrl = this.getClass().getClassLoader().getResource(filename);
		   		
	   	try 
	   	{                
	          image = ImageIO.read(imageUrl);
	          if( image.getColorModel().getPixelSize() == 8 ) image = convert32(image, redTrans, greenTrans, blueTrans );
	    } 
	   	catch (IOException ex) 
	   	{
	           // handle exception...
	    }
	   	
	   	width = image.getWidth();
	   	height = image.getHeight();
	   	origWidth = width;
	   	origHeight = height;
	   	area = width * height;
	}

	public void loadImage( File filename, int redTrans, int greenTrans, int blueTrans )
	{
	   	
		   		
	   	try 
	   	{                
	          image = ImageIO.read(filename);
	          if( image.getColorModel().getPixelSize() == 8 ) image = convert32(image, redTrans, greenTrans, blueTrans);
	    } 
	   	catch (IOException ex) 
	   	{
	           // handle exception...
	    }
	   	
	   	width = image.getWidth();
	   	height = image.getHeight();
	   	origWidth = width;
	   	origHeight = height;
	   	area = width * height;
	}

	public void rotate90()
	{
				
		image = rotateBufferedImage90( image );
		
		width = image.getWidth();
	   	height = image.getHeight();
	   	origWidth = width;
	   	origHeight = height;
	   	area = width * height;
		
	   	rotated = true;
	   	angle += 90;
	}
	
	public void addMargin( int margin )
	{
		this.margin = margin;
		
		BufferedImage texture = new BufferedImage( width + margin * 2, 
		         				  				   height + margin * 2, 
		         				  				   BufferedImage.TYPE_INT_ARGB);

		int[] pixels = ((DataBufferInt) texture.getRaster().getDataBuffer()).getData();	
		
		int offset = 0;
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ )
			{
				int c = (0 << 24) | (0 << 16) | (0 << 8) | (0);
				pixels[offset++] = c;
			}

		}

		Graphics2D g = (Graphics2D)texture.getGraphics();
		g.drawImage(image, margin, margin, null);
		g.dispose();
		image = texture;
     	width = image.getWidth();
	   	height = image.getHeight();
	   	area = width * height;
    	
	}

	public void addMarginAndBleed( int margin )
	{
		this.margin = margin;
		
		BufferedImage texture = new BufferedImage( width + margin * 2, 
		         				  				   height + margin * 2, 
		         				  				   BufferedImage.TYPE_INT_ARGB);
		
		int[] pixels = ((DataBufferInt) texture.getRaster().getDataBuffer()).getData();	
		
		int offset = 0;
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ )
			{
				int c = (0 << 24) | (0 << 16) | (0 << 8) | (0);
				pixels[offset++] = c;
			}

		}

		Graphics2D g = (Graphics2D)texture.getGraphics();
		g.drawImage(image, margin, margin, null);
		g.dispose();		
		image = texture;
		width = image.getWidth();
	   	height = image.getHeight();
	   	area = width * height;
	   	
	   	bleedEdgesBufferedImage( image, margin );
     		   	
	}

	public void addMarginAndResize( int margin )
	{
		this.margin = margin;
		
		BufferedImage texture = new BufferedImage( width + margin * 2, 
		         				  				   height + margin * 2, 
		         				  				   BufferedImage.TYPE_INT_ARGB);

		int[] pixels = ((DataBufferInt) texture.getRaster().getDataBuffer()).getData();	
		
		int offset = 0;
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ )
			{
				int c = (0 << 24) | (0 << 16) | (0 << 8) | (0);
				pixels[offset++] = c;
			}

		}
		
		Graphics2D g = (Graphics2D)texture.getGraphics();
		g.drawImage(image, margin, margin, null);
		g.dispose();
		image = texture;
     	width = image.getWidth();
	   	height = image.getHeight();
	   	origWidth = width;
	   	origHeight = height;
	   	area = width * height;
    	
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public int getIndex()
	{
		return index;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getArea()
	{
		return area;
	}

	public int getMargin()
	{
		return margin;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setArea(int area)
	{
		this.area = area;
	}

	public void setMargin(int margin)
	{
		this.margin = margin;
	}
	
	public int getOrigWidth()
	{
		return origWidth;
	}

	public int getOrigHeight()
	{
		return origHeight;
	}

	public boolean isRotated()
	{
		return rotated;
	}

	public int getAngle()
	{
		return angle;
	}

	private BufferedImage convert32(BufferedImage src ) 
	{
	    BufferedImage dest = new BufferedImage( src.getWidth(), src.getHeight(),
	    										BufferedImage.TYPE_INT_ARGB);
	    int[] pixelsDest = ((DataBufferInt) dest.getRaster().getDataBuffer()).getData();
	    
	    int offset = 0;
	    int w = src.getWidth();
	    int h = src.getHeight();
	    for( int y = 0; y < h; y++ )
		{
			for( int x = 0; x < w; x++ )
			{
				int rgb = src.getRGB(x, y);
				int red = (rgb >> 16) & 0xFF;
				int green = (rgb >> 8) & 0xFF;
				int blue = rgb & 0xFF;
				int alpha = 255;
				
				if( (red == 255) && (blue == 255) )
				{
					alpha = 0;
				}
				int c = (alpha << 24) | (red << 16) | (green << 8) | (blue);
				
				pixelsDest[offset++] = c;
			}

		}
	    return dest;
	}

	private BufferedImage convert32(BufferedImage src, int redTrans, int greenTrans, int blueTrans ) 
	{
	    BufferedImage dest = new BufferedImage( src.getWidth(), src.getHeight(),
	    										BufferedImage.TYPE_INT_ARGB);
	    int[] pixelsDest = ((DataBufferInt) dest.getRaster().getDataBuffer()).getData();
	    
	    int offset = 0;
	    int w = src.getWidth();
	    int h = src.getHeight();
	    for( int y = 0; y < h; y++ )
		{
			for( int x = 0; x < w; x++ )
			{
				int rgb = src.getRGB(x, y);
				int red = (rgb >> 16) & 0xFF;
				int green = (rgb >> 8) & 0xFF;
				int blue = rgb & 0xFF;
				int alpha = 255;
				
				if( (red == redTrans) && (blue == blueTrans) && (green == greenTrans) )
				{
					alpha = 0;
				}
				int c = (alpha << 24) | (red << 16) | (green << 8) | (blue);
				
				pixelsDest[offset++] = c;
			}

		}
	    return dest;
	}
	
	
	private BufferedImage rotateBufferedImage90( BufferedImage orig ) 
	{      
		int w = orig.getWidth();
		int h = orig.getHeight();
		BufferedImage dest = new BufferedImage( h, w, BufferedImage.TYPE_INT_ARGB );
		for( int y = 0; y < h; y++ )
		{
			for( int x  = 0; x < w; x++ )
			{
				int color = orig.getRGB(x, y);
				dest.setRGB(h-1-y, x, color);
			}
		}
		
		return dest;
				
	}
	
	private void bleedEdgesBufferedImage( BufferedImage orig, int margin ) 
	{      
		int w = orig.getWidth();
		int h = orig.getHeight();
		//top
		int mTop = margin;
		int mLeft = margin;
		int mBottom = h - margin;
		int mRight = w - margin;
		
		
		for( int i = 0; i < margin; i++ )
		{
			// top and bottom
			for( int span = 0; span < w; span++ )
			{
				int color = orig.getRGB(span, mTop);
				orig.setRGB(span, mTop-i-1, color);
				color = orig.getRGB(span, mBottom-1);
				orig.setRGB(span, mBottom+i, color);
				
			}
			// left and right 
			for( int span = 0; span < h; span++ )
			{
				int color = orig.getRGB(mLeft, span);
				orig.setRGB(mLeft-i-1, span, color);
				color = orig.getRGB(mRight-1, span);
				orig.setRGB(mRight+i, span, color);
				
			}
			
				
		}
			
				
	}
	

}
