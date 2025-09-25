package dk.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpServiceTest {

    private HelpService helpService = new HelpService();

    @Test
    void getFileExtPart() {
        assertEquals("txt", helpService.getFileExtPart("file.txt"));
        assertEquals("", helpService.getFileExtPart("file"));
    }

    @Test
    void howmanyCharOccur() {
        assertEquals(2, helpService.howmanyCharOccur("http://google.com:4837/service", ':'));
        assertEquals(0, helpService.howmanyCharOccur("google.com", ':'));
    }
}