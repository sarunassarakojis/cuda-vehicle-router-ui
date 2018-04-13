package com.vehicle.router;

public class NativeLibraryRunner {

    static {
        System.loadLibrary("cuda-vehicle-router-lib");
    }

    public native void helloNative();
}
