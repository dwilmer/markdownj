/*
Copyright (c) 2005, Pete Bevin.
<http://markdownj.petebevin.com>

All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

* Redistributions of source code must retain the above copyright notice,
  this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright
  notice, this list of conditions and the following disclaimer in the
  documentation and/or other materials provided with the distribution.

* Neither the name "Markdown" nor the names of its contributors may
  be used to endorse or promote products derived from this software
  without specific prior written permission.

This software is provided by the copyright holders and contributors "as
is" and any express or implied warranties, including, but not limited
to, the implied warranties of merchantability and fitness for a
particular purpose are disclaimed. In no event shall the copyright owner
or contributors be liable for any direct, indirect, incidental, special,
exemplary, or consequential damages (including, but not limited to,
procurement of substitute goods or services; loss of use, data, or
profits; or business interruption) however caused and on any theory of
liability, whether in contract, strict liability, or tort (including
negligence or otherwise) arising in any way out of the use of this
software, even if advised of the possibility of such damage.

*/

package com.petebevin.markdown.test;

import com.petebevin.markdown.MarkdownProcessor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MarkdownTestTester extends TestCase {
    String test;
    String dir;

    public MarkdownTestTester(String dir, String test) {
        super(test);
        this.test = test;
        this.dir = dir;
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("MarkdownTest");
        suite.addTest(newSuite("tests/MarkdownTest"));
        return suite;
    }

    public static Test newSuite(String dirName) {
        TestSuite suite = new TestSuite("MarkdownProcessor file " + dirName);
        File dir = new File(dirName);
        File[] dirEntries = dir.listFiles();

        for (int i = 0; i < dirEntries.length; i++) {
            File dirEntry = dirEntries[i];
            String fileName = dirEntry.getName();
            if (fileName.endsWith(".text")) {
                String testName = fileName.substring(0, fileName.lastIndexOf('.'));
                suite.addTest(new MarkdownTestTester(dirName, testName));
            }
        }

        return suite;
    }

    public void runTest() throws IOException {
        String testText = slurp(new File(dir, test + ".text"));
        String htmlText = slurp(new File(dir, test + ".html"));
        MarkdownProcessor markup = new MarkdownProcessor();
        assertEquals(test, htmlText.trim(), markup.markdown(testText).trim());
    }

    private String slurp(File file) throws IOException {
        FileReader in = new FileReader(file);
        StringBuffer sb = new StringBuffer();
        int ch;
        while ((ch = in.read()) != -1) {
            sb.append((char) ch);
        }
        return sb.toString();
    }
}