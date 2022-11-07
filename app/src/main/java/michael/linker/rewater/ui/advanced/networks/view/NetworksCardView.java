package michael.linker.rewater.ui.advanced.networks.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.model.FullNetworkModel;
import michael.linker.rewater.ui.model.bundle.NetworkModelBundle;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;
import michael.linker.rewater.ui.IView;
import michael.linker.rewater.ui.elementary.status.CombinedStatusView;

public class NetworksCardView implements IView {
    private final CardView mCardView;
    private final TextView mHeading;
    private final TextView mDescription;
    private final CombinedStatusView mCombinedStatusView;
    private final ImageButton mExpandOrLooseButton;
    private final Button mSettingsButton;
    private final View mHiddenContent;
    private final OrderedTransition mTransition;
    private FullNetworkModel mData;

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

    public void setData(final FullNetworkModel dataModel) {
        mData = dataModel;
        mHeading.setText(dataModel.getHeading());
        mDescription.setText(dataModel.getDescription());
        this.setGoneIfNoTextInTextView(mDescription);
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
        mSettingsButton.setOnClickListener(l -> {
            final Bundle bundle = new NetworkModelBundle().pack(mData);
            Navigation.findNavController(mCardView).navigate(
                    R.id.navigation_action_networks_to_networks_edit, bundle);
        });
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
        this.setGoneIfNoTextInTextView(mDescription);
        mHiddenContent.setVisibility(View.VISIBLE);
    }

    private void setGoneIfNoTextInTextView(final TextView textView) {
        final CharSequence text = textView.getText();
        if (text == null || text.equals("")) {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public View getViewInstance() {
        return mCardView;
    }
}
