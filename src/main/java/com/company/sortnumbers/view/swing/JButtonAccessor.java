package com.company.sortnumbers.view.swing;

import aurelienribon.tweenengine.TweenAccessor;
import javax.swing.JButton;

public class JButtonAccessor implements TweenAccessor<JButton> {

    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;

    @Override
    public int getValues(JButton target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POSITION_X -> {
                returnValues[0] = target.getX();
                return 1;
            }
            case POSITION_Y -> {
                returnValues[0] = target.getY();
                return 1;
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public void setValues(JButton target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POSITION_X -> target.setLocation((int) newValues[0], target.getY());
            case POSITION_Y -> target.setLocation(target.getX(), (int) newValues[0]);
        }
    }
}
