package rebus;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.lang.reflect.Method;

public class Growler extends JPanel implements ActionListener {

	/**
	 * @author sstruhar
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
	private Timer timer1;
	private Color borderColor;
	private Color bodyColor;
	private JPanel passedPanel;
	private JLabel label;

	/**
	 * Create the frame and set the text message
	 * @param the message to set in the growler
	 */
	public Growler(Color newBorderColor , Color newBodyColor, JPanel newPassedPanel) {
		borderColor = newBorderColor;
		bodyColor = newBodyColor;
		passedPanel = newPassedPanel;
	}

	/**
	 * stop the timer and initiate the fade (if your machine can do it) then close and dispose of the growl
	 * Borrowed some of this code from http://www.rune-server.org/runescape-development/rs2-client/snippets/257338-jframe-transparency.html
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		timer1.stop();
		try{
			Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
			Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);
			for(float i = 0.95f; i > 0.0; i = i-0.001f){
				mSetWindowOpacity.invoke(null, passedPanel, Float.valueOf(i));
			}
		}
		catch (Exception e1)
		{   
			//Swallow the error.  Might consider changing this to indicate that an update is needed.
		}
		finally{
			passedPanel.setVisible(false);//hide
			//this.dispose();//destroy
		}


	}
	
	public void showMessage(String message) {
		passedPanel.removeAll();
		if (timer1 != null) {
				timer1.stop();
		}
		passedPanel.setVisible(true);
		passedPanel.setBackground(bodyColor);
		passedPanel.setBorder(new LineBorder(borderColor, 3, true));
		passedPanel.setLayout(new GridBagLayout());
		label = new JLabel("");
		passedPanel.add(label);
		label.setText(message);
		timer1 = new Timer(3000, this);
		timer1.setInitialDelay(3000);
		timer1.start();
	}
	
}
