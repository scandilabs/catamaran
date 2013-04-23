package com.scandilabs.catamaran.util;

import java.io.IOException;

import com.scandilabs.catamaran.util.StringUtils;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

    public void testStringDupeElmination() throws IOException {
        
        String s = StringUtils.removeDuplicatePipeDelimitedTerms("asdf the great| qwer is my name");
        System.out.println(s);
        
        s = StringUtils.removeDuplicatePipeDelimitedTerms("asdf | qwer|oiuoiu");
        System.out.println(s);
        
        s = StringUtils.removeDuplicatePipeDelimitedTerms("asdf | ");
        System.out.println(s);
        
        s = StringUtils.removeDuplicatePipeDelimitedTerms(" asdf  ");
        System.out.println(s);
        
        s = StringUtils.removeDuplicatePipeDelimitedTerms("");
        System.out.println("x" + s + "x");
        
        s = StringUtils.removeDuplicatePipeDelimitedTerms(" ");
        System.out.println("x" + s + "x");
        
        s = StringUtils.removeDuplicatePipeDelimitedTerms(null);
        System.out.println("x" + s + "x");
        
    }
}
