package notepad.frontend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

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
//		txtText.getDocument().addDocumentListener(new DocumentListener() {
//
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				Document.getInstance().setText(txtText.getText());
//				
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				Document.getInstance().setText(txtText.getText());
//				
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				Document.getInstance().setText(txtText.getText());
//				
//			}
//			
//		});
		txtText.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				Document.getInstance().setText(txtText.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				Document.getInstance().setText(txtText.getText());
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
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
	
	private int resetDocument() {
		int res = 0;
		if(Document.getInstance().isModified()) {
			res = JOptionPane.showConfirmDialog(this, "Do yout want to save changes before proceeding?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION);
			switch(res) {
			case JOptionPane.YES_OPTION:
				performSave();
				break;
				
			case JOptionPane.NO_OPTION:
				break;
				
			case JOptionPane.CANCEL_OPTION:
				break;
			}
		}		
		
		if(res != JOptionPane.CANCEL_OPTION) {
			Document.getInstance().reset();
		}
		
		
		return res;
	}
	
	private JFileChooser initFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Text documents", "txt"));
		return fc;
	}
	
	private void updateGraphics() {
		if(Document.getInstance().getSaveDest() != null) {
			this.setTitle("Notepad - " + Document.getInstance().getSaveDest().getName());
		}
		else {
			this.setTitle("Notepad");
		}
		
		txtText.setText(Document.getInstance().getText());
	}
	
	public void performNew() {
		resetDocument();
		updateGraphics();
	}
	
	public void performOpen() {
		int res = resetDocument();
		if(res != JOptionPane.CANCEL_OPTION) {
			JFileChooser fc = initFileChooser();
			if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				Document.getInstance(fc.getSelectedFile());
				updateGraphics();
			}
		}
	}
	
	public void performSave() {
		if(Document.getInstance().getSaveDest() != null) {
			Document.getInstance().save();
		}
		else {
			performSaveAs();
		}
	}
	
	public void performSaveAs() {
		JFileChooser fc = initFileChooser();
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File _f = fc.getSelectedFile();
			if(!_f.getAbsolutePath().endsWith(".txt")) {
				_f = new File(_f.getAbsolutePath() + ".txt");
			}
			Document.getInstance().setSaveDest(_f);
			performSave();
			updateGraphics();
			
		}
	}
	
	public void performExit() {
		int res = resetDocument();
		if(res != JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(itemNew)) {
			performNew();
		}
		else if(e.getSource().equals(itemOpen)) {
			performOpen();
		}
		else if(e.getSource().equals(itemSave)) {
			performSave();
		}
		else if(e.getSource().equals(itemSaveAs)) {
			performSaveAs();
		}
		else if(e.getSource().equals(itemExit)) {
			performExit();
		}

	}

	public static void main(String[] args) {
		new NotepadGUI().setVisible(true);

	}

}
