package com.firework.Modules.Internet;

/**
 * Created by Slava on 03.03.2017.
 */

public class JSONParseException extends Exception {
    public JSONParseException(String message)
    {
        super(message);
    }

    public JSONParseException()
    {
        super("JSON Data Parse exception!");
    }
}
