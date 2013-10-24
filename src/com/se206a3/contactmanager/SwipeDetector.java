package com.se206a3.contactmanager;

import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author Ben Brown
 *
 * Swipe detector for list view items.
 * Swipe left = delete contact.
 * Swipe right = edit contact.
 */
public class SwipeDetector implements View.OnTouchListener {

	/**
	 * 
	 * @author Ben Brown
	 * Enum that defines swipe types
	 */
    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        None // when no action was detected
    }

    /**
     * Min distance moved for a swipe to be registered
     */
    private static final int MIN_DISTANCE = 75;
    
    /**
     * Points where finger touches and leaves screen
     */
    private float downX, upX;
    
    /**
     * Type of swipe
     */
    private Action mSwipeDetected = Action.None;

    /**
     * Returns if swipe is detected
     * @return boolean (If swipe has occurred)
     */
    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    /**
     * Returns type of swipe
     * @return swipe type
     */
    public Action getAction() {
        return mSwipeDetected;
    }

    @Override
    /**
     * Method that is called on touch of elements
     */
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: // If no swipe
            downX = event.getX();
            mSwipeDetected = Action.None;
            return false; // allow other events like Click to be processed
        case MotionEvent.ACTION_UP: // If swipe
            upX = event.getX();

            float deltaX = downX - upX;

            // horizontal swipe detection
            if (Math.abs(deltaX) > MIN_DISTANCE) {
                // left or right
                if (deltaX < 0) {
                    mSwipeDetected = Action.LR;
                    return false;
                }
                if (deltaX > 0) {
                    mSwipeDetected = Action.RL;
                    return false;
                }
            } 
            return false;
        }
        return false;
    }
}