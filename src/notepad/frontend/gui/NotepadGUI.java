package notepad.frontend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import notepad.backend.beam.Document;

public class NotepadGUI extends JFrame implements ActionListener {

	
	private static final long serialVersionUID = -7607190610495468374L;
	
	private JTextArea txtText;

	private JMenuItem itemNew;
	private JMenuItem itemOpen;
	private JMenuItem itemSave;
	private JMenuItem itemSaveAs;
	private JMenuItem itemExit;
	
	private CharWordCounter chworCount;
	private PageCounter pageCount;
	
	public NotepadGUI() {
		super("Notepad");
		setExtendedState(MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(500, 250));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Document.getInstance();
		initComponents();
	}

	private void initComponents() {
		setJMenuBar(initJMenuBar());
		setLayout(new BorderLayout());
		
		this.add(initTextPane(), BorderLayout.CENTER);
		this.add(initCounterPanel(), BorderLayout.SOUTH);
		
	}
	
	private JPanel initCounterPanel() {
		JPanel pan = new JPanel(new GridLayout(1, 2));
		
		chworCount = new CharWordCounter(txtText);
		pageCount = new PageCounter(txtText);
		
		pan.add(chworCount);
		pan.add(pageCount);
		
		new Thread(chworCount).start();
		new Thread(pageCount).start();
		
		
		
		return pan;
	}
	
	private JScrollPane initTextPane() {
		txtText = new JTextArea();
		txtText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				Document.getInstance().setText(txtText.getText());
			}
		});
		
		return new JScrollPane(txtText);
	}
	
	private JMenuBar initJMenuBar() {
		JMenuBar bar = new JMenuBar();
		bar.add(initFileMenu());
		return bar;
	}
	
	private JMenu initFileMenu() {
		JMenu mnuFile = new JMenu("File");
		
		itemNew = new JMenuItem("New");
		itemNew.addActionListener(this);
		itemOpen = new JMenuItem("Open");
		itemOpen.addActionListener(this);
		itemSave = new JMenuItem("Save");
		itemSave.addActionListener(this);
		itemSaveAs = new JMenuItem("Save as");
		itemSaveAs.addActionListener(this);
		itemExit = new JMenuItem("Exit");
		itemExit.addActionListener(this);
		
		mnuFile.add(itemNew);
		mnuFile.add(itemOpen);
		mnuFile.add(itemSave);
		mnuFile.add(itemSaveAs);
		mnuFile.addSeparator();
		mnuFile.add(itemExit);
		
		return mnuFile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new NotepadGUI().setVisible(true);

	}

}
