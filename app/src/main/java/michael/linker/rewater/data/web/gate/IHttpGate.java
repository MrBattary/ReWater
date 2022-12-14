package michael.linker.rewater.data.web.gate;

import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;
import michael.linker.rewater.data.web.gate.exceptions.group.ClientErrorException;
import michael.linker.rewater.data.web.gate.exceptions.group.ServerErrorException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.ForbiddenHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.ServerErrorHttpException;
import okhttp3.Response;

public interface IHttpGate {
    /**
     * Makes HTTP GET request to the provided URL.
     *
     * @param url url
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response get(final String url) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Makes HTTP GET request to the provided URL combined with settings URL.
     *
     * @param url url
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response getWithSettings(final String url) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Makes HTTP POST request to the provided URL with provided JSON body.
     *
     * @param url  url
     * @param json Json object as String
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response post(String url, String json) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Makes HTTP POST request to the provided URL combined with settings URL
     * and provided JSON body.
     *
     * @param url  url
     * @param json Json object as String
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response postWithSettings(String url, String json) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Makes HTTP PUT request to the provided URL with provided JSON body.
     *
     * @param url  url
     * @param json Json object as String
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response put(String url, String json) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Makes HTTP PUT request to the provided URL combined with settings URL
     * and provided JSON body.
     *
     * @param url  url
     * @param json Json object as String
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response putWithSettings(String url, String json) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Makes HTTP DELETE request to the provided URL.
     *
     * @param url url
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response delete(String url) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Makes HTTP DELETE request to the provided URL combined with settings URL.
     *
     * @param url url
     * @return response object, if response has code 200-299
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection
     * @throws FailureHttpException     Any error
     * @throws ClientErrorException     400-499 errors
     * @throws ServerErrorException     500-599 errors
     * @throws BadRequestHttpException  400
     * @throws ForbiddenHttpException   403
     * @throws NotFoundHttpException    404
     * @throws ServerErrorHttpException 500
     */
    Response deleteWithSettings(String url) throws
            HttpGateFailureException, FailureHttpException,
            ClientErrorException, ServerErrorException,
            BadRequestHttpException, ForbiddenHttpException,
            NotFoundHttpException, ServerErrorHttpException;

    /**
     * Set new settings to the http gate.
     *
     * @param newSettings model with new settings
     */
    void setNewSettings(HttpGateSettings newSettings);

    /**
     * Returns settings of the http gate.
     *
     * @return model with current settings
     */
    HttpGateSettings getSettings();

    /**
     * Returns status of the http gate.
     *
     * @return status object
     */
    HttpGateStatusObserver getStatusObserver();
}
