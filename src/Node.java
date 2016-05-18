
public class Node
{

	private Node[] child = new Node[2];
	private Rect rect = new Rect();
	private int imageID;
	
	public Node()
	{
		child[0] = null;
		child[1] = null;
		
		imageID = 0;
				
	}
	
	public void createTexture( int width, int height )
	{
		rect.x1 = 0;
		rect.y1 = 0;
		rect.x2 = width-1;
		rect.y2 = height-1;
	}
	
	public Node insert( Sprite img, boolean rotate )
	{
		if( img.isRotated() )
		{
			rotate = false;
		}
		
		// non leaf
	    if( !isLeaf() ) 				// we're not a leaf then
	    {    
	        
	        // (try inserting into first child)
	        Node newNode = child[0].insert( img, rotate );
	        if(newNode != null )
	        {
	        	return newNode;
	        }
	        
	       	//(no room, insert into second)
	       	return child[1].insert( img, rotate );
	        
	    }    
	    else  // leaf
	    {
	    	
	        //(if there's already an image here, return)
	        if( imageID != 0 )
	        {
	        	return null;
	        }

			Rect b = new Rect( rect.x1, rect.y1,
							   ( rect.x1 + img.getWidth() ) - 1,
							   ( rect.y1 + img.getHeight() ) - 1 );
	    	
			// check fit value
			int result = qualifyRectangle(rect, b);
	       
			
	        // if the containing rect too small(image is bigger), return
	        // one of the image axis is bigger than rect
	        if(result == 1) 	// img doesn't fit in pnode->rect
	        {
	        	if( (!rotate) )
	        	{
	        		return null;
	        	}
	        	else
	        	{
        	    	// rotation packer algo
		        	// rotate image 90 degs and requalify
		        	// if image is still bigger, return null
		        	// otherwise
		        	
		        	// just rotate the dimensions to test
		        	Rect c = new Rect( rect.x1, rect.y1,
							   ( rect.x1 + img.getHeight() ) - 1,
							   ( rect.y1 + img.getWidth() ) - 1 );
		        	
		        	result = qualifyRectangle(rect, c);
		        	
		        	if(result == 1) return null;   // still too big
		        	
		        	// Fits so rotate and pack
		        	img.rotate90();
		        	
		        	// result would either be 0 or -1 at this point
		        	// which can be handled by the code below
        		}
	        	
	        }

	        // if we're just right, accept
	        if (result == 0)  	// img fits perfectly in pnode->rect
	        {
	        	return this;
	        }
	        
	        // otherwise, gotta split this node and create some kids
	        // ie: we are smaller so split the node
	        child[0] = new Node();
	        child[1] = new Node();
	        
	        // decide which way to split
	        int rw = (rect.x2 - rect.x1) + 1; 
	        int rh = (rect.y2 - rect.y1) + 1; 
	        
	        int dw = rw - img.getWidth();
	        int dh = rh - img.getHeight();
	        
	        
	        // child 0 rect = image rect dimensions
	        if(dw > dh)
	        {
	            child[0].rect.x1 = rect.x1;
	            child[0].rect.y1 = rect.y1;
	            child[0].rect.x2 = rect.x1 + (img.getWidth() - 1);
	            child[0].rect.y2 = rect.y2;
	            
	            child[1].rect.x1 = rect.x1 + (img.getWidth());
	            child[1].rect.y1 = rect.y1;
	            child[1].rect.x2 = rect.x2;
	            child[1].rect.y2 = rect.y2;
	        }    
	        else
	        {
	            child[0].rect.x1 = rect.x1;
	            child[0].rect.y1 = rect.y1;
	            child[0].rect.x2 = rect.x2;
	            child[0].rect.y2 = rect.y1 + (img.getHeight() - 1);                               
	                  	
	        	child[1].rect.x1 = rect.x1;
	            child[1].rect.y1 = rect.y1 + (img.getHeight());
	            child[1].rect.x2 = rect.x2;
	            child[1].rect.y2 = rect.y2;
	        }
	       
	        // return first child since it would fit perfectly
	        // ie. we have just calculated the bounding box that
	        //     can hold the image perfectly see "if (dw > dh) then" codeblock
	        	        
	        return child[0].insert(img, rotate);
	        
	    }
	    

	}
	
	public boolean isLeaf()
	{
		return ((child[0] == null) && (child[1] == null) );	
	}
	
	
	// tests if b could fit inside a
	// -1 = B is smaller
	//  0 = B fits A
	//  1 = B is one axis larger
	private int qualifyRectangle(Rect a, Rect b)
	{

		// a.x1 = b.x1	
		// a.y1 = b.y1
		
		
		int a_wid = (a.x2 - a.x1) + 1;
		int a_hei = (a.y2 - a.y1) + 1;
		
		int b_wid = (b.x2 - b.x1) + 1;
		int b_hei = (b.y2 - b.y1) + 1;
			
		if( (b_wid > a_wid) || (b_hei > a_hei) )
		{
			return 1;
		}
		
		if( (b_wid < a_wid) || (b_hei < a_hei) )
		{
			return -1;
		}
		
		return 0;
		
	}

	
	public Node[] getChild()
	{
		return child;
	}

	public Rect getRect()
	{
		return rect;
	}

	public void setRect(Rect rect)
	{
		this.rect = rect;
	}

	public int getImageID()
	{
		return imageID;
	}

	public void setImageID(int imageID)
	{
		this.imageID = imageID;
	}

	
}
