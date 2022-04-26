package matFat;

import java.util.Set;

//XXX trenger denne være abstract?
public abstract class TagBoxUser {

    // TODO hvilken synlighetmodifikator??
    TagBox tagBox;

    public void addTag(String tag) throws IllegalArgumentException {
        tagBox.addTag(tag);
    }

    public void addTags(Set<String> tags) throws IllegalArgumentException {
        tags.forEach((tag) -> addTag(tag));
    }

    public void removeTag(String tag) throws IllegalArgumentException {
        tagBox.removeTag(tag);
    }

}
