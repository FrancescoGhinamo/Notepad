package notepad.backend.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileServiceImpl implements ITextFileService {
	
	TextFileServiceImpl() {
		
	}

	@Override
	public void saveText(String text, File dest) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(dest);
			String toWrite = text.replaceAll("\n", "\r\n");
			fw.write(toWrite);
		} catch (IOException e) {
			
		}
		finally {
			if(fw != null) {
				try {
					fw.flush();
				} catch (IOException e) {
					try {
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public String openText(File source) {
		
		String res = "";
		// TODO Auto-generated method stub
		FileInputStream fis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			
			fis = new FileInputStream(source);
			
			byte[] buff = new byte[4096];
			int nRead;
			while((nRead = fis.read(buff, 0, buff.length)) != -1) {
				bos.write(buff, 0, nRead);
			}
			res = bos.toString();
			
		} catch (IOException e) {
			
		}
		finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
		return res;

	}

}
