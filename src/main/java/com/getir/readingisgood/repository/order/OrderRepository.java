package com.getir.readingisgood.repository.order;

import com.getir.readingisgood.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<Order> findAllByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(final Date startDate, final Date endDate);
    List<Order> findAllByCustomerId(final Long id, final Pageable pageable);

    @Query(value = "select monthname(created_date) as \"Month\", count(*) as \"Total Order Count\",\n" +
            " sum(book_count) as \"Total Book Count\", sum(total_amount) as \"Total Purchased Amount\" from orders where customer_id=?1\n" +
            " group by monthname(created_date)", nativeQuery = true)
    List<Tuple> getCustomerMonthlyStatistics(final Long id);
}
