package edu.school21;

import org.springframework.stereotype.Component;

//@Component
public class ClassicalMusic implements Music {
    public ClassicalMusic() {}

    public static ClassicalMusic getClassicalMusic() {
        return new ClassicalMusic();
    }

    @Override
    public String getSong() {
        return "Beethoven symphony number 5";
    }
}
