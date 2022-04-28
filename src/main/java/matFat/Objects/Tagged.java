package matFat.Objects;

import java.util.Set;

import matFat.exceptions.IllegalTagFormatException;

public abstract class Tagged {

    // Initialize empty tagbox
    protected TagBox tagBox = new TagBox();

    /**
     * Add tag to tag box
     * 
     * @param tag
     * @throws IllegalTagFormatException if unvalid format on tag
     */
    public void addTag(String tag) throws IllegalTagFormatException {
        tagBox.addTag(tag);
    }

    /**
     * Add tags to tag box
     * 
     * @param tags
     * @throws IllegalTagFormatException if unvalid format on tag
     */
    public void addTags(Set<String> tags) throws IllegalTagFormatException {
        tags.forEach((tag) -> addTag(tag));
    }

    /**
     * Removes tag from tag box
     * 
     * @param tag
     * @throws IllegalArgumentException if tag not in box
     */
    public void removeTag(String tag) throws IllegalArgumentException {
        tagBox.removeTag(tag);
    }

    /**
     * Removes tags from box
     * 
     * @param tags
     * @throws IllegalArgumentException if removing tag not in box
     */
    public void removeTags(Set<String> tags) throws IllegalArgumentException {
        tagBox.removeTags(tags);
    }

}
