package cipherMethod;

public class Singleton {
    private static Singleton instance;

    public Singleton() {
    }

    private Singleton getInstance() {
        if(instance == null) {
            synchronized (Singleton.class){
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
