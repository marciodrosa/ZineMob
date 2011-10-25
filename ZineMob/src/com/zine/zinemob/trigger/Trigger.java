package com.zine.zinemob.trigger;

/**
 * An action to be executed into a TriggerQueue.
 */
public abstract class Trigger implements Runnable {
	
	private boolean singleExecution = false;
	private boolean blockQueue = false;
	private long executionFlags = 0;

	/**
	 * Returns if the trigger must be removed from the TriggerQueue after execute.
	 */
	public boolean isSingleExecution() {
		return singleExecution;
	}

	/**
	 * Sets if the trigger must be removed from the TriggerQueue after execute.
	 */
	public void setSingleExecution(boolean singleExecution) {
		this.singleExecution = singleExecution;
	}

	/**
	 * Returns if the Triggers after this Trigger on the TriggerQueue must
	 * not execute if this Trigger don't execute.
	 */
	public boolean mustBlockQueue() {
		return blockQueue;
	}

	/**
	 * Sets if the Triggers after this Trigger on the TriggerQueue must
	 * not execute if this Trigger don't execute.
	 */
	public void setBlockQueue(boolean blockQueue) {
		this.blockQueue = blockQueue;
	}

	/**
	 * Returns the flags of this Trigger.
	 */
	public long getExecutionFlags() {
		return executionFlags;
	}

	/**
	 * Sets the flags of this Trigger.
	 */
	public void setExecutionFlags(long executionFlags) {
		this.executionFlags = executionFlags;
	}
	
	public abstract void run();
}
