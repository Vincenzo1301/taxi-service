package edu.hm.network.group12.client.impl;

public class LampClientResponse {

    /**
     * The HTTP response code.
     */
    private final int responseCode;

    /**
     * The message of the response.
     */
    private final String responseMessage;

    public LampClientResponse(int responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    @Override
    public String toString() {
        return responseCode + ": " + responseMessage;
    }
}
