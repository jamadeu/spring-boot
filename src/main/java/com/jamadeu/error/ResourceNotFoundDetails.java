package com.jamadeu.error;

/**
 * Created by Jean Amadeu 12 julho 2020
 */
public class ResourceNotFoundDetails {
    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String developerMessage;

    private ResourceNotFoundDetails() {
    }


    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public static final class ResourceNotFoundDetailsBuilder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private ResourceNotFoundDetailsBuilder() {
        }

        public static ResourceNotFoundDetailsBuilder newBuilder() {
            return new ResourceNotFoundDetailsBuilder();
        }

        public ResourceNotFoundDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ResourceNotFoundDetailsBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ResourceNotFoundDetailsBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ResourceNotFoundDetailsBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResourceNotFoundDetailsBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResourceNotFoundDetails build() {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            resourceNotFoundDetails.status = status;
            resourceNotFoundDetails.developerMessage = developerMessage;
            resourceNotFoundDetails.detail = detail;
            resourceNotFoundDetails.timestamp = timestamp;
            resourceNotFoundDetails.title = title;
            return resourceNotFoundDetails;
        }
    }
}
