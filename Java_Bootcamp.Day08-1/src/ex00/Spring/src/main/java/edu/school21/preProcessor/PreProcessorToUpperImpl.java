package edu.school21.preProcessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String process(String message) {
        return message.toUpperCase();
    }
}
