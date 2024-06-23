package com.vnpt.web.rest.response;

import com.vnpt.entity.RoleEntity;
import com.vnpt.entity.UserEntity;
import java.util.List;
import org.springframework.data.domain.Page;

public class GetListRoleResponse {

    private long recordsTotal;

    private int totalPages;

    private int sizeOfPage;

    private int recordsFiltered;

    private List<RoleEntity> data;

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSizeOfPage() {
        return sizeOfPage;
    }

    public void setSizeOfPage(int sizeOfPage) {
        this.sizeOfPage = sizeOfPage;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<RoleEntity> getData() {
        return data;
    }

    public void setData(List<RoleEntity> data) {
        this.data = data;
    }
}
