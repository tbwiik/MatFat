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

    // Allowed with empty box
    public TagBox(Set<String> tags) throws IllegalTagFormatException {

        setTagsSet(tags);

    }

    // (Can) be used in pretty much every class
    public TagBox() {
    }

    private void checkTag(String tag) throws IllegalTagFormatException {
        if (tag.length() < MIN_LENGTH_TAG)
            throw new IllegalTagFormatException("Too short tag");

        if (tag.length() > MAX_LENGTH_TAG)
            throw new IllegalTagFormatException("Too long tag");

        boolean iNameMatch = Pattern.matches(NAMEREG_STRING, tag);

        if (!iNameMatch)
            throw new IllegalTagFormatException("Tag can only consist of chars");
    }

    private void setTagsSet(Set<String> tags) throws IllegalTagFormatException {

        if (tags.isEmpty()) {
            this.tags = tags;
        } else {
            tags.forEach((tag) -> {
                checkTag(tag);
                this.tags.add(tag);
            });
        }
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    public void retainAll(Set<String> tags) {
        this.tags.retainAll(tags);
    }

    public void addTag(String tag) throws IllegalTagFormatException {
        checkTag(tag);
        tags.add(tag);
    }

    public void addTags(Set<String> tags) throws IllegalTagFormatException {
        tags.forEach((tag) -> addTag(tag));
    }

    public void removeTag(String tag) throws IllegalArgumentException {
        if (!tags.contains(tag))
            throw new IllegalArgumentException("Tag not in box");
        tags.remove(tag);
    }

    public void removeTags(Set<String> tags) throws IllegalArgumentException {
        for (String tag : tags) {
            removeTag(tag);
        }
    }

    @Override
    public String toString() {

        StringBuilder sBuilder = new StringBuilder();

        tags.forEach((tag) -> sBuilder.append(tag + " "));

        return sBuilder.toString();
    }

}