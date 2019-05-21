package tddmicroexercises.textconvertor;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
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

    @Test(expected = FileNotFoundException.class)
    public void throws_when_file_does_not_exist() throws IOException {
        new HtmlPagesConverter(new File("does not exist").getAbsolutePath());
    }

    @Test
    public void converts_empty_file_to_single_page_without_page_break() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(tempFile(""));
        assertEquals("", converter.getHtmlPage(0));
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
    public void parses_file_with_multiple_page_breaks() throws IOException {
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

    @Test
    public void converts_file_with_nothing_but_a_page_break_into_two_empty_pages() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(tempFile("PAGE_BREAK"));
        assertEquals("", converter.getHtmlPage(0));
        assertEquals("", converter.getHtmlPage(1));
    }

    @Test
    public void converts_file_with_two_page_breaks_with_nothing_in_between() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(tempFile("page 1\n" +
                "PAGE_BREAK\n" +
                "PAGE_BREAK\n" +
                "page 3"));
        assertEquals("page 1<br />", converter.getHtmlPage(0));
        assertEquals("", converter.getHtmlPage(1));
        assertEquals("page 3<br />", converter.getHtmlPage(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void ignores_multiple_page_breaks_on_same_line() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter(tempFile("page 1\n" +
                "PAGE_BREAK ignored content? PAGE_BREAK\n" +
                "page 2"));
        assertEquals("page 1<br />", converter.getHtmlPage(0));
        assertEquals("page 2<br />", converter.getHtmlPage(1));
        converter.getHtmlPage(2);
    }

    @Parameters({
            "&|&amp;",
            "<|&lt;",
            ">|&gt;",
            "\"|&quot;",
            "'|&quot;"
    })
    @Test
    public void escapes_html(String character, String expected) throws IOException {
        HtmlPagesConverter htmlPagesConverter = new HtmlPagesConverter(tempFile(character));
        assertEquals(expected + "<br />", htmlPagesConverter.getHtmlPage(0));
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

    @Test
    public void is_unaffacted_by_subsequent_file_changes() throws IOException {
        String tempFilename = tempFile("A line\nPAGE_BREAK\nAnother line\n");
        HtmlPagesConverter converter = new HtmlPagesConverter(tempFilename);

        overwriteFile(tempFilename, "foo\nbar\n");

        assertEquals("A line<br />", converter.getHtmlPage(0));
        assertEquals("Another line<br />", converter.getHtmlPage(1));
    }

    private void overwriteFile(String tempFilename, String newContent) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(tempFilename)) {
            writer.print(newContent);
        }
    }
}
