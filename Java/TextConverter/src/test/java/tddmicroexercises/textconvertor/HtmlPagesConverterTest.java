package tddmicroexercises.textconvertor;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;


public class HtmlPagesConverterTest {
    @Ignore
    @Test
    public void foo() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter("foo");
        assertEquals("foo", converter.getFilename());
    }
}
