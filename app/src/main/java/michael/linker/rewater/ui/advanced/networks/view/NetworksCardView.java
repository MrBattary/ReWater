package michael.linker.rewater.ui.advanced.networks.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.networks.model.CompactNetworkModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModelFailedException;
import michael.linker.rewater.ui.animation.transition.IOrderedTransition;
import michael.linker.rewater.ui.elementary.status.CombinedStatusView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class NetworksCardView {
    private final Context mParentContext;
    private final CardView mCardView;
    private final TextView mHeading;
    private final TextView mDescription;
    private final CombinedStatusView mCombinedStatusView;
    private final ImageButton mExpandOrLooseButton;
    private final Button mSettingsButton;
    private final View mHiddenContent;
    private final IOrderedTransition mTransition;
    private String mId;

    public NetworksCardView(
            final Context context,
            final View view,
            final NetworksViewModel parentViewModel,
            final IOrderedTransition transition) {
        mParentContext = context;
        mCardView = view.findViewById(R.id.networks_card);
        mHeading = view.findViewById(R.id.networks_card_heading);
        mDescription = view.findViewById(R.id.networks_card_description);
        mCombinedStatusView = new CombinedStatusView(
                view.findViewById(R.id.networks_card_combined_status));
        mExpandOrLooseButton = view.findViewById(R.id.networks_card_expand_or_loose_button);
        mSettingsButton = view.findViewById(R.id.networks_card_settings_button);
        mHiddenContent = view.findViewById(R.id.networks_card_hidden_content);
        mTransition = transition;

        transition.addChangeBoundsTarget(view);
        this.initTransitionTargets();
        this.setCompactView();
        this.initButtonsLogic(parentViewModel);
    }

    public void setCompactNetworkModel(final CompactNetworkModel model) {
        mId = model.getId();
        mHeading.setText(model.getHeading());
        mDescription.setText(model.getDescription());
        this.setGoneIfNoTextInTextView(mDescription);
        mCombinedStatusView.setStatus(model.getStatus());
    }

    private void initButtonsLogic(final NetworksViewModel parentViewModel) {
        initExpandOrLooseButtonLogic();
        initSettingsButtonLogic(parentViewModel);
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

    private void initSettingsButtonLogic(final NetworksViewModel parentViewModel) {
        mSettingsButton.setOnClickListener(l -> {
            try {
                parentViewModel.setEditableNetworkId(mId);
            } catch (NetworksViewModelFailedException e) {
                ToastProvider.showShort(mParentContext, e.getMessage());
            }
            Navigation.findNavController(mCardView).navigate(
                    R.id.navigation_action_networks_to_networks_edit);
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
}
