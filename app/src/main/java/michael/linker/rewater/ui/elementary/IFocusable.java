package michael.linker.rewater.ui.elementary;

import android.view.View;

public interface IFocusable {
    /**
     * A callback to be invoked when the focus state of a view changed.
     *
     * @param listener called when the focus state of a view has changed
     */
    void setOnFocusChangeListener(View.OnFocusChangeListener listener);
}
