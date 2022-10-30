package michael.linker.rewater.ui.animation.transition;

import android.view.View;
import android.view.ViewGroup;

/**
 * TransitionSet with specific order of transitions.
 * In: Change bounds -> Fade.
 * Out: Fade -> Change bounds.
 */
public interface IOrderedTransition {
    /**
     * Set root view.
     *
     * @param rootView root group view
     */
    void setRootView(final ViewGroup rootView);

    /**
     * Set duration for both transitions.
     *
     * @param mills milliseconds
     */
    void setDuration(final int mills);

    /**
     * Set duration for the fade transition.
     *
     * @param mills milliseconds
     */
    void setFadeDuration(final int mills);

    /**
     * Set duration for the change bounds transition.
     *
     * @param mills milliseconds
     */
    void setChangeBoundsDuration(final int mills);

    /**
     * Add target for the fade transition.
     *
     * @param targetView target view
     */
    void addFadeTarget(final View targetView);

    /**
     * Add target for the change bounds transition.
     *
     * @param targetView target view
     */
    void addChangeBoundsTarget(final View targetView);

    /**
     * Begins a delayed transition with the order of transitions: Change bounds -> Fade.
     *
     * @throws OrderedTransitionNotFoundException if the root view was not provided
     */
    void beginDelayedAppearing() throws OrderedTransitionNotFoundException;

    /**
     * Begins a delayed transition with the order of transitions: Fade -> Change bounds.
     *
     * @throws OrderedTransitionNotFoundException if the root view was not provided
     */
    void beginDelayedDisappearing() throws OrderedTransitionNotFoundException;
}
