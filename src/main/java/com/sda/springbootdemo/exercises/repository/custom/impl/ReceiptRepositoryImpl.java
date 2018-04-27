package com.sda.springbootdemo.exercises.repository.custom.impl;

import com.sda.springbootdemo.exercises.model.Product;
import com.sda.springbootdemo.exercises.model.Receipt;
import com.sda.springbootdemo.exercises.repository.custom.ReceiptRepositoryCustom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ReceiptRepositoryImpl implements ReceiptRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Receipt> search(String buyer, LocalDateTime startDate, LocalDateTime endDate,
        String productName, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Receipt> productQuery = builder.createQuery(Receipt.class);
        productQuery = prepareQuery(productQuery, buyer, startDate, endDate, productName, false, builder, pageable);

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery = prepareQuery(countQuery, buyer, startDate, endDate, productName, true, builder, pageable);

        Long count = entityManager.createQuery(countQuery).getSingleResult();

        int pageSize = null != pageable ? pageable.getPageSize() : 0;
        int firstResult = null != pageable ? pageable.getPageNumber() * pageSize : 0;

        List<Receipt> processingPeriods = entityManager.createQuery(productQuery)
            .setMaxResults(pageSize)
            .setFirstResult(firstResult)
            .getResultList();
        return new PageImpl<>(processingPeriods, pageable, count);
    }


    private <T> CriteriaQuery<T> prepareQuery(CriteriaQuery<T> query, String buyer,
        LocalDateTime startDate, LocalDateTime endDate, String productName,
        boolean count, CriteriaBuilder builder, Pageable pageable) {

        Root<Receipt> root = query.from(Receipt.class);

        if (count) {
            CriteriaQuery<Long> countQuery = (CriteriaQuery<Long>) query;
            query = (CriteriaQuery<T>) countQuery.select(builder.count(root));
        }

        Predicate predicate = builder.conjunction();

        if (null != buyer) {
            predicate = builder.and(predicate,
                builder.like(builder.upper(root.get("buyer")), buyer.toUpperCase()));
        }

        if (null != startDate) {
            predicate = builder.and(predicate,
                builder.greaterThanOrEqualTo(root.get("date"), startDate));
        }

        if (null != endDate) {
            predicate = builder.and(predicate,
                builder.lessThanOrEqualTo(root.get("date"), endDate));
        }

        if (productName != null) {
            Join<Receipt, Product> receiptProducts = root.join("products", JoinType.LEFT);
            predicate = builder.and(
                predicate,
                builder.like(builder.upper(receiptProducts.get("name")), productName.toUpperCase()));
        }

        if (!count && pageable != null && pageable.getSort() != null) {
            query = addSortProperties(query, root, builder, pageable);
        }

        return query.where(predicate);
    }

    private <T> CriteriaQuery<T> addSortProperties(CriteriaQuery<T> query,
        Root<Receipt> root, CriteriaBuilder builder, Pageable pageable) {

        List<Order> orders = new ArrayList<>();
        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        Sort.Order order;

        while (iterator.hasNext()) {
            order = iterator.next();
            String property = order.getProperty();
            Path path = root.get(property);

            if (order.isAscending()) {
                orders.add(builder.asc(path));
            } else {
                orders.add(builder.desc(path));
            }
        }

        return query.orderBy(orders);
    }
}
