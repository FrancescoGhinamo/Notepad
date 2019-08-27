package notepad.backend.service;

public class TextFileServiceFactory {

	public static ITextFileService getTextFileService() {
		return (ITextFileService)new TextFileServiceImpl();
	}
}
