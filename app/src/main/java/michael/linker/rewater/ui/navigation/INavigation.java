package michael.linker.rewater.ui.navigation;

import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import michael.linker.rewater.constant.Status;

/**
 * Wrapper around application navigation
 */
public interface INavigation {
    /**
     * Get the current summary status for a home item.
     *
     * @return on of the possible statuses
     */
    Status getSummaryStatusFromHomeItem();

    /**
     * Set the current summary status for a home item.
     *
     * @param status the new status
     * @throws NavigationFaultException if an error occurred during status setting
     */
    void setSummaryStatusForHomeItem(Status status) throws NavigationFaultException;

    /**
     * Get the internal app bar configuration.
     *
     * @return AppBarConfiguration instance
     */
    AppBarConfiguration getAppBarConfiguration();

    /**
     * Get the internal navigation controller.
     *
     * @return NavController instance
     */
    NavController getNavController();
}
