package michael.linker.rewater.util.communication;

import android.os.Bundle;

/**
 * Parametrized Bundle
 *
 * @param <T> type of the model
 */
public interface IBundle<T> {
    /**
     * Pack data from model to the bundle.
     *
     * @param model model with data
     * @return bundle with packed data
     */
    Bundle pack(T model);

    /**
     * Unpack data from bundle.
     *
     * @param bundle bundle with packed data
     * @return model with data
     */
    T unpack(final Bundle bundle);
}