package hyper.darye.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * outbound
 */
public class Outbound implements Serializable {
    /**
     * 출고 ID (기본 키)
     */
    private Long id;

    /**
     * 입고 ID (외래 키)
     */
    private Long inboundId;

    /**
     * 출고 수량
     */
    private Integer quantity;

    /**
     * 출고 날짜
     */
    private LocalDateTime outboundDate;

    /**
     * 출고 사유 ID (외래 키)
     */
    private Long reasonId;

    /**
     * 생성 날짜
     */
    private LocalDateTime createdDate;

    /**
     * 수정 날짜
     */
    private LocalDateTime lastModifiedDate;

    /**
     * 삭제 날짜
     */
    private LocalDateTime deletedDate;

    public Outbound() {
    }

    public Outbound(Long inboundId, Integer quantity, LocalDateTime outboundDate, Long reasonId) {
        this.inboundId = inboundId;
        this.quantity = quantity;
        this.outboundDate = outboundDate;
        this.reasonId = reasonId;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInboundId() {
        return inboundId;
    }

    public void setInboundId(Long inboundId) {
        this.inboundId = inboundId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(LocalDateTime outboundDate) {
        this.outboundDate = outboundDate;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
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

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Outbound other = (Outbound) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInboundId() == null ? other.getInboundId() == null : this.getInboundId().equals(other.getInboundId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getOutboundDate() == null ? other.getOutboundDate() == null : this.getOutboundDate().equals(other.getOutboundDate()))
            && (this.getReasonId() == null ? other.getReasonId() == null : this.getReasonId().equals(other.getReasonId()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getLastModifiedDate() == null ? other.getLastModifiedDate() == null : this.getLastModifiedDate().equals(other.getLastModifiedDate()))
            && (this.getDeletedDate() == null ? other.getDeletedDate() == null : this.getDeletedDate().equals(other.getDeletedDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInboundId() == null) ? 0 : getInboundId().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getOutboundDate() == null) ? 0 : getOutboundDate().hashCode());
        result = prime * result + ((getReasonId() == null) ? 0 : getReasonId().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getLastModifiedDate() == null) ? 0 : getLastModifiedDate().hashCode());
        result = prime * result + ((getDeletedDate() == null) ? 0 : getDeletedDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", inboundId=").append(inboundId);
        sb.append(", quantity=").append(quantity);
        sb.append(", outboundDate=").append(outboundDate);
        sb.append(", reasonId=").append(reasonId);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append(", deletedDate=").append(deletedDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}