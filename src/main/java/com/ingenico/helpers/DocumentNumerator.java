package com.ingenico.helpers;

public class DocumentNumerator extends BaseNumerator{
    private static DocumentNumerator documentNumerator = null;

    private DocumentNumerator(){
        Counter = 1;
    }

    public static DocumentNumerator getNumerator(){
        if(documentNumerator == null)
            documentNumerator = new DocumentNumerator();
        return documentNumerator;
    }
}
