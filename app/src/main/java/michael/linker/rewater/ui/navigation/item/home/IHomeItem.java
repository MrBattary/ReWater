package michael.linker.rewater.ui.navigation.item.home;

import michael.linker.rewater.type.Status;
import michael.linker.rewater.ui.navigation.NavigationFaultException;

public interface IHomeItem {
    Status getSummaryStatus();

    void setSummaryStatus(Status newStatus) throws NavigationFaultException;
}
