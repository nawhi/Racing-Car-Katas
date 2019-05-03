package tddmicroexercises.textconvertor;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class HtmlConverterTest {

    private final HtmlConverter converter = new HtmlConverter();

    @Test
    public void appends_break_to_every_line_and_concatenates() {
        List<String> lines = asList("foo", "bar", "baz");
        assertEquals("foo<br />bar<br />baz<br />", converter.convertLines(lines));
    }
}