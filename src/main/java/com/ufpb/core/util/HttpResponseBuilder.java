package com.ufpb.core.util;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder {

    private String statusline = new String();
    private Map<String, String> headers = new HashMap<>();
    private String payload;
    public HttpResponseBuilder() {
        statusline = String.format("HTTP/1.1 %s\r\n", Status.OK.toString());
    }

    public HttpResponseBuilder setStatus(Status status) {
        statusline  = String.format("HTTP/1.1 %s\r\n", status.toString());
        return this;
    }

    public HttpResponseBuilder addHeader(String chave, String valor) {
        headers.put(chave, valor);
        return this;
    }

    public HttpResponseBuilder setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append(statusline);
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
        }
        sb.append("\r\n");
        sb.append(payload.toString());
        return sb.toString();
    }
}