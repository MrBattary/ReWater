package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.stream.Collectors;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.DetailedStatusModel;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.ui.advanced.schedules.adapter.ScheduleDevicesItemAdapter;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.UpdateScheduleViewModel;
import michael.linker.rewater.ui.animation.transition.IOrderedTransition;
import michael.linker.rewater.ui.elementary.status.CombinedStatusView;

public class ScheduleCardView {
    private final Context mParentContext;
    private final CardView mCardView;
    private final TextView mName, mPeriod, mVolume;
    private final CombinedStatusView mCombinedStatusView;
    private final ImageButton mExpandOrLooseButton;
    private final ViewGroup mHiddenContent;
    private final RecyclerView mAttachedDevicesRecyclerView;
    private final Button mSettingsButton;
    private final IOrderedTransition mTransition;
    private ScheduleUiModel mDataModel;

    public ScheduleCardView(
            final Context context,
            final View view,
            final UpdateScheduleViewModel childViewModel,
            final IOrderedTransition transition
    ) {
        mParentContext = context;
        mCardView = view.findViewById(R.id.schedule_card);
        mName = view.findViewById(R.id.schedule_card_name);
        mPeriod = view.findViewById(R.id.schedule_card_period);
        mVolume = view.findViewById(R.id.schedule_card_volume);
        mCombinedStatusView = new CombinedStatusView(
                view.findViewById(R.id.schedule_card_combined_status));
        mExpandOrLooseButton = view.findViewById(R.id.schedule_card_expand_or_loose_button);
        mHiddenContent = view.findViewById(R.id.schedule_card_hidden_content);
        mAttachedDevicesRecyclerView = view.findViewById(R.id.schedule_card_devices);
        mSettingsButton = view.findViewById(R.id.schedule_card_settings_button);
        mTransition = transition;

        transition.addChangeBoundsTarget(view);
        this.initTransitionTargets();
        this.setCompactView();
        this.initButtonsLogic(childViewModel);
    }

    public void setUiModel(final ScheduleUiModel model) {
        mDataModel = model;

        // Regular data
        mName.setText(model.getName());
        mPeriod.setText(model.getPeriod().formatToCompact());
        mVolume.setText(model.getVolume().formatToCompact());

        // Attached devices list
        mAttachedDevicesRecyclerView.setLayoutManager(new LinearLayoutManager(mParentContext));
        mAttachedDevicesRecyclerView.setAdapter(
                new ScheduleDevicesItemAdapter(mParentContext, model.getDeviceModels()));

        // Calculate status from the attached devices
        final Status batteryWorstStatus = Status.getWorstStatus(model.getDeviceModels().stream()
                .map(deviceModel -> deviceModel.getStatus().getBattery())
                .collect(Collectors.toList()));
        final Status waterWorstStatus = Status.getWorstStatus(model.getDeviceModels().stream()
                .map(deviceModel -> deviceModel.getStatus().getWater())
                .collect(Collectors.toList()));
        mCombinedStatusView.setStatus(
                new DetailedStatusModel(waterWorstStatus, batteryWorstStatus));
    }

    private void initButtonsLogic(final UpdateScheduleViewModel childViewModel) {
        initExpandOrLooseButtonLogic();
        initSettingsButtonLogic(childViewModel);
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

    private void initSettingsButtonLogic(final UpdateScheduleViewModel childViewModel) {
        mSettingsButton.setOnClickListener(l -> {
            childViewModel.setScheduleIdAndRefreshViewModel(mDataModel.getId());
            // TODO: Replace to edit
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
        mTransition.addFadeTarget(mPeriod);
        mTransition.addFadeTarget(mVolume);
        mTransition.addFadeTarget(mAttachedDevicesRecyclerView);
    }

    private void setCompactView() {
        mExpandOrLooseButton.setImageDrawable(
                DrawablesProvider.getDrawable(R.drawable.ic_button_expand));
        mCombinedStatusView.displayCompact();
        mSettingsButton.setVisibility(View.GONE);
        mPeriod.setVisibility(View.GONE);
        mVolume.setVisibility(View.GONE);
        mAttachedDevicesRecyclerView.setVisibility(View.GONE);
        mHiddenContent.setVisibility(View.GONE);
    }

    private void setLooseView() {
        mExpandOrLooseButton.setImageDrawable(
                DrawablesProvider.getDrawable(R.drawable.ic_button_loose));
        mCombinedStatusView.displayDetailed();
        mSettingsButton.setVisibility(View.VISIBLE);
        mPeriod.setVisibility(View.VISIBLE);
        mVolume.setVisibility(View.VISIBLE);
        mAttachedDevicesRecyclerView.setVisibility(View.VISIBLE);
        mHiddenContent.setVisibility(View.VISIBLE);
    }
}
