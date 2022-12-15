package michael.linker.rewater.data.local.stub;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.local.stub.model.FullDeviceModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;

public class DevicesLocalData implements IDevicesData {
    private final List<FullDeviceModel> mFullDeviceModels;

    public DevicesLocalData() {
        mFullDeviceModels = new ArrayList<>();
        mFullDeviceModels.add(new FullDeviceModel(
                "a2726b77-2214-4aa2-b4af-2fac0a4f84bc",
                "RWD-2214",
                new DetailedStatusModel(Status.OK, Status.WARNING)));
        mFullDeviceModels.add(new FullDeviceModel(
                "832b9239-b362-47c1-bfbc-96589bcad643",
                "Diffenbachia",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "13fea3f4-3619-4ec9-bba7-4e1bef39157f",
                "RWD-3619",
                new DetailedStatusModel(Status.WARNING, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "86cd66a7-9179-415e-b437-f772a21cc4d7",
                "RWD-9179",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "794fa505-1cdc-480e-b198-e062a36e8fa7",
                "Rosary",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "8753e736-ccf0-4352-94e3-ee9273df6570",
                "Rosaline",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "13b187b9-207e-4b05-9a80-cfbe4d23d43d",
                "Ficus-1",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "05cf7d59-fd14-4c26-bbb9-415a7ae27141",
                "Ficus-2",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "353f5967-c7bd-4c57-8528-4f1ab7b88005",
                "Blue leaf",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "691f0988-5b72-487c-a1ed-ea8aa1aafdb0",
                "Red leaf",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "de5f1c7b-f792-4ff2-9d29-9b6fe1257047",
                "LARGE Begonia",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "21bef4a5-c1c1-4588-b2c9-7f8188c89db4",
                "small Begonia",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "c8000897-b41c-4da8-9c10-64ad47426bb8",
                "Tomato 1",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "0b4cc0a7-86eb-405b-953d-ec3229e4c761",
                "Tomato 2",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "454a35af-cfc1-4080-836c-d226647c021f",
                "Tomato 3",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "25b7d648-1baa-4b96-9af4-32c25d4c94b5",
                "Cucumber 1",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "00b62eae-87c7-43d6-9ca9-2035bc5eec94",
                "Cucumber 2",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "b671a753-e8ea-4cd1-b16a-0b8032e58e63",
                "Cabbage 1",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "dad09ddb-667b-4d59-ac15-fedb31cc5318",
                "Cabbage 2",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "88ea0329-9f59-42a0-a7cf-662dffc7febb",
                "Cabbage 3",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "a3367fa7-b9d8-465f-aeea-8a343b170906",
                "Cabbage 4",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "0bbad079-252d-497c-9b4d-7d49fafb675a",
                "Emptiness",
                new DetailedStatusModel(Status.OK, Status.OK)));
    }

    @Override
    public List<FullDeviceModel> getDevicesList() {
        return new ArrayList<>(mFullDeviceModels);
    }

    @Override
    public FullDeviceModel getDeviceById(final String id) {
        for (FullDeviceModel deviceModel : mFullDeviceModels) {
            if (id.equals(deviceModel.getId())) {
                return deviceModel;
            }
        }
        return null;
    }

    @Override
    public void removeDeviceById(final String id) {
        for (int i = 0; i < mFullDeviceModels.size(); i++) {
            if (id.equals(mFullDeviceModels.get(i).getId())) {
                mFullDeviceModels.remove(i);
                return;
            }
        }
    }

    @Override
    public void updateDevice(final String id, final FullDeviceModel model) {
        for (int i = 0; i < mFullDeviceModels.size(); i++) {
            if (id.equals(mFullDeviceModels.get(i).getId())) {
                mFullDeviceModels.set(i, new FullDeviceModel(
                        id,
                        model.getName(),
                        model.getStatus()));
                return;
            }
        }
    }

    @Override
    public void addDevice(final FullDeviceModel model) {
        mFullDeviceModels.add(new FullDeviceModel(
                model.getId(),
                model.getName(),
                new DetailedStatusModel(Status.OK, Status.OK)
        ));
    }
}
