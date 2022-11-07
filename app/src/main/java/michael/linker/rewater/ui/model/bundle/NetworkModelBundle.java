package michael.linker.rewater.ui.model.bundle;

import android.os.Bundle;

import michael.linker.rewater.data.model.FullNetworkModel;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.ui.model.DetailedStatusModel;

public class NetworkModelBundle implements IBundle<FullNetworkModel> {
    private static final String ID_KEY = "id";
    private static final String ID_HEADING = "heading";
    private static final String ID_DESCRIPTION = "description";
    private static final String ID_WATER = "water";
    private static final String ID_ENERGY = "energy";

    @Override
    public Bundle pack(final FullNetworkModel model) {
        Bundle bundle = new Bundle();
        bundle.putString(ID_KEY, model.getId());
        bundle.putString(ID_HEADING, model.getHeading());
        bundle.putString(ID_DESCRIPTION, model.getDescription());
        bundle.putString(ID_WATER, model.getStatus().getWater().toString());
        bundle.putString(ID_ENERGY, model.getStatus().getEnergy().toString());
        return bundle;
    }

    @Override
    public FullNetworkModel unpack(final Bundle bundle) {
        return new FullNetworkModel(
                bundle.getString(ID_KEY),
                bundle.getString(ID_HEADING),
                bundle.getString(ID_DESCRIPTION),
                new DetailedStatusModel(
                        Status.valueOf(bundle.getString(ID_WATER)),
                        Status.valueOf(bundle.getString(ID_ENERGY))));
    }
}
