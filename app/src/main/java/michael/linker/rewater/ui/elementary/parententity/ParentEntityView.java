package michael.linker.rewater.ui.elementary.parententity;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.res.StatusColorsProvider;
import michael.linker.rewater.ui.elementary.ICustomView;

public class ParentEntityView implements ICustomView {
    private final View mView;
    private final ImageView mImageView;
    private final TextView mTextView;
    private final String mPlaceholderText;

    public ParentEntityView(final View view, final String placeholderText) {
        mView = view;
        mPlaceholderText = placeholderText;
        mImageView = mView.findViewById(R.id.parent_entity_icon);
        mTextView = mView.findViewById(R.id.parent_entity_text);
    }

    public void setParentEntity(final String parentEntityName) {
        if (parentEntityName != null && !parentEntityName.equals("")) {
            mTextView.setText(parentEntityName);
            mImageView.setImageTintList(ColorStateList.valueOf(
                    StatusColorsProvider.getColorForStatus(Status.OK)
            ));
        } else {
            this.removeParentEntity();
        }
    }

    public void removeParentEntity() {
        mTextView.setText(mPlaceholderText);
        mImageView.setImageTintList(ColorStateList.valueOf(
                StatusColorsProvider.getColorForStatus(Status.DEFECT)
        ));
    }

    @Override
    public View getViewInstance() {
        return mView;
    }
}
