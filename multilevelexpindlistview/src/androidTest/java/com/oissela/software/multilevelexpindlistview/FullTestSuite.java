package com.oissela.software.multilevelexpindlistview;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

public class FullTestSuite extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(FullTestSuite.class)
                        .includeAllPackagesUnderHere().build();
    }

    public FullTestSuite() {
        super();
    }
}