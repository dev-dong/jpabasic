package jpabook.jpashop.api;

import jpabook.jpashop.domain.item.Address;
import jpabook.jpashop.domain.item.Order;
import jpabook.jpashop.domain.item.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // getName()을 하면 Lazy 강제 초기화가 된다.
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    // Entity가 아닌 DTO로 변경해서 반환해야한다.
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // ORDER 2개
        // N + 1 문제 -> 1 + 회원 N개 + 배송 N개
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());

        // 2개면 loop가 2번 돈다
        return orders.stream().map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
    }

    // Repository는 순수한 Entity를 조회하기 때문에 DTO를 조회한다면 쿼리 디렉토리를 따로 만들어서 관리하는게 좋다.
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
