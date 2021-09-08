package com.VirtualNoticeBoardBackend.Payload.Requests;

public class RemoveAnnouncementRequest {
    private Long announcementId;

    public RemoveAnnouncementRequest(Long announcementId) {
        this.announcementId = announcementId;
    }

    public RemoveAnnouncementRequest() {}

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }
}
