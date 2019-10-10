package notepad.frontend.gui;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

public class CharWordCounter extends JLabel implements Runnable {

	
	private static final long serialVersionUID = 7193120597222639878L;
	
	private static final String FIXED_TEXT1 = "Character count: ";
	private static final String FIXED_TEXT2 = "Word count: ";
	
	private JTextArea linked;	
	

	public CharWordCounter(JTextArea linked) {
		super();
		this.linked = linked;
	}



	@Override
	public void run() {
		while(true) {
			String text = linked.getText();
			int charCount = text.length();
			int wordCount = 0;
			if(!text.equals("")) {
				wordCount = StringUtils.split(text).length;
			}
			
			this.setText(FIXED_TEXT1 + charCount + "            " + FIXED_TEXT2 + wordCount);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
