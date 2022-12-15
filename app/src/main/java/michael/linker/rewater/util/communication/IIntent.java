package michael.linker.rewater.util.communication;

import android.content.Intent;

/**
 * Parametrized Intent
 *
 * @param <T> type of the model
 */
public interface IIntent<T> {
    /**
     * Pack data from model to the bundle.
     *
     * @param model model with data
     * @param intent storage for data
     * @return intent with packed data
     */
    Intent pack(T model, Intent intent);

    /**
     * Unpack data from intent.
     *
     * @param intent intent with packed data
     * @return model with data
     */
    T unpack(final Intent intent);
}