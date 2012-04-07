package com.zine.zinemob.gui;

import com.zine.zinemob.eventmessage.EventMessage;
import com.zine.zinemob.animation.AnimationController;
import com.zine.zinemob.drawableelement.LinearLayoutElement;
import com.zine.zinemob.drawableelement.layout.StretchToParentLayout;

/**
 * A window to be place into a GuiScene.
 */
public class Window extends Container {
	
	private GuiController guiController;
	
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

	public void setGuiScene(GuiScene guiScene) {
		super.setGuiScene(guiScene);
		if (guiController != null) {
			guiController.setGuiScene(guiScene);
		}
	}
	
	/**
	 * Returns the controller. It is null unless you set an instance with the setGuiController method.
	 */
	public GuiController getGuiController() {
		return guiController;
	}

	/**
	 * Sets the controller. If this method is not called or is called with a null
	 * argument, then the Window events is controlled by the GuiController object
	 * of the GuiScene.
	 */
	public void setGuiController(GuiController guiController) {
		this.guiController = guiController;
		if (guiController != null) {
			guiController.setGuiScene(getGuiScene());
		}
	}

	/**
	 * Overrides to call the onEvent of the GuiController object.
	 */
	public void onGuiEvent(EventMessage event) {
		if (guiController == null) {
			if (getGuiScene() != null) {
				GuiController sceneGuiController = getGuiScene().getGuiController();
				if (sceneGuiController != null) {
					sceneGuiController.onEvent(event);
				}
			}
		} else {
			guiController.onEvent(event);
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
