package michael.linker.rewater.ui.util;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class LiveDataUtils {
    public static class TwoCombinedLiveData<A,B> extends MediatorLiveData<Pair<A, B>> {
        private A a = null;
        private B b = null;

        public TwoCombinedLiveData(LiveData<A> ld1, LiveData<B> ld2) {
            setValue(Pair.create(a, b));

            addSource(ld1, (a) -> {
                if(a != null) {
                    this.a = a;
                }
                setValue(Pair.create(a, b));
            });

            addSource(ld2, (b) -> {
                if(b != null) {
                    this.b = b;
                }
                setValue(Pair.create(a, b));
            });
        }
    }
}
