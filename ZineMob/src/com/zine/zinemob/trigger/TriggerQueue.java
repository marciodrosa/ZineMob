package com.zine.zinemob.trigger;

import java.util.Vector;

/**
 * An queue of Triggers that are executed sequencially.
 */
public class TriggerQueue {
	
	private Vector triggers = new Vector();
	
	/**
	 * Executes all the triggers.
	 */
	public void run() {
		run(0);
	}

	/**
	 * Executes all triggers that contains some of the flags.
	 */
	public void run(int flags) {
		
		for (int i=0; i<triggers.size(); i++) {
		
			Trigger trigger = (Trigger) triggers.elementAt(i);
			
			if (flags  == 0 || (trigger.getExecutionFlags() & flags) != 0) {
				
				trigger.run();
				
				if (trigger.isSingleExecution()) {
					triggers.removeElementAt(i--);
				}
				
			} else if (trigger.mustBlockQueue()) {
				break;
			}
		}
	}
	
	/**
	 * Add the trigger into the queue.
	 */
	public void addTrigger(Trigger trigger) {
		triggers.addElement(trigger);
	}
	
}
