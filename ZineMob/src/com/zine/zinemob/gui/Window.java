package com.zine.zinemob.gui;

import com.zine.zinemob.animation.AnimationController;
import com.zine.zinemob.drawableelement.LinearLayoutElement;
import com.zine.zinemob.drawableelement.layout.StretchToParentLayout;

/**
 * A window to be place into a GuiScene.
 */
public class Window extends Container {
	
	private GuiEventsController guiEventsController;
	
	private AnimationController showAnimation, resumeAnimation, goToBackgroundAnimation, closeAnimation;
	
	private boolean hideWhenGoToBackground = true;
	private boolean closeWhenGoToBackground = false;
	
	public Window() {
	}
	
	/**
	 * @param maximize true to add a layout fixer to the drawable element to stretch
	 * the size of the window to the size of the screen. Default is false.
	 */
	public Window(boolean maximize) {
		if (maximize) {
			getLinearLayoutElement().setFitPolicy(LinearLayoutElement.FIT_POLICY_DONT_FIT_TO_CHILDREN);
			getLinearLayoutElement().addLayout(new StretchToParentLayout(getLinearLayoutElement()));
		}
	}

	/**
	 * Returns the events controller.
	 */
	public GuiEventsController getGuiEventsController() {
		return guiEventsController;
	}

	/**
	 * Sets the events controller.
	 */
	public void setGuiEventsController(GuiEventsController guiEventsController) {
		this.guiEventsController = guiEventsController;
	}

	/**
	 * Overrides to call the onEvent of the GuiEventsController object.
	 */
	public void onGuiEvent(GuiEvent event) {
		if (getGuiEventsController() != null) {
			getGuiEventsController().onEvent(event, getGuiSceneController());
		}
	}

	/**
	 * Returns the animation to execute when the Window is shown.
	 */
	public AnimationController getShowAnimation() {
		return showAnimation;
	}

	/**
	 * Sets the animation to execute when the Window is shown.
	 */
	public void setShowAnimation(AnimationController showAnimation) {
		this.showAnimation = showAnimation;
	}

	/**
	 * Returns the animation to execute when the Window is resumed (back to foreground).
	 */
	public AnimationController getResumeAnimation() {
		return resumeAnimation;
	}

	/**
	 * Sets the animation to execute when the Window is resumed (back to foreground).
	 */
	public void setResumeAnimation(AnimationController resumeAnimation) {
		this.resumeAnimation = resumeAnimation;
	}

	/**
	 * Returns the animation to execute when the Window goes to background.
	 */
	public AnimationController getGoToBackgroundAnimation() {
		return goToBackgroundAnimation;
	}

	/**
	 * Sets the animation to execute when the Window goes to background.
	 */
	public void setGoToBackgroundAnimation(AnimationController goToBackgroundAnimation) {
		this.goToBackgroundAnimation = goToBackgroundAnimation;
	}

	/**
	 * Returns the animation to execute when the Window is closed.
	 */
	public AnimationController getCloseAnimation() {
		return closeAnimation;
	}

	/**
	 * Sets the animation to execute when the Window is closed.
	 */
	public void setCloseAnimation(AnimationController closeAnimation) {
		this.closeAnimation = closeAnimation;
	}

	/**
	 * Returns if the Window must be hided when comes to background.
	 */
	public boolean mustHideWhenGoToBackground() {
		return hideWhenGoToBackground;
	}

	/**
	 * Sets if the Window must be hided when comes to background.
	 */
	public void setHideWhenGoToBackground(boolean hideWhenGoToBackground) {
		this.hideWhenGoToBackground = hideWhenGoToBackground;
	}

	/**
	 * Returns if the Window must be closed when comes to background.
	 */
	public boolean mustCloseWhenGoToBackground() {
		return closeWhenGoToBackground;
	}

	/**
	 * Sets if the Window must be closed when comes to background.
	 */
	public void setCloseWhenGoToBackground(boolean closeWhenGoToBackground) {
		this.closeWhenGoToBackground = closeWhenGoToBackground;
	}
	
}
