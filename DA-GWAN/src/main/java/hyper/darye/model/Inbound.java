package hyper.darye.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * inbound
 */
public class Inbound implements Serializable {
    /**
     * 입고 ID (기본 키)
     */
    private Long id;

    /**
     * 제품 ID (외래 키)
     */
    private Long productId;

    /**
     * 배치 번호
     */
    private String batchNumber;

    /**
     * 입고 수량
     */
    private Integer quantity;

    /**
     * 입고 가격 (원)
     */
    private Integer purchasePrice;

    /**
     * 유통기한
     */
    private LocalDateTime  expiryDate;

    /**
     * 입고 날짜
     */
    private LocalDateTime  receivedDate;

    /**
     * 생성 날짜
     */
    private LocalDateTime  createdDate;

    /**
     * 수정 날짜
     */
    private LocalDateTime  lastModifiedDate;

    public Inbound(Long productId, String batchNumber, Integer quantity, Integer purchasePrice, LocalDateTime  expiryDate, LocalDateTime  receivedDate) {
        this.productId = productId;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.expiryDate = expiryDate;
        this.receivedDate = receivedDate;
    }

    public Inbound(Long id, Long productId, String batchNumber, Integer quantity, Integer purchasePrice, LocalDateTime  expiryDate, LocalDateTime  receivedDate, LocalDateTime  createdDate, LocalDateTime  lastModifiedDate) {
        this.id = id;
        this.productId = productId;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.expiryDate = expiryDate;
        this.receivedDate = receivedDate;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDateTime  getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime  expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDateTime  getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime  receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDateTime  getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime  createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime  getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime  lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
        Inbound other = (Inbound) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getBatchNumber() == null ? other.getBatchNumber() == null : this.getBatchNumber().equals(other.getBatchNumber()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPurchasePrice() == null ? other.getPurchasePrice() == null : this.getPurchasePrice().equals(other.getPurchasePrice()))
            && (this.getExpiryDate() == null ? other.getExpiryDate() == null : this.getExpiryDate().equals(other.getExpiryDate()))
            && (this.getReceivedDate() == null ? other.getReceivedDate() == null : this.getReceivedDate().equals(other.getReceivedDate()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getLastModifiedDate() == null ? other.getLastModifiedDate() == null : this.getLastModifiedDate().equals(other.getLastModifiedDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getBatchNumber() == null) ? 0 : getBatchNumber().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPurchasePrice() == null) ? 0 : getPurchasePrice().hashCode());
        result = prime * result + ((getExpiryDate() == null) ? 0 : getExpiryDate().hashCode());
        result = prime * result + ((getReceivedDate() == null) ? 0 : getReceivedDate().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getLastModifiedDate() == null) ? 0 : getLastModifiedDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", batchNumber=").append(batchNumber);
        sb.append(", quantity=").append(quantity);
        sb.append(", purchasePrice=").append(purchasePrice);
        sb.append(", expiryDate=").append(expiryDate);
        sb.append(", receivedDate=").append(receivedDate);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}