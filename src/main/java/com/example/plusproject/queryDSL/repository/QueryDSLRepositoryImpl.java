package com.example.plusproject.queryDSL.repository;

import com.example.plusproject.shoppingmall.entity.QShoppingMall;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryDSLRepositoryImpl implements QueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ShoppingMall> findByTotalRatingAndStoreStatusAfterId(
            TotalRating totalRating,
            StoreStatus storeStatus,
            Long cursorId,
            Pageable pageable
    ) {
        QShoppingMall shoppingMall = QShoppingMall.shoppingMall;
        JPAQuery<ShoppingMall> query = jpaQueryFactory.selectFrom(shoppingMall);

        //두 조건을 다 만족 시키는 경우
        if (totalRating != null && storeStatus != null) {
            query.where(shoppingMall.totalRating.eq(totalRating)
                    .and(shoppingMall.storeStatus.eq(storeStatus)));
        }
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

        // 페이지 크기보다 하나 더 많은 데이터를 가져와서, 마지막 아이템을 nextCursorId로 사용
        List<ShoppingMall> shoppingMalls = query.limit(pageable.getPageSize() + 1).fetch();

        // nextCursorId를 계산하는 코드. 만약 결과가 하나 더 많은 데이터가 있으면 마지막 데이터를 커서로 사용
        Long nextCursorId = (shoppingMalls.size() > pageable.getPageSize())
                ? shoppingMalls.get(pageable.getPageSize()).getId()
                : null;

        // 페이지 크기만큼 데이터 반환 (마지막 데이터를 제외)
        List<ShoppingMall> content = shoppingMalls.size() > pageable.getPageSize()
                ? shoppingMalls.subList(0, pageable.getPageSize())
                : shoppingMalls;

        return new PageImpl<>(content, pageable, shoppingMalls.size());
    }
}
