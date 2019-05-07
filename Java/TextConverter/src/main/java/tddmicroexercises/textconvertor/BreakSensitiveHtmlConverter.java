package tddmicroexercises.textconvertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BreakSensitiveHtmlConverter {

    public String invoke(int page, List<Integer> breaks, String filename) throws IOException {
        Integer offsetToThisPage = breaks.get(page);
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.skip(offsetToThisPage);
        List<String> lines = readLines(reader);

        return lines.stream()
                .map(this::toHtml)
                .collect(Collectors.joining());
    }

    private List<String> readLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();
        while (line != null)
        {
            if (line.contains("PAGE_BREAK")) {
                break;
            }
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();
        return lines;
    }

    private String toHtml(String line) {
        return StringEscapeUtils.escapeHtml(line) + "<br />";
    }
}
