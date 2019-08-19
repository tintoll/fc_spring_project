package com.tintoll.admin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    private String content;


    // LAZY : 지연로딩 (연관관계에 대한 orderDetailList를 호출하지 않는이상 select를 하지 않겠다는 의미)
    // EAGER : 즉시로딩 (바로 연관관계가  설정된 데이터를 select하겠다는 의미)
    // EAGER는 1:1 방식에만 추천하고 대부분은 LAZY를 해준다.
    // 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderDetail> orderDetailList;
}
