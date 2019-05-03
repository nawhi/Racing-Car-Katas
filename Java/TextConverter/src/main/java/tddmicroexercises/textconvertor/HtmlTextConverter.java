package tddmicroexercises.textconvertor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HtmlTextConverter
{
    private String fullFilenameWithPath;
	private final HtmlConverter htmlConverter = new HtmlConverter();

	public HtmlTextConverter(String fullFilenameWithPath)
    {
        this.fullFilenameWithPath = fullFilenameWithPath;
    }

    public String convertToHtml() throws IOException{
		Path path = Paths.get(fullFilenameWithPath);
		List<String> lines = Files.readAllLines(path);
		return htmlConverter.convertLines(lines);
    }

	public String getFilename() {
		return this.fullFilenameWithPath;
	}

}
