package com.king.practices;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void replaceString() throws Exception {
        String aaa="asdasdasdada,nishiwodeyan,asdasdadad";
        String[] split = aaa.split("nishiwodeyan");
        StringBuilder builder =new StringBuilder();
        builder.append(split[0])
                .append("啊啊啊是大神")
                .append(split[1]);

        System.out.println(builder.toString());
    }
}