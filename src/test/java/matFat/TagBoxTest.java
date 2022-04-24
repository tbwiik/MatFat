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
    void testAddTags() {

        String tag3 = "lowcarb";
        newTagSet.add(tag3);
        newTagSet.add(changeTag);

        tagBox.addTags(tag3, changeTag);
        Assertions.assertEquals(newTagSet, tagBox.getTags());

    }

    @Test
    void testRemoveTag() {

        tagBox.removeTag(tag2);
        newTagSet.remove(tag2);
        Assertions.assertEquals(newTagSet, tagBox.getTags());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tagBox.removeTag(changeTag);
        });

    }

    @Test
    void testRemoveTags() {

        tagBox.removeTags(tag1, tag2);
        Assertions.assertTrue(tagBox.getTags().isEmpty());

    }

}
