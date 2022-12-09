package michael.linker.rewater.data.web.gate;

import java.io.IOException;

import michael.linker.rewater.config.ConnectionConfiguration;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;
import michael.linker.rewater.data.web.gate.exceptions.group.ClientErrorException;
import michael.linker.rewater.data.web.gate.exceptions.group.ServerErrorException;
import michael.linker.rewater.data.web.gate.exceptions.group.UnrecognizedErrorException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.ForbiddenHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.ServerErrorHttpException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpGate implements IHttpGate {
    private final OkHttpClient mOkHttpClient;
    private final HttpGateStatusObserver mStatus;
    private HttpGateSettings mSettings;

    protected HttpGate(final HttpGateSettings settings) {
        mOkHttpClient = ConnectionConfiguration.getOkHttpClient();
        mStatus = new HttpGateStatusObserver();
        mSettings = settings;
    }

    @Override
    public Response get(final String url) throws FailureHttpException {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    @Override
    public Response getWithSettings(String url)
            throws FailureHttpException {
        return get(mSettings.getConstructedUrl() + url);
    }

    @Override
    public Response post(final String url, final String json) throws FailureHttpException {
        RequestBody requestBody = RequestBody.create(json, HttpGateMediaType.JSON);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    @Override
    public Response postWithSettings(String url, String json) throws FailureHttpException {
        return post(mSettings.getConstructedUrl() + url, json);
    }

    @Override
    public Response put(final String url, final String json) throws FailureHttpException {
        RequestBody requestBody = RequestBody.create(json, HttpGateMediaType.JSON);
        Request request = new Request.Builder()
                .put(requestBody)
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    @Override
    public Response putWithSettings(String url, String json)
            throws FailureHttpException {
        return put(mSettings.getConstructedUrl() + url, json);
    }

    @Override
    public Response delete(final String url) throws FailureHttpException {
        Request request = new Request.Builder()
                .delete()
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    @Override
    public Response deleteWithSettings(String url) throws FailureHttpException {
        return delete(mSettings.getConstructedUrl() + url);
    }

    @Override
    public void setNewSettings(HttpGateSettings newSettings) {
        mSettings = newSettings;
    }

    @Override
    public HttpGateSettings getSettings() {
        return mSettings;
    }

    @Override
    public HttpGateStatusObserver getStatusObserver() {
        return mStatus;
    }

    private Response makeRequest(Request request) throws FailureHttpException {
        try {
            return mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            mStatus.notifyInternetNotAccessible();
            throw new FailureHttpException();
        }
    }

    private void validateResponse(Response response) throws
            UnrecognizedErrorException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException {
        HttpResponseStatus responseStatus = HttpResponseStatus.recognizeStatusByCode(
                response.code());

        if (!HttpResponseStatusGroup.isStatusSuccess(responseStatus)) {
            response.close();
            if (HttpResponseStatusGroup.isStatusClientError(responseStatus)) {
                if (responseStatus.getCode() == HttpResponseStatus.BAD_REQUEST_CODE) {
                    throw new BadRequestHttpException();
                }
                if (responseStatus.getCode() == HttpResponseStatus.FORBIDDEN_CODE) {
                    throw new ForbiddenHttpException();
                }
                if (responseStatus.getCode() == HttpResponseStatus.NOT_FOUND_CODE) {
                    throw new NotFoundHttpException();
                }
                throw new ClientErrorException();
            }
            if (HttpResponseStatusGroup.isStatusServerError(responseStatus)) {
                if (responseStatus.getCode() == HttpResponseStatus.SERVER_ERROR_CODE) {
                    throw new ServerErrorHttpException();
                }
                throw new ServerErrorException();
            }
            throw new UnrecognizedErrorException();
        }
    }
}
