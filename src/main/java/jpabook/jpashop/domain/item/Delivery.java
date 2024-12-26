package jpabook.jpashop.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = LAZY, mappedBy = "delivery")
    @JsonIgnore
    private Order order;

    @Embedded
    private Address address;

    // ENUM을 사용할 때 Enumerted를 꼭 사용해야한다.
    // ORDINAL는 숫자인데 절대 사용하면 안된다!!
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
