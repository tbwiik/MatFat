package matFat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagBoxTest {

    TagBox tagBox;
    Set<String> newTagSet = new HashSet<>();
    String tag1, tag2, changeTag;

    @BeforeEach
    private void setup() {

        tag1 = "vegan";
        tag2 = "meat";
        changeTag = "fast";

        tagBox = new TagBox(tag1, tag2);

        newTagSet = new HashSet<>();
        newTagSet.add(tag1);
        newTagSet.add(tag2);

    }

    @Test
    void testAddTag() {

        // Test positive case
        tagBox.addTag(changeTag);
        newTagSet.add(changeTag);

        Assertions.assertEquals(newTagSet, tagBox.getTags());

        // Test too short tag
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tagBox.addTag("c");
        });

        // Test too long tag
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tagBox.addTag("ccccccccccc");
        });
    }

    @Test
    void testGenerateTags() {

    }

    @Test
    void testRemoveTag() {

    }

    @Test
    void testRemoveTags() {

    }

    @Test
    void testRetainAll() {

    }
}
