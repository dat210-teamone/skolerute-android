package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.OneUtils;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void TestUrl() throws Exception{
        URL url = new URL("http://open.stavanger.kommune.no/dataset/8f8ac030-0d03-46e2-8eb7-844ee11a6203/resource/0371a1db-7074-4568-a0cc-499a5dccfe98/download/skoler.csv");
        String fileName = OneUtils.getFileName(url);
        assertEquals(fileName,"skoler.csv");
    }
}