package tddmicroexercises.textconvertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlPagesConverter {

    private String filename;
    private List<Integer> breaks = new ArrayList<Integer>();
    
    public HtmlPagesConverter(String filename) throws IOException {
        this.filename = filename;

        this.breaks.add(0);
        BufferedReader reader = new BufferedReader(new FileReader(this.filename));
        int cumulativeCharCount = 0;
        String line = reader.readLine();
        while (line != null)
        {
            cumulativeCharCount += line.length() + 1; // add one for the newline
            if (line.contains("PAGE_BREAK")) {
                breaks.add(cumulativeCharCount);
            }
            line = reader.readLine();
        }
        reader.close();
    }

    public String getHtmlPage(int page) throws IOException {
        final BreakSensitiveHtmlConverter foo = new BreakSensitiveHtmlConverter();
        return foo.invoke(page, breaks, filename);
    }

    public String getFilename() {
        return this.filename;
    }

}
