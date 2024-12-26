package jpabook.jpashop.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    // 하나의 회원이 여러개의 상품을 주문하기 때문에
    // mappedBy는 Order Entity에 있는 member 필드에 의해서 맵핑 된 거야 라고 명시
    // 양방향 관계 시 한쪽은 JsonIgnore를 해줘야 한다.
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> order = new ArrayList<>();
}
