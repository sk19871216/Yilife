package com.innovation.android.library.tests;

import com.innovation.android.library.util.InnovationAlgorithm;

import junit.framework.TestCase;

public class InnovationAlgorithmTest extends TestCase {

    public void test宜生活的加密算法() {
        String sha1 = InnovationAlgorithm.SHA1("I*ZkoN3q5SWj9bFPOCrM3hO0Ru(5vO$C", "1234567890123456");
        assertEquals("2cd1d8aab11cd36e18da1fd1c4d1f955dff7fb74", sha1);
    }

}
