package com.ingenico.helpers;

public class ProfileNumerator extends BaseNumerator{
    private static ProfileNumerator profileNumerator = null;

    private ProfileNumerator(){
        Counter = 1;
    }

    public static ProfileNumerator getNumerator(){
        if(profileNumerator == null)
            profileNumerator = new ProfileNumerator();
        return profileNumerator;
    }
}
