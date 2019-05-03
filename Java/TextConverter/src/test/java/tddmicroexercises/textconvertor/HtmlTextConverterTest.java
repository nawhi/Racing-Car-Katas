package tddmicroexercises.textconvertor;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

public class HtmlTextConverterTest {

    @Test
    public void returns_its_filename() {
        HtmlTextConverter converter = new HtmlTextConverter("foo");
        assertEquals("foo", converter.getFilename());
    }

    @Test
    public void converts_a_unicode_file() throws IOException {
        String fullFilenameWithPath = HtmlTextConverterTest.class
                .getResource("/foo.txt").getPath();
        HtmlTextConverter converter = new HtmlTextConverter(fullFilenameWithPath);

        String expectedHtml = "hello<br />world<br />&amp;<br />&lt;<br />&gt;<br />&quot;<br />&quot;<br />";
        assertEquals(expectedHtml, converter.convertToHtml());
    }
}
