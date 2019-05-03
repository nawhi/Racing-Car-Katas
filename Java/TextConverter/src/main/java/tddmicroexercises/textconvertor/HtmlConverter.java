package tddmicroexercises.textconvertor;

import java.util.List;
import java.util.stream.Collectors;

class HtmlConverter {

    String convertLines(List<String> lines) {
        return lines.stream()
                .map(this::convertLine)
                .collect(Collectors.joining());
    }

    private String convertLine(String line) {
        return StringEscapeUtils.escapeHtml(line)
                + "<br />";
    }
}
