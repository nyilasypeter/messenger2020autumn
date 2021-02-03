package org.progmatic.messenger.dtos;

public class MessageDeleteResultDTO {

    private Long msgId;
    private boolean successFullyDeleted = false;
    private boolean successFullyRestored = false;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public boolean isSuccessFullyDeleted() {
        return successFullyDeleted;
    }

    public void setSuccessFullyDeleted(boolean successFullyDeleted) {
        this.successFullyDeleted = successFullyDeleted;
    }

    public boolean isSuccessFullyRestored() {
        return successFullyRestored;
    }

    public void setSuccessFullyRestored(boolean successFullyRestored) {
        this.successFullyRestored = successFullyRestored;
    }
}
