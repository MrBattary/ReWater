package michael.linker.rewater.ui.advanced.navigation.home;

import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.ui.advanced.navigation.NavigationFaultException;

public interface IHomeItem {
    Status getSummaryStatus();

    void setSummaryStatus(Status newStatus) throws NavigationFaultException;
}
