package cipherMethod;

public class FactoryCipher {

    private static FactoryCipher instance;

    public static FactoryCipher getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new FactoryCipher();
                }
            }
        }
        return instance;
    }

    public ICipherMethod makeCipher(String cipherType) {
        switch (cipherType) {
            case "AtBash":
                return new AtBash();
            case "Cesar":
                return new Cesar();
            case "Matrix":
                return new Matrix();
            case "ROT-13":
                return new Rot13();
            case "Vigenere":
                return new Vigenere();
            default: {
                return null;
            }
        }
    }
}
