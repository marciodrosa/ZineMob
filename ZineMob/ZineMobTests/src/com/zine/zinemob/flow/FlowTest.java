package com.zine.zinemob.flow;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class FlowTest extends TestCase {
	
	public FlowTest() {

	}

	public FlowTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new FlowTest("testShouldExecuteFlow", new TestMethod()
		{ public void run(TestCase tc) {((FlowTest)tc).testShouldExecuteFlow(); } } ));
		
		return testSuite;
	}

	public void testShouldExecuteFlow() {
		// given:
		FlowControllerMock flowControllerMock = new FlowControllerMock();
		
		// when:
		Flow.getInstance().startFlow(flowControllerMock);
		
		// then:
		assertEquals("The controller calls made by the Flow class is not the expected.", flowControllerMock.getExpectedCallLog(), flowControllerMock.getCallLog());
	}
	
}
