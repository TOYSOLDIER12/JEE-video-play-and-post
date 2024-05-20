package ma.xproce.video.service.mapper;

import ma.xproce.video.dao.enumerations.Type;

import java.util.HashMap;
import java.util.Map;

public class ReactionMiniMapper {
    private static final Map<Type, String> ICON_CLASSES = new HashMap<>();

    static {
        ICON_CLASSES.put(Type.bja9, "fa-thumbs-up");
        ICON_CLASSES.put(Type.grr, "fa-angry");
        ICON_CLASSES.put(Type.sad, "fa-sad-tear");
        ICON_CLASSES.put(Type.sba3, "fa-face-vomiting");
        ICON_CLASSES.put(Type.wahaha, "fa-laugh-beam");
    }

    public static Map<Type, String> getIconClasses() {
        return ICON_CLASSES;
    }
}
