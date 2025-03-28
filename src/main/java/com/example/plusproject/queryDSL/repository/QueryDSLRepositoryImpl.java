package com.example.plusproject.queryDSL.repository;

import com.example.plusproject.shoppingmall.entity.QShoppingMall;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryDSLRepositoryImpl implements QueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ShoppingMall> findByTotalRatingAndStoreStatusAfterId(
            TotalRating totalRating,
            StoreStatus storeStatus,
            Long cursorId,
            Pageable pageable
    ) {
        QShoppingMall shoppingMall = QShoppingMall.shoppingMall;
        JPAQuery<ShoppingMall> query = jpaQueryFactory.selectFrom(shoppingMall);

        //전체 평가만 존재하는 경우
        if (totalRating != null) {
            query.where(shoppingMall.totalRating.eq(totalRating));
        }
        //영업상태만 존재하는 경우
        if (storeStatus != null) {
            query.where(shoppingMall.storeStatus.eq(storeStatus));
        }

        //커서ID가 존재하는 경우
        if (cursorId != null) {
            query.where(shoppingMall.id.gt(cursorId));
        }

        // 페이지 크기보다 하나 더 많은 데이터를 가져와서, hasNext로 더보기가 있는지 여부 체크
        List<ShoppingMall> shoppingMalls = query.limit(pageable.getPageSize() + 1).fetch();
        boolean hasNext = shoppingMalls.size() > pageable.getPageSize();
        // 페이지 크기만큼 데이터 반환 (마지막 데이터를 제외)
        List<ShoppingMall> content = shoppingMalls.size() > pageable.getPageSize()
                ? shoppingMalls.subList(0, pageable.getPageSize())
                : shoppingMalls;

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
