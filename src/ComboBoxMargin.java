import java.awt.Dimension;

import javax.swing.JComboBox;


class ComboBoxMargin extends JComboBox
{
	
	private static final long serialVersionUID = 1L;

	public ComboBoxMargin()
	{
		final String[] values = { "0", "1", "2", "3", "4", "5", "6", "7",
				  "8", "9", "10",  };

		for( int i = 0; i < values.length; i++ )
		{
			this.addItem(values[i]);
		}
		
		setSelectedIndex(0);
        setPreferredSize(new Dimension(140, 22));
        setMaximumSize(new Dimension(140, 22));
	}
		
}
