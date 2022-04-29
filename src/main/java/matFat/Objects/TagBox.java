package matFat.Objects;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import matFat.exceptions.IllegalTagFormatException;

public class TagBox {

    private Set<String> tags = new HashSet<>();

    private final static String NAMEREG_STRING = "[a-zA-ZæøåÆØÅ]*";
    private final static int MIN_LENGTH_TAG = 3;
    private final static int MAX_LENGTH_TAG = 10;

    /**
     * Construct box with tags
     * <p>
     * Empty box is valid
     * 
     * @param tags
     * @throws IllegalTagFormatException if unvalid format on tags per
     *                                   {@linkplain #checkTag(String)}
     */
    public TagBox(Set<String> tags) throws IllegalTagFormatException {

        setTagsSet(tags);

    }

    /**
     * Empty constructor used for initalizing in other classes
     * <p>
     * Ensures that toString and other functions don't break the program
     */
    public TagBox() {
    }

    /**
     * Check that tag is in correct format
     * 
     * @param tag
     * @throws IllegalTagFormatException if invalid format on tag
     */
    private void checkTag(String tag) throws IllegalTagFormatException {

        if (tag.length() < MIN_LENGTH_TAG)
            throw new IllegalTagFormatException("Too short tag");

        if (tag.length() > MAX_LENGTH_TAG)
            throw new IllegalTagFormatException("Too long tag");

        boolean iNameMatch = Pattern.matches(NAMEREG_STRING, tag);
        if (!iNameMatch)
            throw new IllegalTagFormatException("Tag can only consist of chars");

    }

    /**
     * Sets tags for tagBox
     * <p>
     * Empty box is valid
     * 
     * @param tags
     * @throws IllegalTagFormatException
     */
    private void setTagsSet(Set<String> tags) throws IllegalTagFormatException {

        Set<String> tmpTags = new HashSet<>();
        // Could also use filter
        tags.forEach((tag) -> {
            if (!tag.isBlank())
                tmpTags.add(tag);
        });

        if (tmpTags.isEmpty()) {
            this.tags = tmpTags;

        } else {
            tmpTags.forEach((tag) -> {
                checkTag(tag);
                this.tags.add(tag);
            });
        }
    }

    /**
     * Add tag to tagBox
     * 
     * @param tag
     * @throws IllegalTagFormatException if unvalid per
     *                                   {@linkplain #checkTag(String)}
     */
    public void addTag(String tag) throws IllegalTagFormatException {
        checkTag(tag);
        tags.add(tag);
    }

    /**
     * Add tags to box using {@linkplain #addTag(String)}
     * 
     * @param tags
     * @throws IllegalTagFormatException
     */
    public void addTags(Set<String> tags) throws IllegalTagFormatException {
        tags.forEach((tag) -> addTag(tag));
    }

    /**
     * Remove tag from box
     * 
     * @param tag
     * @throws IllegalArgumentException if removing tag not in box
     */
    public void removeTag(String tag) throws IllegalArgumentException {

        if (!tags.contains(tag))
            throw new IllegalArgumentException("Tag not in box");
        tags.remove(tag);

    }

    /**
     * Remove tags in box
     * 
     * @param tags
     * @throws IllegalArgumentException if removing tag not in box
     */
    public void removeTags(Set<String> tags) throws IllegalArgumentException {
        for (String tag : tags) {
            removeTag(tag);
        }
    }

    /**
     * @return tags in box
     */
    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Add intersection of tags added and tags already in box
     * 
     * @param tags
     */
    public void retainAll(Set<String> tags) {
        this.tags.retainAll(tags);
    }

    @Override
    public String toString() {

        StringBuilder sBuilder = new StringBuilder();

        tags.forEach((tag) -> sBuilder.append(tag + " "));

        return sBuilder.toString();
    }

}
