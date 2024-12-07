package hyper.darye.dto;

import java.time.LocalDateTime;

public abstract class BaseDto {

    protected Long id;
    protected LocalDateTime createdDate;
    protected LocalDateTime lastModifiedDate;

    public BaseDto() {
    }

    public BaseDto(Long id, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    protected String systemDatetoString() {
        return "createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate;
    }
}
