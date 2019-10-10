package notepad.frontend.gui;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

public class PageCounter extends JLabel implements Runnable {

	
	private static final long serialVersionUID = -3191037100693792098L;
	
	private static final String FIXED_TEXT = "Page count: ";
	
	private JTextArea linked;
	
	

	public PageCounter(JTextArea linked) {
		super();
		this.linked = linked;
	}



	@Override
	public void run() {
		while(true) {
			String text = linked.getText();
			int pageCount = text.length();
			
			if(!text.equals("")) {
				pageCount = StringUtils.countMatches(text, "\n");
			}
			pageCount = pageCount / 40;
			
			this.setText(FIXED_TEXT + pageCount);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
