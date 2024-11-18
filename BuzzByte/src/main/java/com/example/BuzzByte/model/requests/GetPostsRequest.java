package com.example.BuzzByte.model.requests;

public class GetPostsRequest {
    private int page = 0;
    private int limit = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}