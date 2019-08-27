package notepad.backend.service;

import java.io.File;

public interface ITextFileService {

	public void saveText(String text, File dest);
	public String openText(File source);
}
