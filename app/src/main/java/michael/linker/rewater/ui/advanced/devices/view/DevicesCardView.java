package michael.linker.rewater.ui.advanced.devices.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModelFailedException;
import michael.linker.rewater.ui.animation.transition.IOrderedTransition;
import michael.linker.rewater.ui.elementary.parententity.ParentEntityView;
import michael.linker.rewater.ui.elementary.status.CombinedStatusView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class DevicesCardView {
    private final Context mParentContext;
    private final CardView mCardView;
    private final TextView mName;
    private final ParentEntityView mParentScheduleView, mParentNetworkView;
    private final CombinedStatusView mCombinedStatusView;
    private final ImageButton mExpandOrLooseButton;
    private final Button mSettingsButton;
    private final View mHiddenContent;
    private final IOrderedTransition mTransition;
    private String mId;

    public DevicesCardView(
            final Context context,
            final View view,
            final DevicesViewModel parentViewModel,
            final IOrderedTransition transition) {
        mParentContext = context;
        mCardView = view.findViewById(R.id.devices_card);
        mName = view.findViewById(R.id.devices_card_heading);
        mParentScheduleView = new ParentEntityView(
                view.findViewById(R.id.devices_card_parent_schedule),
                StringsProvider.getString(R.string.parent_schedule_not_found));
        mParentNetworkView = new ParentEntityView(
                view.findViewById(R.id.devices_card_parent_network),
                StringsProvider.getString(R.string.parent_network_not_found));
        mCombinedStatusView = new CombinedStatusView(
                view.findViewById(R.id.devices_card_combined_status));
        mExpandOrLooseButton = view.findViewById(R.id.devices_card_expand_or_loose_button);
        mSettingsButton = view.findViewById(R.id.devices_card_settings_button);
        mHiddenContent = view.findViewById(R.id.devices_card_hidden_content);
        mTransition = transition;

        transition.addChangeBoundsTarget(view);
        this.initTransitionTargets();
        this.setCompactView();
        this.initButtonsLogic(parentViewModel);
    }

    public void setDataModel(final CompactDeviceModel model) {
        mId = model.getId();
        mName.setText(model.getName());
        final IdNameModel parentScheduleNameIdModel = model.getParentSchedule();
        if(parentScheduleNameIdModel != null) {
            mParentScheduleView.setParentEntity(parentScheduleNameIdModel.getName());
        } else {
            mParentScheduleView.clearParentEntity();
        }
        final IdNameModel parentNetworkNameIdModel = model.getParentNetwork();
        if(parentNetworkNameIdModel != null) {
            mParentNetworkView.setParentEntity(parentNetworkNameIdModel.getName());
        } else {
            mParentNetworkView.clearParentEntity();
        }
        mCombinedStatusView.setStatus(model.getStatus());
    }

    private void initButtonsLogic(final DevicesViewModel parentViewModel) {
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

    private void initSettingsButtonLogic(final DevicesViewModel parentViewModel) {
        mSettingsButton.setOnClickListener(l -> {
            try {
                parentViewModel.setEditableDeviceId(mId);
            } catch (NetworksViewModelFailedException e) {
                ToastProvider.showShort(mParentContext, e.getMessage());
            }
            Navigation.findNavController(mCardView).navigate(
                    R.id.navigation_action_devices_to_devices_edit);
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
        mTransition.addFadeTarget(mParentScheduleView.getViewInstance());
        mTransition.addFadeTarget(mParentNetworkView.getViewInstance());
    }

    private void setCompactView() {
        mExpandOrLooseButton.setImageDrawable(
                DrawablesProvider.getDrawable(R.drawable.ic_button_expand));
        mCombinedStatusView.displayCompact();
        mSettingsButton.setVisibility(View.GONE);
        mParentScheduleView.setVisibility(View.GONE);
        mParentNetworkView.setVisibility(View.GONE);
        mHiddenContent.setVisibility(View.GONE);
    }

    private void setLooseView() {
        mExpandOrLooseButton.setImageDrawable(
                DrawablesProvider.getDrawable(R.drawable.ic_button_loose));
        mCombinedStatusView.displayDetailed();
        mSettingsButton.setVisibility(View.VISIBLE);
        mParentScheduleView.setVisibility(View.VISIBLE);
        mParentNetworkView.setVisibility(View.VISIBLE);
        mHiddenContent.setVisibility(View.VISIBLE);
    }
}
