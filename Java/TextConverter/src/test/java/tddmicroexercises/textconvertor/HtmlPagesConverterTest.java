package tddmicroexercises.textconvertor;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class HtmlPagesConverterTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private String tempFile(String content) throws IOException {
        File tempFile = tempFolder.newFile("temp");
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.print(content);
        }
        return tempFile.getAbsolutePath();
    }

    @Test
    public void preserves_filename() throws IOException {
        String temporaryFile = tempFile("hello\n" +
                "world\n" +
                "&\n" +
                "<\n" +
                ">\n" +
                "\"\n" +
                "'\n");
        HtmlPagesConverter converter = new HtmlPagesConverter(temporaryFile);
        assertEquals(temporaryFile, converter.getFilename());
    }

    @Test
    public void parses_file_with_no_page_breaks() throws IOException {
        String temporaryFile = tempFile("hello\n" +
                "world\n" +
                "&\n" +
                "<\n" +
                ">\n" +
                "\"\n" +
                "'\n");
        HtmlPagesConverter converter = new HtmlPagesConverter(temporaryFile);
        assertEquals("hello<br />world<br />&amp;<br />&lt;<br />&gt;<br />&quot;<br />&quot;<br />", converter.getHtmlPage(0));
    }

    @Test
    public void parses_file_with_page_break() throws IOException {
        String temporaryFile = tempFile("A line <>\n" +
                "PAGE_BREAK\n" +
                "Another line ?\n" +
                "Embedded PAGE_BREAK \"mid-line\"\n" +
                "final''line\n");
        HtmlPagesConverter converter = new HtmlPagesConverter(temporaryFile);
        assertEquals("A line &lt;&gt;<br />", converter.getHtmlPage(0));
        assertEquals("Another line ?<br />", converter.getHtmlPage(1));
        assertEquals("final&quot;&quot;line<br />", converter.getHtmlPage(2));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void throws_something_for_out_of_range_line_number() throws IOException {
        String temporaryFile = tempFile("A line <>\n" +
                "PAGE_BREAK\n" +
                "Another line ?\n" +
                "Embedded PAGE_BREAK \"mid-line\"\n" +
                "final''line\n");
        HtmlPagesConverter converter = new HtmlPagesConverter(temporaryFile);
        converter.getHtmlPage(3);
    }

}
