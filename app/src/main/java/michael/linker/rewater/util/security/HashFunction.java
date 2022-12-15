package michael.linker.rewater.util.security;

import androidx.annotation.NonNull;

import org.apache.commons.codec.digest.DigestUtils;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public final class HashFunction {
    public static final HashFunction SHA_512;
    public static final HashFunction SHA_256;

    private final String mHashMethodName;

    static {
        SHA_512 = new HashFunction("SHA-512");
        SHA_256 = new HashFunction("SHA-256");
    }

    public HashFunction(String hashMethodName) {
        mHashMethodName = hashMethodName;
    }

    public static String hash(final String msg, final HashFunction hashFunction)
            throws SecurityHashFunctionException {
        if (hashFunction == SHA_256) {
            return DigestUtils.sha256Hex(msg);
        }
        if (hashFunction == SHA_512) {
            return DigestUtils.sha512Hex(msg);
        }
        throw new SecurityHashFunctionException(
                StringsProvider.getString(R.string.security_failure_hash_impl_was_not_found));
    }

    public String getHashMethodName() {
        return mHashMethodName;
    }

    @NonNull
    @Override
    public String toString() {
        return mHashMethodName;
    }
}
