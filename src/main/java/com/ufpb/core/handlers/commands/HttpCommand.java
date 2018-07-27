package com.ufpb.core.handlers.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.ufpb.core.util.HttpResponseBuilder;

public abstract class HttpCommand {
    protected OutputStream os;
    protected HttpResponseBuilder builder;

    protected HttpCommand(OutputStream os) {
        builder = new HttpResponseBuilder();
        this.os = os;
    }

    protected String getServerTime() {
        Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(calendar.getTime());
    }

    public abstract void executa() throws FileNotFoundException, IOException;
}