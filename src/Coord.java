	public class Coord
	{
		private int x;
		private int y;
		private int width;
		private int height;
		private boolean rotated;
		private int index;
		
		public Coord()
		{
			
		}
		
		public Coord( int x, int y, int width, int height, boolean rotated, int index )
		{
			
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.rotated = rotated;
			this.index = index;
	
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

		public boolean isRotated()
		{
			return rotated;
		}

		public void setRotated(boolean rotated)
		{
			this.rotated = rotated;
		}

		public int getIndex()
		{
			return index;
		}

		public void setIndex(int index)
		{
			this.index = index;
		}
		
		
	}
	
