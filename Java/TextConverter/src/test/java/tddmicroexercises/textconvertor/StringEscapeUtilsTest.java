package tddmicroexercises.textconvertor;

import org.junit.Test;

import static org.junit.Assert.*;
import static tddmicroexercises.textconvertor.StringEscapeUtils.escapeHtml;

public class StringEscapeUtilsTest {

    @Test
    public void replaces_amp() {
        assertEquals("&amp;", escapeHtml("&"));
    }

    @Test
    public void replaces_lt() {
        assertEquals("&lt;", escapeHtml("<"));
    }

    @Test
    public void replaces_gt() {
        assertEquals("&gt;", escapeHtml(">"));
    }

    @Test
    public void replaces_single_quote() {
        assertEquals("&quot;", escapeHtml("\""));
    }

    @Test
    public void replaces_double_quote() {
        assertEquals("&quot;", escapeHtml("'"));
    }
}