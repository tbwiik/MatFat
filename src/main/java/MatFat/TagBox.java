package matFat;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class TagBox {

    private Set<String> tags = new HashSet<>();

    public final static String NAMEREG_STRING = "[a-zA-ZæøåÆØÅ]*";
    private final static int MIN_LENGTH_TAG = 3;
    private final static int MAX_LENGTH_TAG = 10;

    // TODO change such that all tags are lowercase and stripped
    public TagBox(String... tags) throws IllegalArgumentException {

        setTags(tags);

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

    private void setTags(String... tags) throws IllegalArgumentException {
        for (String tag : tags) {
            checkTag(tag);
            this.tags.add(tag);
        }
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    // XXX Rewrite documentation? Also ineffective code i think. See use in IngCont
    // XXX Have this here or in IngCont?
    /**
     * Generate tags to {@linkplain IngredientContainer} based on all
     * {@linkplain Ingredient} tags
     * <p>
     * Only add tags that every ingredient has.
     * This ensures that tags in container is valid for whole container.
     * E.g. dont add vegan if container contains meat as ingredient
     * <p>
     * Overwrites prior content of whole tagBox!
     * 
     */
    public void generateTags(IngredientContainer ingredientContainer) {

        // Adds tags to have an amount of tags to start with
        tags.addAll(ingredientContainer.getIngredient(0).getTags());

        // Iterates through ingredients and only keep tags that are similar
        ingredientContainer.getIngredients().forEach((ingredient) -> {
            tags.retainAll(ingredient.getTags());
        });

    }

    public void retainAll(Set<String> tags) {
        this.tags.retainAll(tags);
    }

    public void addTag(String tag) throws IllegalArgumentException {
        checkTag(tag);
        tags.add(tag);
    }

    // XXX Necessary?
    public void addTags(Set<String> tags) throws IllegalArgumentException {
        tags.forEach((tag) -> addTag(tag));
    }

    public void removeTag(String tag) throws IllegalArgumentException {
        if (!tags.contains(tag))
            throw new IllegalArgumentException("Tag not in box");
        tags.remove(tag);
    }

    // XXX necessary?
    public void removeTags(String... tags) throws IllegalArgumentException {
        this.tags.forEach((tag) -> removeTag(tag));
    }
}
