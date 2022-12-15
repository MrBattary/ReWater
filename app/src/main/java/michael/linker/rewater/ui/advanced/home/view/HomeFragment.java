package michael.linker.rewater.ui.advanced.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.ui.advanced.home.adapter.HomeHistoryItemAdapter;
import michael.linker.rewater.ui.advanced.home.viewmodel.HomeViewModel;
import michael.linker.rewater.ui.elementary.chart.IChart;
import michael.linker.rewater.ui.elementary.chart.StatusPieChart;
import michael.linker.rewater.ui.elementary.chart.model.PieChartLookModel;
import michael.linker.rewater.ui.elementary.chart.model.PieChartStatusDataModel;
import michael.linker.rewater.ui.elementary.text.status.IStatusStyledTextView;
import michael.linker.rewater.ui.elementary.text.status.StatusStyledColoredTextView;

public class HomeFragment extends Fragment {
    private static final PageSizeCommonRequest
            HOME_HISTORY_PAGINATION_REQUEST = new PageSizeCommonRequest(0, 5);
    private TextView mUsernameTextView, mNetworksChartNotFound, mDevicesChartNotFound;
    private IStatusStyledTextView mSummaryStatusTextView;
    private ViewGroup mHistoryEventsNotFoundPlaceholder;
    private IChart<PieChartStatusDataModel> mNetworksPieChart, mDevicesPieChart;
    private RecyclerView mHistoryEventsRecyclerView;

    private HomeViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        final NavController navController = NavHostFragment.findNavController(this);
        final ViewModelStoreOwner currentViewModelStoreOwner = navController
                .getViewModelStoreOwner(R.id.root_navigation_home);
        mViewModel = new ViewModelProvider(currentViewModelStoreOwner).get(HomeViewModel.class);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initFieldsData();
    }

    private void initFields(final View view) {
        mUsernameTextView = view.findViewById(R.id.home_greeting_username);
        mSummaryStatusTextView = new StatusStyledColoredTextView(
                view.findViewById(R.id.home_greeting_summary_status));
        mNetworksChartNotFound = view.findViewById(R.id.home_charts_networks_not_found);
        mNetworksPieChart = new StatusPieChart(
                view.findViewById(R.id.home_charts_networks_chart),
                new PieChartLookModel(R.string.chart_networks_placeholder)
        );
        mDevicesChartNotFound = view.findViewById(R.id.home_charts_devices_not_found);
        mDevicesPieChart = new StatusPieChart(
                view.findViewById(R.id.home_charts_devices_chart),
                new PieChartLookModel(R.string.chart_devices_placeholder)
        );
        mHistoryEventsNotFoundPlaceholder = view.findViewById(R.id.home_history_events_not_found);
        mHistoryEventsRecyclerView = view.findViewById(R.id.home_history_events);
    }

    private void initFieldsData() {
        mViewModel.getProfileModel().observe(getViewLifecycleOwner(),
                model -> mUsernameTextView.setText(model.getUsername()));
        mViewModel.getNetworksOverallStatus().observe(getViewLifecycleOwner(), status -> {
            if (status.equals(Status.OK) || status.equals(Status.UNDEFINED)) {
                mSummaryStatusTextView.setText(
                        StringsProvider.getString(R.string.summary_status_fine), Status.OK);
            }
            if (status.equals(Status.WARNING)) {
                mSummaryStatusTextView.setText(
                        StringsProvider.getString(R.string.summary_status_warning), Status.WARNING);
            }
            if (status.equals(Status.DEFECT)) {
                mSummaryStatusTextView.setText(
                        StringsProvider.getString(R.string.summary_status_defect), Status.DEFECT);
            }
        });
        mViewModel.getHistoryList().observe(getViewLifecycleOwner(), historyModels -> {
            if (historyModels.size() > 0) {
                mHistoryEventsRecyclerView.setLayoutManager(
                        new LinearLayoutManager(requireContext()));
                mHistoryEventsRecyclerView.swapAdapter(
                        new HomeHistoryItemAdapter(requireContext(), historyModels), false);
                mHistoryEventsRecyclerView.setVisibility(View.VISIBLE);
                mHistoryEventsNotFoundPlaceholder.setVisibility(View.GONE);
            } else {
                mHistoryEventsRecyclerView.setVisibility(View.GONE);
                mHistoryEventsNotFoundPlaceholder.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getNetworkStatusChartDataModel().observe(getViewLifecycleOwner(),
                chartData -> {
                    if (chartData.getDataSize() > 0) {
                        mNetworksPieChart.setDataModel(
                                new PieChartStatusDataModel(
                                        StringsProvider.getString(
                                                R.string.chart_networks_center_text),
                                        chartData
                                ));
                        mNetworksPieChart.setVisibility(View.VISIBLE);
                        mNetworksChartNotFound.setVisibility(View.GONE);
                    } else {
                        mNetworksPieChart.setVisibility(View.GONE);
                        mNetworksChartNotFound.setVisibility(View.VISIBLE);
                    }
                });
        mViewModel.getDeviceStatusChartDataModel().observe(getViewLifecycleOwner(),
                chartData -> {
                    if (chartData.getDataSize() > 0) {
                        mDevicesPieChart.setDataModel(
                                new PieChartStatusDataModel(
                                        StringsProvider.getString(
                                                R.string.chart_devices_center_text),
                                        chartData
                                ));
                        mDevicesPieChart.setVisibility(View.VISIBLE);
                        mDevicesChartNotFound.setVisibility(View.GONE);
                    } else {
                        mDevicesPieChart.setVisibility(View.GONE);
                        mDevicesChartNotFound.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.requireProfileUpdate();
        mViewModel.requireNetworksUpdate();
        mViewModel.requireDevicesUpdate();
        mViewModel.requireHistoryUpdate(HOME_HISTORY_PAGINATION_REQUEST);
    }
}