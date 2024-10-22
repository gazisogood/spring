package edu.school21.preProcessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String process(String message) {
        return message.toLowerCase();
    }
}
