import java.awt.Dimension;

import javax.swing.JComboBox;

class ComboBoxSize extends JComboBox
{
	
	private static final long serialVersionUID = 1L;

	public ComboBoxSize()
	{
		final String[] values = { "2", "4", "8", "16", "32", "64", "128",
								  "256", "512", "1024", "2048", "4096" };
		
		for( int i = 0; i < values.length; i++ )
		{
			this.addItem(values[i]);
		}
		
		setSelectedIndex(8);
        setPreferredSize(new Dimension(140, 22));
        setMaximumSize(new Dimension(140, 22));
	}
		
}

