package vn.hoangkhang.laptopshop.domain.dto;

import java.util.Optional;

public class ReviewCriteriaDTO {
    private Optional<String> page;
    private Optional<String> productId;
    private Optional<String> rating;
    private Optional<String> fromDate;
    private Optional<String> toDate;
    private Optional<String> sort;

    public Optional<String> getPage() {
        return page;
    }

    public void setPage(Optional<String> page) {
        this.page = page;
    }

    public Optional<String> getProductId() {
        return productId;
    }

    public void setProductId(Optional<String> productId) {
        this.productId = productId;
    }

    public Optional<String> getRating() {
        return rating;
    }

    public void setRating(Optional<String> rating) {
        this.rating = rating;
    }

    public Optional<String> getFromDate() {
        return fromDate;
    }

    public void setFromDate(Optional<String> fromDate) {
        this.fromDate = fromDate;
    }

    public Optional<String> getToDate() {
        return toDate;
    }

    public void setToDate(Optional<String> toDate) {
        this.toDate = toDate;
    }

    public Optional<String> getSort() {
        return sort;
    }

    public void setSort(Optional<String> sort) {
        this.sort = sort;
    }

}
