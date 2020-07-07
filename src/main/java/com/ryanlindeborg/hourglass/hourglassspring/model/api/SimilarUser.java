package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing json that will be sent to client for similar user profiles
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimilarUser {
    // By including the entire user, you can grab the profile image url, etc.
    private User user;
//    private String fullName;
//    private List<SimilarAttribute> similarAttributes;
    // string key is description of path to field - for presentation only
    private Map<String, SimilarAttributeValue> similarAttrs;

    // Could start with taking out recursive similarAttributeValue then add later- just start with value and metadata

    @Data
    // T here can be any object or just string - serializable
    private static class SimilarAttributeValue<T> {
        // With this recursion, can pass down entire objects - and let front end explore tree however deep they want to
        // Note: This T value will be n^2 b/c including all objects at entire level
        // To solve complexity, could just have entire structure at root node and T at other level is null
        // Could decorate job T object and hang confidencScores on each node
        // To decorate in Java, could subclass and add analytical but could get messy
        // This is more possible in JS
        private T value;
        // Note: Could add this map later - don't have to recurse down for first version of the solution
        private Map<String, SimilarAttributeValue> similarAttrs;
        /* metadta : e.g. match confidence, etc */
        // Note: Could add other metadata here!!
        float confidenceScore;
    }

//    @Data
    // May want to have fuzzy matching based on AI (how similar - confidence score: diff opacity based on how confident similarity is)
    // Look into generics
//    private static class SimilarAttribute<T> {
//        // Object on which there is profile match - this is hierarchy of depth 1 - so don't need similarFields map
////        private T similarObject;
//        private String similarKey;
//        private T similarValue;
//
//        // Could also build hierarchy object - similarFields - some filled in, and some not - front end has to parse
//        // Map of similar field name with field value
////        private Map<String, String> similarFields;
//    }
}
