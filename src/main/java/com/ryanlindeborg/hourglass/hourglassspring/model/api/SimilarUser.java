package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;

import java.util.List;

/**
 * Class representing json that will be sent to client for similar user profile
 */
public class SimilarUser {
    // User who you would like to find similar profiles for
    private User sourceUser;
    // User that has similar profile attribute to main user
    private User similarUser;
    private List<SimilarAttribute> similarAttributes;

    private class SimilarAttribute {
        // Object on which there is profile match
        private String similarObject;
        // Object fields on which there is profile match
        private List<String> similarFields;

        public String getSimilarObject() {
            return similarObject;
        }

        public void setSimilarObject(String similarObject) {
            this.similarObject = similarObject;
        }

        public List<String> getSimilarFields() {
            return similarFields;
        }

        public void setSimilarFields(List<String> similarFields) {
            this.similarFields = similarFields;
        }
    }

    public User getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(User sourceUser) {
        this.sourceUser = sourceUser;
    }

    public User getSimilarUser() {
        return similarUser;
    }

    public void setSimilarUser(User similarUser) {
        this.similarUser = similarUser;
    }

    public List<SimilarAttribute> getSimilarAttributes() {
        return similarAttributes;
    }

    public void setSimilarAttributes(List<SimilarAttribute> similarAttributes) {
        this.similarAttributes = similarAttributes;
    }
}
