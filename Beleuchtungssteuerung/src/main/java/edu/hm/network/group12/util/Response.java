package edu.hm.network.group12.util;

public class Response {

    /**
     * The HTTP response code.
     */
    private final int responseCode;

    /**
     * The message of the response.
     */
    private final String responseMessage;

    public Response(int responseCode, String responseMessage) {
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
