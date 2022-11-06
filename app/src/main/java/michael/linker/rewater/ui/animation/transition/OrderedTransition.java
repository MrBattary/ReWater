package michael.linker.rewater.ui.animation.transition;

import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

public class OrderedTransition implements IOrderedTransition {
    private ViewGroup mRootView;
    private final TransitionSet mTransitionSet;
    private final Transition mFade;
    private final Transition mChangeBounds;

    public OrderedTransition() {
        mTransitionSet = new TransitionSet();
        mFade = new Fade(Visibility.MODE_IN);
        mChangeBounds = new ChangeBounds();
    }

    @Override
    public void setRootView(final ViewGroup rootView) {
        mRootView = rootView;
    }

    @Override
    public void setDuration(final int mills) {
        setFadeDuration(mills);
        setChangeBoundsDuration(mills);
    }

    @Override
    public void setFadeDuration(final int mills) {
        mFade.setDuration(mills);
    }

    @Override
    public void setChangeBoundsDuration(final int mills) {
        mChangeBounds.setDuration(mills);
    }

    @Override
    public void addFadeTarget(final View targetView) {
        mFade.addTarget(targetView);
    }

    @Override
    public void addChangeBoundsTarget(final View targetView) {
        mChangeBounds.addTarget(targetView);
    }

    @Override
    public void beginDelayedAppearing() throws OrderedTransitionNotFoundException {
        TransitionSet appearTransitionSet = this.beginDelayedTransition();
        this.setAppearingOrder(appearTransitionSet);
        TransitionManager.beginDelayedTransition(mRootView, appearTransitionSet);
    }

    @Override
    public void beginDelayedDisappearing() throws OrderedTransitionNotFoundException {
        TransitionSet disappearTransitionSet = this.beginDelayedTransition();
        this.setDisappearingOrder(disappearTransitionSet);
        TransitionManager.beginDelayedTransition(mRootView, disappearTransitionSet);
    }

    private TransitionSet beginDelayedTransition() throws OrderedTransitionNotFoundException {
        if (mRootView == null) {
            throw new OrderedTransitionNotFoundException("The root view was not provided!");
        }
        TransitionSet transitionSet = mTransitionSet;
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        return transitionSet;
    }

    private void setAppearingOrder(final TransitionSet transitionSet) {
        transitionSet.addTransition(mChangeBounds);
        transitionSet.addTransition(mFade);
    }

    private void setDisappearingOrder(final TransitionSet transitionSet) {
        transitionSet.addTransition(mFade);
        transitionSet.addTransition(mChangeBounds);
    }
}
