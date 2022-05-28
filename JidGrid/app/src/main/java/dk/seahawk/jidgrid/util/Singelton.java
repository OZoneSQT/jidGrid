package dk.seahawk.jidgrid.util;

// https://stackoverflow.com/questions/18393600/android-singleton-becomes-null
public class Singelton {
    private static Singelton instance = null;

    private Singelton() {}

    public static Singelton getInstance() {
        if (instance == null) instance = new Singelton();
        return instance;
    }

    public void doSomething() {}

}

// https://subscription.packtpub.com/book/web-development/9781786467218/1/ch01lvl1sec11/the-factory-pattern