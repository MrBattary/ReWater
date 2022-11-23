package michael.linker.rewater.ui.elementary.text.status;

import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.ui.elementary.text.IText;

public interface IStatusStyledTextView extends IText {
    /**
     * Set the text to some style according to the provided status.
     *
     * @param text   text to be set
     * @param status the status on the basis of which the style will be set
     */
    void setText(String text, Status status);
}
