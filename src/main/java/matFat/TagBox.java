package matFat;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import matFat.exceptions.IllegalAmountException;

public class TagBox {

    private Set<String> tags = new HashSet<>();

    private final static String NAMEREG_STRING = "[a-zA-ZæøåÆØÅ]*";
    private final static int MIN_LENGTH_TAG = 3;
    private final static int MAX_LENGTH_TAG = 10;

    // TODO change such that all tags are lowercase and stripped
    public TagBox(String... tags) throws IllegalArgumentException {

        setTagsArray(tags);

    }

    public TagBox(Set<String> tags) throws IllegalArgumentException {

        setTagsSet(tags);

    }

    private void checkTag(String tag) throws IllegalArgumentException {
        if (tag.length() < MIN_LENGTH_TAG)
            throw new IllegalArgumentException("Too short tag");

        if (tag.length() > MAX_LENGTH_TAG)
            throw new IllegalArgumentException("Too long tag");

        boolean iNameMatch = Pattern.matches(NAMEREG_STRING, tag);

        if (!iNameMatch)
            throw new IllegalArgumentException("Tag can only consist of chars");
    }

    private void setTagsArray(String... tags) throws IllegalArgumentException {
        for (String tag : tags) {
            checkTag(tag);
            this.tags.add(tag);
        }
    }

    // TODO utilize setTagsArray() or move this out to an own
    private void setTagsSet(Set<String> tags) throws IllegalAmountException {
        tags.forEach((tag) -> {
            checkTag(tag);
            this.tags.add(tag);
        });
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    public void retainAll(Set<String> tags) {
        this.tags.retainAll(tags);
    }

    public void addTag(String tag) throws IllegalArgumentException {
        checkTag(tag);
        tags.add(tag);
    }

    public void addTags(Set<String> tags) throws IllegalArgumentException {
        tags.forEach((tag) -> addTag(tag));
    }

    // XXX use .this to write this to shorter?
    public void addTags(String... tags) throws IllegalArgumentException {
        for (String tag : tags) {
            addTag(tag);
        }
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

    public void removeTags(String... tags) throws IllegalArgumentException {
        for (String tag : tags) {
            removeTag(tag);
        }
    }

    public static void main(String[] args) {
        String t1 = "new";
        String t2 = "tag";
        Set<String> set = new HashSet<>();
        set.add(t1);
        set.add(t2);
        TagBox tb = new TagBox(set);
        System.out.println(tb.getTags());
    }

}
