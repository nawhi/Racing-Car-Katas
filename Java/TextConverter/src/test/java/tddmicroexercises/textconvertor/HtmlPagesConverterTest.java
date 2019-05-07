package tddmicroexercises.textconvertor;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class HtmlPagesConverterTest {

    private static final String NO_PAGE_BREAKS_FILENAME = loadFromResources("/foo.txt");
    private static final String WITH_PAGE_BREAKS_FILENAME = loadFromResources("/with_page_breaks.txt");

    @Test
    public void preserves_filename() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(NO_PAGE_BREAKS_FILENAME);
        assertEquals(NO_PAGE_BREAKS_FILENAME, converter.getFilename());
    }

    @Test
    public void parses_file_with_no_page_breaks() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(NO_PAGE_BREAKS_FILENAME);
        assertEquals("hello<br />world<br />&amp;<br />&lt;<br />&gt;<br />&quot;<br />&quot;<br />", converter.getHtmlPage(0));
    }

    @Test
    public void parses_file_with_page_break() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(WITH_PAGE_BREAKS_FILENAME);
        assertEquals("A line &lt;&gt;<br />", converter.getHtmlPage(0));
        assertEquals("Another line ?<br />", converter.getHtmlPage(1));
        assertEquals("final&quot;&quot;line<br />", converter.getHtmlPage(2));
    }

    @Test(expected=Exception.class)
    public void throws_something_for_out_of_range_line_number() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(WITH_PAGE_BREAKS_FILENAME);
        converter.getHtmlPage(3);
    }

    private static String loadFromResources(String relativeFilename) {
        return HtmlTextConverterTest.class
                .getResource(relativeFilename).getPath();
    }
}
