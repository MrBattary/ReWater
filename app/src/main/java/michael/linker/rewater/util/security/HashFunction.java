package michael.linker.rewater.util.security;

import androidx.annotation.NonNull;

import org.apache.commons.codec.digest.DigestUtils;

public final class HashFunction {
    public static final HashFunction SHA3_512;
    public static final HashFunction SHA3_256;

    private final String mHashMethodName;

    static {
        SHA3_512 = new HashFunction("SHA3-512");
        SHA3_256 = new HashFunction("SHA3-256");
    }

    public HashFunction(String hashMethodName) {
        mHashMethodName = hashMethodName;
    }

    public static String hash(final String msg, final HashFunction hashFunction) {
        if (hashFunction == SHA3_256) {
            return DigestUtils.sha3_256Hex(msg);
        }
        if (hashFunction == SHA3_512) {
            return DigestUtils.sha3_512Hex(msg);
        }
        throw new SecurityHashRuntimeException("Hash function implementation not found!");
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
