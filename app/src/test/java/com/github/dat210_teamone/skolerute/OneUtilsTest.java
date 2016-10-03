package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.OneUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Nicolas on 03.10.2016.
 */

public class OneUtilsTest {
    @Test
    public void Copy_CopyToOtherStream() throws Exception{
        byte[] test = new byte[10];
        for(int i = 0; i < test.length; i++){
            test[i] = (byte)i;
        }
        ByteArrayInputStream byteIn = new ByteArrayInputStream(test);
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream(10);
        OneUtils.copy(byteIn, byteOut);
        byte[] result = byteOut.toByteArray();

        Assert.assertArrayEquals(test, result);
    }
}
