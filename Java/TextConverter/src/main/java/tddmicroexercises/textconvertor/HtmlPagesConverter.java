package tddmicroexercises.textconvertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HtmlPagesConverter {

    private final List<String> allLines = new ArrayList<>();
    private final List<String> linesWithoutPageBreaks = new ArrayList<>();
    private String filename;
    private List<Integer> breaks = new ArrayList<Integer>();

    public HtmlPagesConverter(String filename) throws IOException {
        this.filename = filename;

        this.breaks.add(0);
        BufferedReader reader = new BufferedReader(new FileReader(this.filename));
        int cumulativeCharCount = 0;
        String line = reader.readLine();
        StringBuilder pageBuilder = new StringBuilder();
        while (line != null)
        {
            allLines.add(line);
            cumulativeCharCount += line.length() + 1; // add one for the newline
            if (line.contains("PAGE_BREAK")) {
                int page_break_position = cumulativeCharCount;
                breaks.add(page_break_position);
                linesWithoutPageBreaks.add(pageBuilder.toString());
                pageBuilder.setLength(0);
            } else {
                pageBuilder.append(line);
            }
            line = reader.readLine();
        }

        reader.close();
    }

    public String getHtmlPage(int page) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(String.join("\n", allLines)));
        reader.skip(breaks.get(page));
        StringBuffer htmlPage = new StringBuffer();
        String line = reader.readLine();
        while (line != null)
        {
            if (line.contains("PAGE_BREAK")) {
                break;
            }
            htmlPage.append(StringEscapeUtils.escapeHtml(line));
            htmlPage.append("<br />");

            line = reader.readLine();
        }
        reader.close();
        return htmlPage.toString();
    }

    public String getFilename() {
        return this.filename;
    }

}
