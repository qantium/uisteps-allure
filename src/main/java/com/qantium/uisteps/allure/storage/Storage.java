package com.qantium.uisteps.allure.storage;

import com.qantium.uisteps.core.storage.Saved;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Anton Solyankin
 */
public class Storage extends com.qantium.uisteps.core.storage.Storage {

    @Override
    public File save(Saved file) {
        try {
            File savedFile = super.save(file);
            attach(savedFile);
            return savedFile;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Attachment(value = "{0}")
    protected byte[] attach(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }
}
