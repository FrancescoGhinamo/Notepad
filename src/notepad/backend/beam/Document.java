package notepad.backend.beam;

import java.io.File;

import notepad.backend.service.TextFileServiceFactory;

public class Document {
	
	private static Document me;

	public String text;
	public File saveDest;
	public boolean modified;
	
	private Document() {
		text = "";
		saveDest = null;
		modified = false;
	}
	
	
	
	private Document(String text, File saveDest) {
		super();
		this.text = text;
		this.saveDest = saveDest;
		modified = false;
	}



	public static Document getInstance() {
		if(me == null) {
			me = new Document();
		}
		return me;
	}
	
	
	public void save() {
		TextFileServiceFactory.getTextFileService().saveText(text, saveDest);
		modified = false;
	}
	
	public void open(File source) {
		String txt = TextFileServiceFactory.getTextFileService().openText(saveDest);
		me = new Document(txt, source);
	}
	
	public void reset() {
		me = new Document();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		modified = true;
	}

	public File getSaveDest() {
		return saveDest;
	}

	public void setSaveDest(File saveDest) {
		this.saveDest = saveDest;
	}

	public boolean isModified() {
		return modified;
	}
	
	
	
	
}
