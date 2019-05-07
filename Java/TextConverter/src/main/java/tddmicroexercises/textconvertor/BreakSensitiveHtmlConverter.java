package tddmicroexercises.textconvertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BreakSensitiveHtmlConverter {

    public String invoke(int page, List<Integer> breaks, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Integer offsetToThisPage = breaks.get(page);
        reader.skip(offsetToThisPage);
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
}
