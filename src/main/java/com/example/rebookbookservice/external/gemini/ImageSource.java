package com.example.rebookbookservice.external.gemini;


public record ImageSource(byte[] bytes, String mimeType) {

    public static ImageSource of(byte[] bytes, String mimeType) {
        return new ImageSource(bytes, mimeType);
    }
}