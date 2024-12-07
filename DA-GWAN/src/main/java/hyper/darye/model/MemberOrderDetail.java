package hyper.darye.model;

import java.time.Instant;

public class MemberOrderDetail {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer amount;
    private Instant createdDate;
    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder() {
        return orderId;
    }

    public void setOrder(Long order) {
        this.orderId = order;
    }

    public Long getProduct() {
        return productId;
    }

    public void setProduct(Long product) {
        this.productId = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

}