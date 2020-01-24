package distance.learning.common;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class ResourceLoader {
    private Map<String, Image> loadedImages = new HashMap<>();

    public Image loadImage(String name) {
        if (loadedImages.containsKey(name)) {
            return loadedImages.get(name);
        }
        var image = new Image(getClass().getResourceAsStream(format("/icons/%s.png", name)));
        loadedImages.put(name, image);
        return image;
    }
}
