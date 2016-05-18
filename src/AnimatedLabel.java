import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class AnimatedLabel extends JLabel implements ActionListener
{
	private Timer timer;
	private boolean blinkMe = true;
	private String myText;
	private String doubleText;
	private int scroller;
	
	public AnimatedLabel( int delay, String text )
	{
		this.myText = text + "   ";
		this.doubleText = this.myText + this.myText;
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
	
		blinkMe = !blinkMe;
		scroller += 1;
		if( scroller >= myText.length() ) scroller = 0;
			setText(doubleText.substring(scroller, scroller+myText.length()-1));
		
		repaint();
	}

	public void setMyText(String myText)
	{
		this.myText = myText + "   ";
		this.doubleText = this.myText + this.myText;
	}
	
	
}
