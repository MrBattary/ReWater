package michael.linker.rewater.ui.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import michael.linker.rewater.R;
import michael.linker.rewater.model.network.NetworkItemModel;
import michael.linker.rewater.assist.provider.DrawablesProvider;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;

public class NetworksCardView implements IView {
    private final CardView mCardView;
    private final TextView mHeading;
    private final TextView mDescription;
    private final CombinedStatusView mCombinedStatusView;
    private final ImageButton mExpandOrLooseButton;
    private final Button mSettingsButton;
    private final View mHiddenContent;
    private final OrderedTransition mTransition;
    private NetworkItemModel mData;

    public NetworksCardView(final View view, final OrderedTransition transition) {
        mCardView = view.findViewById(R.id.networks_card);
        mHeading = view.findViewById(R.id.networks_card_heading);
        mDescription = view.findViewById(R.id.networks_card_description);
        mCombinedStatusView = new CombinedStatusView(
                view.findViewById(R.id.networks_card_combined_status));
        mExpandOrLooseButton = view.findViewById(R.id.networks_card_expand_or_loose_button);
        mSettingsButton = view.findViewById(R.id.networks_card_settings_button);
        mHiddenContent = view.findViewById(R.id.networks_card_hidden_content);
        mTransition = transition;

        this.initTransitionTargets();
        this.setCompactView();
        this.initButtonsLogic();
    }

    public void setData(final NetworkItemModel dataModel) {
        mData = dataModel;
        mHeading.setText(dataModel.getHeading());
        mDescription.setText(dataModel.getDescription());
        mCombinedStatusView.setStatus(dataModel.getStatus());
    }

    private void initButtonsLogic() {
        initExpandOrLooseButtonLogic();
        initSettingsButtonLogic();
    }

    private void initExpandOrLooseButtonLogic() {
        mExpandOrLooseButton.setOnClickListener(view -> {
            if (mHiddenContent.getVisibility() == View.VISIBLE) {
                mTransition.beginDelayedDisappearing();
                setCompactView();
            } else {
                mTransition.beginDelayedAppearing();
                setLooseView();
            }
        });
    }

    private void initSettingsButtonLogic() {
        // TODO (Battary): Add logic
    }

    private void initTransitionTargets() {
        // Permanent content
        mTransition.addChangeBoundsTarget(mCardView);
        mTransition.addFadeTarget(mCombinedStatusView.getViewInstance());
        mTransition.addFadeTarget(mCombinedStatusView.getOverallStatusView().getViewInstance());
        mTransition.addFadeTarget(mCombinedStatusView.getDetailedStatusView().getViewInstance());
        // Hidden content
        mTransition.addChangeBoundsTarget(mHiddenContent);
        mTransition.addFadeTarget(mSettingsButton);
        mTransition.addFadeTarget(mDescription);
    }

    private void setCompactView() {
        mExpandOrLooseButton.setImageDrawable(
                DrawablesProvider.getDrawable(R.drawable.ic_button_expand));
        mCombinedStatusView.displayCompact();
        mSettingsButton.setVisibility(View.GONE);
        mDescription.setVisibility(View.GONE);
        mHiddenContent.setVisibility(View.GONE);
    }

    private void setLooseView() {
        mExpandOrLooseButton.setImageDrawable(
                DrawablesProvider.getDrawable(R.drawable.ic_button_loose));
        mCombinedStatusView.displayDetailed();
        mSettingsButton.setVisibility(View.VISIBLE);
        mDescription.setVisibility(View.VISIBLE);
        mHiddenContent.setVisibility(View.VISIBLE);
    }

    @Override
    public View getViewInstance() {
        return mCardView;
    }
}
