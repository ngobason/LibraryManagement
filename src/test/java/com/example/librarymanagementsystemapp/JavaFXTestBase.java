package com.example.librarymanagementsystemapp;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public abstract class JavaFXTestBase {

    private static boolean javafxStarted = false;

    @BeforeAll
    static void initJfxRuntime() {
        if (!javafxStarted) {
            Platform.startup(() -> {});
            javafxStarted = true;
        }
    }
}
