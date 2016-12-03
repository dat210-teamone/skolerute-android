package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.OneUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by Nicolas on 03.10.2016.
 * Part of project skolerute-android
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

    @Test
    public void firstOrNull_EmptyArray_GetNull() throws Exception{
        String[] data = new String[] { };
        String result = OneUtils.firstOrNull(data);
        Assert.assertNull(result);
    }

    @Test
    public void firstOrNull_OneElementArray_GetFirst() throws Exception{
        String[] data = new String[] { "Hello" };
        String result = OneUtils.firstOrNull(data);
        Assert.assertEquals(data[0], result);
    }

    @Test
    public void firstOrNull_TwoElementArray_GetFirst() throws Exception{
        String[] data = new String[] { "Hello", "World" };
        String result = OneUtils.firstOrNull(data);
        Assert.assertEquals(data[0], result);
    }

    @Test
    public void sameDay_sameDate() throws Exception{
        Date a = new Date();
        Assert.assertTrue("Date is the same day", OneUtils.sameDay(a, a));
    }

    @Test
    public void sameDay_differentDate() throws Exception{
        Date a = new Date();
        Date b = new Date(a.getTime() + 864000000);
        Assert.assertFalse("Date is the same day", OneUtils.sameDay(a, b));
    }

}
