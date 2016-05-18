import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class SaveC
{

	private List<String> lines = new ArrayList<String>();
	private int width;
	private int height;
	
	public SaveC( int bitmapWidth, int bitmapHeight, List<Coord> coords )
	{
		width = bitmapWidth;
		height = bitmapHeight;
		
		int size = coords.size();
		for( int i  = 0; i < size; i++ )
		{
			int idx = coords.get(i).getIndex();
			Coord c = coords.get(idx);
			String line = "\t" +
						  c.getX() + ",\t" +
						  c.getY() + ",\t" +
						  c.getWidth() + ",\t" +
						  c.getHeight() + ",\t";
			if( c.isRotated() )
			{
				line += "" + 1 + ",\t // " + i;
			}
			else
			{
				line += "" + 0 + ",\t // " + i;
			}
						  
			lines.add(line);
		}
	}
	
	public void saveCoords( String title )
	{
		System.out.println("/* ");
		System.out.println("\tRel's TexturePacker Generated Coordinates ");
		System.out.println("\thttp://rel.phatcode.net ");
		System.out.println("\trelminator 2014 ");
		System.out.println("");
		System.out.println("\tFilename: " + title +".h");
		System.out.println("\tWidth: " + width);
		System.out.println("\tHeight: " + height);
		System.out.println("\tImages: " + lines.size());
		System.out.println("");
		System.out.println("\tFormat: x,	y,	width,	height,	rotated");
		System.out.println("*/");
		
		System.out.println("");
		System.out.println("");
		
		System.out.println("#ifndef " + title.toUpperCase() +"__H");
		System.out.println("#define " + title.toUpperCase() +"__H");
		
		System.out.println("");
		System.out.println("");
		
		System.out.println("#define " + title.toUpperCase() +"_BITMAP_WIDTH\t" + width);
		System.out.println("#define " + title.toUpperCase() +"_BITMAP_HEIGHT\t" + height);
		System.out.println("#define " + title.toUpperCase() +"_NUM_IMAGES\t" + lines.size());
		
		System.out.println("");
		System.out.println("");
		
		System.out.println("extern const unsigned int " +
				title.toLowerCase() + "_textCoords[] = { ");
						
		
		int size = lines.size();
		for( int i  = 0; i < size; i++ )
		{
			System.out.println(lines.get(i));
		}
		
		System.out.println("};");
		
		System.out.println("");
		System.out.println("");
		System.out.println("#endif \t\t//" + title.toUpperCase() +"__H");
		
	}
	
	public void saveToFileCoords( String title )
	{
		File out = new File( title + ".h" );
	    FileWriter fw;
		try
		{
			fw = new FileWriter ( out );
			PrintWriter pw = new PrintWriter( fw );
			pw.println("/* ");
		    pw.println("\tRel's TexturePacker Generated Coordinates ");
		    pw.println("\thttp://rel.phatcode.net ");
			pw.println("\trelminator 2014 ");
			pw.println("");
			pw.println("\tFilename: " + title +".h");
			pw.println("\tWidth: " + width);
			pw.println("\tHeight: " + height);
			pw.println("\tImages: " + lines.size());
			pw.println("");
			pw.println("\tFormat: x,	y,	width,	height,	rotated");
			pw.println("*/");
			
			pw.println("");
			pw.println("");
			
			pw.println("#ifndef " + title.toUpperCase() +"__H");
			pw.println("#define " + title.toUpperCase() +"__H");
			
			pw.println("");
			pw.println("");
			
			pw.println("#define " + title.toUpperCase() +"_BITMAP_WIDTH\t" + width);
			pw.println("#define " + title.toUpperCase() +"_BITMAP_HEIGHT\t" + height);
			pw.println("#define " + title.toUpperCase() +"_NUM_IMAGES\t" + lines.size());
			
			pw.println("");
			pw.println("");
			
			pw.println("extern const unsigned int " +
					title.toLowerCase() + "_textCoords[] = { ");
							
			
			int size = lines.size();
			for( int i  = 0; i < size; i++ )
			{
				pw.println(lines.get(i));
			}
			
			pw.println("};");
			
			pw.println("");
			pw.println("");
			pw.println("#endif \t\t//" + title.toUpperCase() +"__H");

			fw.close();
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		

	}

}
