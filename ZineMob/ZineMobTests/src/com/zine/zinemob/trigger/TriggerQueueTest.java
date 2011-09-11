package com.zine.zinemob.trigger;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class TriggerQueueTest extends TestCase {
	
	private TriggerQueue triggerQueue;
	private StringBuffer theStringBuffer;

	public TriggerQueueTest() {

	}

	public TriggerQueueTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		triggerQueue = new TriggerQueue();
		theStringBuffer = new StringBuffer();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new TriggerQueueTest("testRun", new TestMethod()
		{ public void run(TestCase tc) {((TriggerQueueTest)tc).testRun(); } } ));

		testSuite.addTest(new TriggerQueueTest("testRunWithFlagsShouldRunOnlyTheTriggersWithTheFlags", new TestMethod()
		{ public void run(TestCase tc) {((TriggerQueueTest)tc).testRunWithFlagsShouldRunOnlyTheTriggersWithTheFlags(); } } ));

		testSuite.addTest(new TriggerQueueTest("testShouldNotRunTriggersAfterSomeTriggerThatMustBlockDontRun", new TestMethod()
		{ public void run(TestCase tc) {((TriggerQueueTest)tc).testShouldNotRunTriggersAfterSomeTriggerThatMustBlockDontRun(); } } ));

		testSuite.addTest(new TriggerQueueTest("testShouldRunTriggersAfterSomeTriggerThatMustBlockRun", new TestMethod()
		{ public void run(TestCase tc) {((TriggerQueueTest)tc).testShouldRunTriggersAfterSomeTriggerThatMustBlockRun(); } } ));

		testSuite.addTest(new TriggerQueueTest("testShouldRemoveFromQueueTheSingleExecutionTriggerAfterRun", new TestMethod()
		{ public void run(TestCase tc) {((TriggerQueueTest)tc).testShouldRemoveFromQueueTheSingleExecutionTrigger(); } } ));

		testSuite.addTest(new TriggerQueueTest("testShouldNotRemoveFromQueueTheSingleExecutionTriggerIfTheTriggerDontRun", new TestMethod()
		{ public void run(TestCase tc) {((TriggerQueueTest)tc).testShouldNotRemoveFromQueueTheSingleExecutionTriggerIfTheTriggerDontRun(); } } ));

		return testSuite;
	}
	
	private Trigger createTriggerThatWritesIntoTheStringBuffer(final String str) {
		
		return new Trigger() {
			public void run() {
				theStringBuffer.append(str);
			}
		};
	}

	private void testRun() {
		
		// given:
		triggerQueue.addTrigger(createTriggerThatWritesIntoTheStringBuffer("a"));
		triggerQueue.addTrigger(createTriggerThatWritesIntoTheStringBuffer("b"));
		triggerQueue.addTrigger(createTriggerThatWritesIntoTheStringBuffer("c"));
		
		// when:
		triggerQueue.run();
		
		// then:
		assertEquals("The content of the StringBuffer is not the expected, so the triggers dont run as expected.", "abc", theStringBuffer.toString());
	}

	private void testRunWithFlagsShouldRunOnlyTheTriggersWithTheFlags() {
		
		int flag1 = 2;
		int flag2 = 4;
		int flag3 = 8;
		
		// given:
		Trigger trigger1 = createTriggerThatWritesIntoTheStringBuffer("a");
		Trigger trigger2 = createTriggerThatWritesIntoTheStringBuffer("b");
		Trigger trigger3 = createTriggerThatWritesIntoTheStringBuffer("c");
		Trigger trigger4 = createTriggerThatWritesIntoTheStringBuffer("d");
		
		trigger1.setExecutionFlags(flag1);
		trigger2.setExecutionFlags(flag2);
		trigger3.setExecutionFlags(flag3);
		trigger4.setExecutionFlags(flag1 | flag2);
		
		triggerQueue.addTrigger(trigger1);
		triggerQueue.addTrigger(trigger2);
		triggerQueue.addTrigger(trigger3);
		triggerQueue.addTrigger(trigger4);
		
		// when:
		triggerQueue.run(flag1 | flag3);
		
		// then:
		assertEquals("The content of the StringBuffer is not the expected, so the triggers dont run as expected.", "acd", theStringBuffer.toString());
	}

	private void testShouldNotRunTriggersAfterSomeTriggerThatMustBlockDontRun() {
		
		int flag1 = 2;
		int flag2 = 4;
		int flag3 = 8;
		
		// given:
		Trigger trigger1 = createTriggerThatWritesIntoTheStringBuffer("a");
		Trigger trigger2 = createTriggerThatWritesIntoTheStringBuffer("b");
		Trigger trigger3 = createTriggerThatWritesIntoTheStringBuffer("c");
		
		trigger1.setExecutionFlags(flag1);
		trigger2.setExecutionFlags(flag2);
		trigger3.setExecutionFlags(flag3);
		
		trigger2.setBlockQueue(true);
		
		triggerQueue.addTrigger(trigger1);
		triggerQueue.addTrigger(trigger2);
		triggerQueue.addTrigger(trigger3);
		
		// when:
		triggerQueue.run(flag1 | flag3);
		
		// then:
		assertEquals("The content of the StringBuffer is not the expected, so the triggers dont run as expected.", "a", theStringBuffer.toString());
	}

	private void testShouldRunTriggersAfterSomeTriggerThatMustBlockRun() {
		
		int flag1 = 2;
		int flag2 = 4;
		int flag3 = 8;
		
		// given:
		Trigger trigger1 = createTriggerThatWritesIntoTheStringBuffer("a");
		Trigger trigger2 = createTriggerThatWritesIntoTheStringBuffer("b");
		Trigger trigger3 = createTriggerThatWritesIntoTheStringBuffer("c");
		
		trigger1.setExecutionFlags(flag1);
		trigger2.setExecutionFlags(flag2);
		trigger3.setExecutionFlags(flag3);
		
		trigger2.setBlockQueue(true);
		
		triggerQueue.addTrigger(trigger1);
		triggerQueue.addTrigger(trigger2);
		triggerQueue.addTrigger(trigger3);
		
		// when:
		triggerQueue.run(flag1 | flag2 | flag3);
		
		// then:
		assertEquals("The content of the StringBuffer is not the expected, so the triggers dont run as expected.", "abc", theStringBuffer.toString());
	}

	private void testShouldRemoveFromQueueTheSingleExecutionTrigger() {
		
		// given:
		Trigger trigger1 = createTriggerThatWritesIntoTheStringBuffer("a");
		Trigger trigger2 = createTriggerThatWritesIntoTheStringBuffer("b");
		Trigger trigger3 = createTriggerThatWritesIntoTheStringBuffer("c");
		
		triggerQueue.addTrigger(trigger1);
		triggerQueue.addTrigger(trigger2);
		triggerQueue.addTrigger(trigger3);
		
		trigger2.setSingleExecution(true);
		
		// when:
		triggerQueue.run();
		triggerQueue.run();
		
		// then:
		assertEquals("The content of the StringBuffer is not the expected, so the triggers dont run as expected.", "abcac", theStringBuffer.toString());
	}

	private void testShouldNotRemoveFromQueueTheSingleExecutionTriggerIfTheTriggerDontRun() {
		
		int flag1 = 2;
		int flag2 = 4;
		
		// given:
		Trigger trigger1 = createTriggerThatWritesIntoTheStringBuffer("a");
		Trigger trigger2 = createTriggerThatWritesIntoTheStringBuffer("b");
		Trigger trigger3 = createTriggerThatWritesIntoTheStringBuffer("c");
		
		triggerQueue.addTrigger(trigger1);
		triggerQueue.addTrigger(trigger2);
		triggerQueue.addTrigger(trigger3);
		
		trigger1.setExecutionFlags(flag1);
		trigger2.setExecutionFlags(flag2);
		trigger3.setExecutionFlags(flag1);
		
		trigger2.setSingleExecution(true);
		
		// when:
		triggerQueue.run(flag1);
		triggerQueue.run();
		
		// then:
		assertEquals("The content of the StringBuffer is not the expected, so the triggers dont run as expected.", "acabc", theStringBuffer.toString());
	}
	
}
