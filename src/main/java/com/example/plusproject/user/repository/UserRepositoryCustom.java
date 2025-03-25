package com.example.plusproject.user.repository;

// soft delete 구현을 위한 custom repository
public interface UserRepositoryCustom {

    // 필터 활성화 및 비활성화 메서드
    void enableSoftDeleteFilter();
    void disableSoftDeleteFilter();
}
