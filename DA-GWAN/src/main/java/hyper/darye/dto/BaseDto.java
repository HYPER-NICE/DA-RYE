package hyper.darye.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public abstract class BaseDto {

    protected Long id;
    protected Instant createdDate;
    protected Instant lastModifiedDate;

    public BaseDto() {
    }

    public BaseDto(Long id, Instant createdDate, Instant lastModifiedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    protected String systemDatetoString() {
        return "createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate;
    }
}
