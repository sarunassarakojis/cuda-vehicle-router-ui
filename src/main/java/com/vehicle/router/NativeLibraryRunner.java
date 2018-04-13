package com.vehicle.router;

public class NativeLibraryRunner {

    static {
        System.loadLibrary("hello");
    }

    public native void helloNative();
}
