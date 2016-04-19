package com.qantium.uisteps.allure.storage;

import net.lightbody.bmp.core.har.Har;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Anton Solyankin
 */
public class Storage extends com.qantium.uisteps.core.storage.Storage {

    @Override
    @Step("Save {1} META[listen=false]")
    public File save(String data, String path) throws IOException {
        return super.save(data, path);
    }

    @Override
    @Step("Save {1} META[listen=false]")
    public File save(Har har, String path) throws IOException {
        attach(path, har);
        return super.save(har, path);
    }

    @Override
    @Step("Save {1} META[listen=false]")
    public File save(byte[] bytes, String path) throws IOException {
        attach(path, bytes);
        return super.save(bytes, path);
    }

    @Override
    @Step("Save {1} META[listen=false]")
    public File save(RenderedImage image, String path) throws IOException {
        return super.save(image, path);
    }


    @Step("Attach {0} META[listen=false]")
    public String attach(String name, String data) {
        return attachString(data);
    }

    @Step("Attach {0} META[listen=false]")
    public byte[] attach(String path, Har har) throws IOException {
        return attachBytes(path, Files.readAllBytes(super.save(har, path).toPath()));
    }

    @Step("Attach {0} META[listen=false]")
    public byte[] attach(String name, byte[] data) {
        return attachBytes(name, data);
    }

    @Attachment(value = "data", type = "text/plain")
    protected String attachString(String data) {
        return data;
    }

    @Attachment(value = "{0}")
    protected byte[] attachBytes(String name, byte[] data) {
        return data;
    }

}
