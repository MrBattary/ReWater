package michael.linker.rewater.ui.navigation;

import michael.linker.rewater.constant.Status;

public interface INavigation {
    Status getSummaryStatusFromHomeItem();

    void setSummaryStatusForHomeItem(Status status) throws NavigationFaultException;
}
